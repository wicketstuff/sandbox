package wicket.contrib.dojo.cometd;

import java.util.HashMap;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.templates.DojoPackagedTextTemplate;
import wicket.markup.html.IHeaderResponse;

public class DojoSharedFormBehavior extends AbstractRequireDojoBehavior {

	String channel;
	
	public DojoSharedFormBehavior(String channel) {
		super();
		this.channel = channel;
	}

	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.Form");
		libs.add("dojo.json");
		libs.add("dojo.io.cometd");
	}

	protected void respond(AjaxRequestTarget target) {
		//NOTHING TO DO
	}

	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		DojoPackagedTextTemplate template = new DojoPackagedTextTemplate(DojoSharedFormBehavior.class,"DojoSharedFromBehaviorTemplate.js" );
		HashMap map = new HashMap();
		map.put("form", getComponent().getMarkupId());
		map.put("channel", this.channel);
		response.renderJavascript(template.asString(map), template.getWidgetUniqueKey(getComponent()));
	}

}
