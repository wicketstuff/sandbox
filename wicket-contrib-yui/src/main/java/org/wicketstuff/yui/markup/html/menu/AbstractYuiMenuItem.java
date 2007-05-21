package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class AbstractYuiMenuItem extends Panel
{
    public static final String MENU_ITEM_LINK_ID = "menuItemLink";
    public static final String MENU_ITEM_LABEL_ID = "menuItemLabel";
    public static final String MENU_ITEM_SUBMENU_ID = "submenu";

    public AbstractYuiMenuItem(String id)
    {
        super(id);
                
        AbstractLink link = getLink(MENU_ITEM_LINK_ID);
        add(link);
        
        Label label = getLabel(MENU_ITEM_LABEL_ID);
        label.setRenderBodyOnly(true);
        
        link.add(label);
        
        MarkupContainer subMenu = getSubMenu(MENU_ITEM_SUBMENU_ID);
        subMenu.setRenderBodyOnly(true);
        add(subMenu);
        
    }

    public abstract AbstractLink getLink(String menuItemLinkId);

    public abstract Label getLabel(String menuItemLabelId);

    public abstract MarkupContainer getSubMenu(String menuItemSubMenuId);

    public abstract String getMenuClass();
    
    @Override
    protected void onComponentTag(ComponentTag tag)
    {
        super.onComponentTag(tag);
        tag.put("class", getMenuClass());
    }
}
