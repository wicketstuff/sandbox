package org.wicketstuff.push.cometd.sharedform;

import java.util.HashMap;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.wicketstuff.dojo.templates.DojoPackagedTextTemplate;

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
