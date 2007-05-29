package org.wicketstuff.yui.markup.html.menu;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class YuiMenuItem extends AbstractYuiMenuItem
{
    
    static final Logger log = LoggerFactory.getLogger(YuiMenuItem.class);

    public static final String MENU_ITEM_ID = "menuItem";
    private static final String CSS_CHECKED = "checked";
    
    private boolean checked = false;
    
    protected String text;
    
    public YuiMenuItem(final String label) {
        super (MENU_ITEM_ID, label);
        
        IModel checkedModel = new AbstractReadOnlyModel() {

            @Override
            public Object getObject()
            {
                if(isChecked()) {
                    return CSS_CHECKED;
                }
                return null;
            }
            
        };
        
        add(new AttributeAppender("class", true, checkedModel, " "));
    }
    
    @Override
    public String getMenuClass()
    {
        return "yuimenuitem";
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    
}
