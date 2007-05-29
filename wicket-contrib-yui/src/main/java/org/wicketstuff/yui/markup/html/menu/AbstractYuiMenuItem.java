package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public abstract class AbstractYuiMenuItem extends Panel
{
    public static final String MENU_ITEM_LINK_ID = "menuItemLink";
    public static final String MENU_ITEM_LABEL_ID = "menuItemLabel";
    public static final String MENU_ITEM_SUBMENU_ID = "submenu";
    public static final String FRAGMENT_NO_SUBMENU_ID = "menuItemNoSubMenu";
    public static final String FRAGMENT_WITH_SUBMENU_ID = "menuItemWithSubMenu";
    public static final String MENU_ITEM_ID = "theMenuItem";
    
    private static final String CSS_SELECTED = "selected";
    
    private static final String CSS_DISABLED = "disabled";
    
    private boolean selected;
    
    private boolean disabled;
    
    
    private String text;


    public AbstractYuiMenuItem(String id, String text)
    {
        super(id);
        setOutputMarkupId(true);
        setText(text);
        Label label = getLabel(MENU_ITEM_LABEL_ID);
        label.setRenderBodyOnly(true);
        
        MarkupContainer subMenu = getSubMenu(MENU_ITEM_SUBMENU_ID);
        if(null == subMenu) {
            Fragment fragNoSubMenu = new Fragment(MENU_ITEM_ID, FRAGMENT_NO_SUBMENU_ID);
            AbstractLink link = getLink(MENU_ITEM_LINK_ID);
            link.add(label);
            fragNoSubMenu.add(link);
            fragNoSubMenu.setRenderBodyOnly(true);
            add(fragNoSubMenu);
        } else {
            Fragment fragWithSubMenu = new Fragment(MENU_ITEM_ID, FRAGMENT_WITH_SUBMENU_ID);

            fragWithSubMenu.add(label);
            
            subMenu.setRenderBodyOnly(true);
            
            fragWithSubMenu.add(subMenu);
            
            fragWithSubMenu.setRenderBodyOnly(true);
            
            add(fragWithSubMenu);
            
        }
        
        IModel classModel = new AbstractReadOnlyModel() {

            @Override
            public Object getObject()
            {
                if(isDisabled()) {
                    return CSS_DISABLED;
                }
                else if(isSelected()) {
                    return CSS_SELECTED;
                }
                return null;
            }
            
        };
        
        add(new AttributeAppender("class", true, classModel, " "));

    }

    public abstract AbstractLink getLink(String menuItemLinkId);


    public abstract MarkupContainer getSubMenu(String menuItemSubMenuId);

    public abstract String getMenuClass();
    
    public final String getText()
    {
        return text;
    }

    public final void setText(String text)
    {
        this.text = text;
    }

    public Label getLabel(String menuItemLabelId)
    {
        String labelText = getText();
        return new Label(menuItemLabelId, labelText);
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

}
