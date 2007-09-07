/*
 * YuiEditorPage.java
 *
 * Created on 7. September 2007, 11:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.wicketstuff.yui.examples.pages;

import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.editor.YuiEditor;

/**
 *
 * @author korbinianbachl
 */
public class YuiEditorPage extends WicketExamplePage {
    
    /**
     * Creates a new instance of YuiEditorPage
     */
    public YuiEditorPage() {
        add(new YuiEditor("yuieditor", new Model("This is the Modelcontent.")));
    }
    
}
