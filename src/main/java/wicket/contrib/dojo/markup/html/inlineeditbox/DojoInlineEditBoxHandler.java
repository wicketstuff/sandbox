package wicket.contrib.dojo.markup.html.inlineeditbox;

import java.util.HashMap;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.extensions.util.resource.PackagedTextTemplate;
import wicket.markup.html.IHeaderResponse;

/**
 * Dojo inlineEditBox behavior
 */
public class DojoInlineEditBoxHandler extends AbstractRequireDojoBehavior {
	
	/** connect the onSave function of dojo with the wicket callbackUrl */
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		PackagedTextTemplate packagedTextTemplate = new PackagedTextTemplate(this.getClass(), "InlineEditBox.js");
		HashMap map = new HashMap();
		map.put("callbackUrl", getCallbackUrl());
		map.put("markupId", getComponent().getMarkupId());	
		response.renderJavascript(packagedTextTemplate.asString(map), null);
	}

	/** set the required libraries for the inlineEditBox dojo widget
	 * @param libs the dojo libraries to be included
	 */
	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.InlineEditBox");
		libs.add("dojo.event.*");
	}

	/** recover the text value and set it in the model */
	protected void respond(AjaxRequestTarget target) {
		String textValue = getComponent().getRequest().getParameter("newTextValue");
		getComponent().setModelObject(textValue);
		((DojoInlineEditBox)getComponent()).onSave(target);
	}

}
