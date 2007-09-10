package org.wicketstuff.yui.markup.html.menu2;

import org.apache.wicket.markup.html.WebMarkupContainer;

class YuiMenuGroupMenu extends YuiMenu {

	YuiMenuGroupMenu() {
		this(false, false);
	}

	YuiMenuGroupMenu( final boolean firstMenu, boolean addInit) {
		super( null, firstMenu, addInit );
	}
	
	protected WebMarkupContainer getMenuContainer() {
		return null;
	}

}
