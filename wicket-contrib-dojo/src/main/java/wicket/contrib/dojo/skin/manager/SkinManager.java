package wicket.contrib.dojo.skin.manager;

import wicket.Component;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.skin.AbstractDojoSkin;
import wicket.markup.ComponentTag;

public class SkinManager {
	
	private static SkinManager instance;
	private AbstractDojoSkin skin;

	private SkinManager() {
		
	}
	
	public static SkinManager getInstance(){
		if (instance == null){
			instance = new SkinManager();
		}
		return instance;
	}
	
	public void setupSkin(AbstractDojoSkin skin){
		this.skin = skin;
	}
	
	public void setSkin(Component component,  AbstractRequireDojoBehavior behavior, ComponentTag tag){
		if (skin !=null){
			String templateCssPath = skin.getTemplateCssPath(component, behavior);
			String templateHtmlPath = skin.getTemplateHtmlPath(component, behavior);
			if(templateCssPath != null){
				tag.put("templateCssPath", templateCssPath);
			}
			if (templateHtmlPath != null){		
				tag.put("templatePath"   , templateHtmlPath);
			}
		}
	}
	
	
	
}
