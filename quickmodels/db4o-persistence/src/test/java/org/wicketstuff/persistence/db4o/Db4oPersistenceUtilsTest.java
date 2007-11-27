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

import com.db4o.ext.Db4oUUID;
import junit.framework.TestCase;

/**
 *
 * @author Tim Boudreau
 */
public class Db4oPersistenceUtilsTest extends TestCase {
    
    public Db4oPersistenceUtilsTest() {
    }

    String test = "12h10q732001:74696d2d626f756472656175732d636f6d70757465722e6c6f63616c00c0a802640000011441c5bf179c04dbd95d9ed881b9a8df927dd8cc04";
    public void testStringToUUid() {
        Db4oPersistenceUtils u = new Db4oPersistenceUtils(null);
        Db4oUUID uuid = u.stringToUUid(test);
        String s = u.uuidToString(uuid);
        assertEquals (test, s);
        Db4oUUID uuid2 = u.stringToUUid(s);
        assertEquals (uuid, uuid2);
    }


}
