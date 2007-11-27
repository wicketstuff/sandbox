/* 
 * The files in this library are DUAL-LICENSED for license compatibility with
 * the db4o (http://db4o.com) object database, as follows:  Db4o is distributed
 * under both commercial licenses and the GNU Public License version 2 (GPLv2).
 * If you obtained your copy of db4o under the GPLv2 license, then you agree to
 * use this software also under the terms of the GPLv2.  
 * 
 * If you obtained your copy of db4o under a commercial license, then you may
 * use this software under the terms of the Apache license (or the GPLv2 license
 * if you so choose).
 * 
 * =============================================================================
 * GNU Public License Notice:
 * Db4o implementation of the WicketStuff persistence facade service provider
 * interface
 * Copyright (C) 2007 Tim Boudreau
 *
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * =============================================================================
 * Apache License Notice:
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.persistence.db4o;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ext.Db4oException;
import org.wicketstuff.persistence.Db;
import org.wicketstuff.persistence.DbJob;
import org.wicketstuff.persistence.spi.CapabilityProvider;
import org.wicketstuff.persistence.spi.DbImplementation;
import org.wicketstuff.persistence.spi.PersistenceUtils;

import java.io.*;

/**
 * Implementation of database access over the db4o object database
 * @author Tim Boudreau
 */
public final class Db4oDbImpl implements DbImplementation <ObjectContainer>, CapabilityProvider, Defrag, Backup {
    private final String file;
    private static transient ObjectContainer container;
    
    Db4oDbImpl(String file) {
        this.file = file;
        System.err.println("Using database file " + new File(file).getAbsolutePath());
    }
    
    public static Db<ObjectContainer> create(String file) {
        Db4oDbImpl impl = new Db4oDbImpl(file);
        Db<ObjectContainer> result = new Db<ObjectContainer> (impl);
        return result;
    }

    public static Db<ObjectContainer> create(String file, DbJob<ObjectContainer, Void, Db> initialSetup) {
        Db4oDbImpl impl = new Db4oDbImpl(file);
        Db<ObjectContainer> result = new Db<ObjectContainer> (impl);
        result.run(initialSetup, result);
        return result;
    }
    
    public void shutdown() {
        if (container != null) {
            container.ext().purge();
            while (!container.close()) {}
            container = null;
        }
    }
    
    volatile long accesscount = 0;
    public <T, P> T run(DbJob<ObjectContainer, T, P> job, P param) {
        if (defragging) {
            throw new RuntimeException ("Database defragmentation in progress");
        }
        if (container == null) {
            initContainer();
        }
        T result;
        try {
            result = job.run(container, param);
        } finally {
            accesscount++;
            try {
                container.commit();
            } catch (Db4oException e) {
                e.printStackTrace();
                //Restart the db.  If the database was wiped, this
                //will be needed.  Or if something went horribly wrong...
                initContainer();
                try {
                    result = job.run(container, param);
                } finally {
                    container.commit();
                }
            }
        }
        if (accesscount % 500 == 0) {
            memCheck();
        }
        return result;
    }
    
    private void memCheck() {
        long maxMem = Runtime.getRuntime().maxMemory();
        long avail = Runtime.getRuntime().freeMemory();
        System.err.println("Max memory " + maxMem);
        System.err.println("Avail memory " + avail);
        System.err.println("DB accesses " + accesscount);
        if (avail < ((maxMem / 3) * 2)) {
            System.err.println(avail + " out of " + maxMem + " memory used." +
                    " Running database purge to clear available memory");
            container.ext().purge();
            
            //XXX deleteme this is only to be able to log the real
            //results of cleanup
//            for (int i=0; i < 3; i++) {
//                System.gc();
//                System.runFinalization();
//            }
            long nue = Runtime.getRuntime().freeMemory();
            System.err.println("Free memory now " + nue + ".  " +
                    (nue - avail) + " recovered");
        }
    }
    
    private void initContainer() {
        container = Db4o.openFile(file);
    }


    public PersistenceUtils createPersistenceUtils(Db<ObjectContainer> db) {
        return new Db4oPersistenceUtils(db);
    }

    public <T> T getCapability(Class<T> clazz) {
        if (clazz == Defrag.class || clazz == Backup.class) {
            return (T) this;
        } else {
            return null;
        }
    }

    private volatile boolean defragging = false;
    public void defragmentDatabase() throws IOException {
        if (container == null) {
            initContainer();
        }
        container.ext().backup("backup.yap");
        if (container == null) {
            throw new IOException ("Container null");
        }
        defragging = true;
        try {
            while (!container.close()) {
                //do nothing
            }
            container = null;
            try {
                com.db4o.defragment.Defragment.defrag(file);
            } catch (RuntimeException e) {
                File f = new File (this.file);
                File backup = new File ("backup.yap");
                FileInputStream in = new FileInputStream (backup);
                FileOutputStream out = new FileOutputStream (f);
                try {
                    copy (in, out);
                } finally {
                    in.close();
                    out.close();
                }
                throw new RuntimeException ("Exception occurred during " +
                        "defragmentation.  Restoring undefragmented " +
                        "backup.", e);
            }
        } finally {
            defragging = false;
        }
    }
    
    public static void copy(InputStream is, OutputStream os)
    throws IOException {
        final byte[] BUFFER = new byte[1024];
        int len;

        for (;;) {
            len = is.read(BUFFER);

            if (len == -1) {
                return;
            }

            os.write(BUFFER, 0, len);
        }
    }

    public void backup(String file) throws IOException {
        if (container == null) {
            initContainer();
        }
        container.ext().backup(file);
    }
}
