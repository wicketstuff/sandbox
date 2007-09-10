package org.wicketstuff.yui.markup.html.menu2;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

public class YuiMenuItem extends AbstractYuiMenuItem
{
    public static final String MENU_ITEM_ID = "menuItem";

    public YuiMenuItem(final IAction action) {
        super (MENU_ITEM_ID);
        
        Link link = null;
        
        add( link = new Link( "link", new Model( action.getName() ) ) {
        	public void onClick() {
        		action.onClick();
        	}
        });
        link.add( new Label( "linkLabel", new Model( action.getName() )).setRenderBodyOnly( true ));
    }
    
    public YuiMenuItem( final String label, final Link link ) {
    	super( MENU_ITEM_ID );
    	if ( link.getId().equals( "link" ) == false ) {
    		throw new RuntimeException( "Link's id needs to be 'link' " );
    	}
    	
    	add( link );
        link.add( new Label( "linkLabel", new Model( label )).setRenderBodyOnly(true));
    }
    
    @Override
    public String getMenuClass()
    {
        return "yuimenuitem";
    }
    

}
