package org.wicketstuff.dojo.examples;

import java.util.ArrayList;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.dojo.markup.html.form.DojoDropDownChoice;
import org.wicketstuff.dojo.markup.html.form.DojoSubmitButton;
import org.wicketstuff.dojo.skin.AbstractDojoSkin;
import org.wicketstuff.dojo.skin.manager.SkinManager;
import org.wicketstuff.dojo.skin.windows.WindowsDojoSkin;

/**
 * Basic bookmarkable index page.
 * 
 * NOTE: You can get session properties from ExampleSession via getQuickStartSession()
 */
public class Index extends ExampleBasePage 
{
	private final DojoDropDownChoice  skin;
	
	private String currentSkin;;
	
	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public Index(final PageParameters parameters) 
    {
		currentSkin = getCurrentSelectedSkin();
		
        ArrayList<String> skins = new ArrayList<String>();
        skins.add("Windows");
        skins.add("Default");
        
        skin = new DojoDropDownChoice("skins", new PropertyModel(this, "currentSkin"), skins);
        
        Form form = new Form("selectSkin");
        final Label label;
        add(label = new Label("skinSelected", new PropertyModel(this, "currentSkin")));
        label.setOutputMarkupId(true);
        
        add(form);
        form.add(skin);
        form.add(new DojoSubmitButton("submit", form){

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				if ("Windows".equals(skin.getModelObjectAsString())){
        			SkinManager.getInstance().setupSkin(new WindowsDojoSkin());
        		}else{
        			SkinManager.getInstance().setupSkin(null);
        		}
				target.addComponent(label);
			}
        	
        });  
	}
	
	public String getCurrentSelectedSkin(){
		AbstractDojoSkin current = SkinManager.getInstance().getSkin();
		if (current == null){
			return "Default";
		}
		if (current instanceof WindowsDojoSkin){
			return "Windows";
		}
		return "Default";
	}

}
