package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;

public abstract class YuiMenuItem extends AbstractYuiMenuItem
{

    public static final String MENU_ITEM_ID = "menuItem";
    
    public YuiMenuItem()
    {
        super(MENU_ITEM_ID);
    }

    @Override
    public String getMenuClass()
    {
        return "yuimenuitem";
    }

}
