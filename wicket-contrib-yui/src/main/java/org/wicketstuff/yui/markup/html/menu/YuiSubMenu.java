package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;

public class YuiSubMenu extends YuiMenu
{

    public YuiSubMenu(String id, YuiMenuGroupListModel model)
    {
        super(id, model);
    }

    @Override
    protected String getMenuElementId()
    {
        return null;
    }

    
    

}
