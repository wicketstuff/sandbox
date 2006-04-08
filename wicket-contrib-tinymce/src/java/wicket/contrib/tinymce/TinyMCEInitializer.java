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
package wicket.contrib.tinymce;

import java.util.regex.Pattern;

import wicket.Application;
import wicket.IInitializer;
import wicket.markup.html.PackageResource;

/**
 * Wicket initializer for TinyMCE component.
 * 
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCEInitializer implements IInitializer {

	private static final String TINY_MCE = "^tiny_mce.*(.js|.gif|.html|.css)$";

	private Class scope = TinyMCEPanel.class;

	public void init(Application application) {
		PackageResource.bind(application, scope, Pattern.compile(TINY_MCE), true);
	}

	/* used for testing purpose */
	void setScope(Class scope) {
		this.scope = scope;
	}
}
