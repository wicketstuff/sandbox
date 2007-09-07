/*
 * YuiEditor.java
 *
 * Created on 7. September 2007, 11:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wicketstuff.yui.markup.html.editor;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.yui.YuiHeaderContributor;

/**
 *
 * @author korbinianbachl
 */
public class YuiEditor extends Panel implements IHeaderContributor{
    
    private static final long serialVersionUID = 1L;

    /** Ref to CSS file */
    private static final ResourceReference CSS = new ResourceReference(YuiHeaderContributor.class, "inc/2.3.0/assets/skins/sam/skin.css");
    
    /** Creates a new instance of YuiEditor 
     *
     *  Attention: currently in Alpha state!
     *  @param String id
     *  @param IModel model
     */
    public YuiEditor(String id, IModel model) {
        super(id);
        
        add(YuiHeaderContributor.forModule("editor", new String[]{"utilities","container","menu","button"}));
        add(HeaderContributor.forCss(CSS));
        //add(HeaderContributor.forJavaScript(YuiEditor.class,"YuiEditor.js"));
        TextArea ta = new TextArea("editorArea", model);
        ta.setMarkupId("msgpost");
      
        add(ta);
    }
    
    
    /**
     * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
     */
    public void renderHead(IHeaderResponse response)
    {
            response.renderOnLoadJavascript("\n var myEditor = new YAHOO.widget.Editor('msgpost', { \n" +
             	   " height: '300px', \n" +
              	   " width: '522px', \n" +
              	   " dompath: true, //Turns on the bar at the bottom \n" +
              	   " animate: true //Animates the opening, closing and moving of Editor windows \n" +
              	 " }); \n" +
             " myEditor.render(); \n");
            
            
             
            
    }
    
}













