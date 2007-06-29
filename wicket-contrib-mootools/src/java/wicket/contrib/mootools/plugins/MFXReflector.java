package wicket.contrib.mootools.plugins;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;

import wicket.contrib.mootools.AbstractRequireMooStatelessBehavior;

public class MFXReflector extends AbstractRequireMooStatelessBehavior {
	private static final long serialVersionUID = 1L;
	private CompressedResourceReference REFLECTOR_JS = new CompressedResourceReference(MFXReflector.class,"reflection.js");
	
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavascriptReference(REFLECTOR_JS);
	}
	
	@Override
	public void onComponentTag(Component arg0, ComponentTag arg1) {
		super.onComponentTag(arg0, arg1);
		
		String currentClass = "";
		if(arg1.getString("class") != null)
			currentClass = arg1.getString("class").toString();
		arg1.put("class", currentClass + " reflect");
	}
	
	@Override
	public String mooFunction() {
		// TODO Auto-generated method stub
		return null;
	}

}
