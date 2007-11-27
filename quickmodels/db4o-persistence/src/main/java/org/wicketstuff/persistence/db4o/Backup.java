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

import java.io.IOException;

/**
 * Capability of backing up the database
 * @author Tim Boudreau
 */
public interface Backup {
    public void backup (String file) throws IOException;
}
