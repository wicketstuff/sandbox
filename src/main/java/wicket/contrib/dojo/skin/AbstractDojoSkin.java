package wicket.contrib.dojo.skin;

import java.io.File;
import java.net.URL;

import wicket.Component;
import wicket.ResourceReference;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

public abstract class AbstractDojoSkin {
	
	protected abstract Class getRessourceClass();
	
	private final boolean exists(String file){
		URL res = getRessourceClass().getClassLoader().getResource(getRessourceClass().getPackage().getName().replace('.', File.separatorChar) + File.separatorChar + file);
 		return res!=null;
	}
	
	public final String getTemplateCssPath(Component component, AbstractRequireDojoBehavior behavior){
		String cssTemplate = getClassName(behavior.getClass().getName()).replaceAll("Handler", "") + ".css";
		 if (exists(cssTemplate)){
			return (String) component.urlFor(new ResourceReference(getRessourceClass(), cssTemplate));
		}else{
			return null;
		}
	}
	
	public final String getTemplateHtmlPath(Component component, AbstractRequireDojoBehavior behavio){
		String htmlTemplate = getClassName(component.getClass().getName().replaceAll("Handler", "")) + ".htm";
		 if (exists(htmlTemplate)){
			return (String) component.urlFor(new ResourceReference(getRessourceClass(), htmlTemplate));
		}else{
			return null;
		}
	}
	
	private final String getClassName(String clazz){
		return clazz.substring(clazz.lastIndexOf(".")+1, clazz.length());
	}
}
