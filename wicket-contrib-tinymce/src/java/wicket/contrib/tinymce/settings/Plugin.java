/*
 *  Copyright (C) 2005  Iulian-Corneliu Costan
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package wicket.contrib.tinymce.settings;

/**
 * Base class for all plugins.
 *
 * @author Iulian-Corneliu COSTAN
 */
public abstract class Plugin extends wicket.contrib.tinymce.settings.Enum {

    // types of plugins: w/o buttons, w/ buttons, w/ standard props, w/ private props

    protected Plugin(String name) {
        super(name);
    }

    // implemented by subclasses
    String defineProperties() {
        return null;
    }

    // implemented by subclasses
    String getExtension() {
        return null;
    }
}
