package org.wicketstuff.push.cometd.sharedform;

import java.util.HashMap;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.push.cometd.dojo.AbstractRequireDojoBehavior;
import org.wicketstuff.push.cometd.dojo.DojoPackagedTextTemplate;

public class DojoSharedFormBehavior extends AbstractRequireDojoBehavior {
  private static final long serialVersionUID = 1L;

  String channel;

  public DojoSharedFormBehavior(final String channel) {
    super();
    this.channel = channel;
  }

  public void setRequire(final RequireDojoLibs libs) {
    libs.add("dijit.form.Form");
    libs.add("dojox.cometd");
  }

  protected void respond(final AjaxRequestTarget target) {
    // NOTHING TO DO
  }

  public void renderHead(final IHeaderResponse response) {
    super.renderHead(response);
    final DojoPackagedTextTemplate template =
        new DojoPackagedTextTemplate(DojoSharedFormBehavior.class,
            "DojoSharedFromBehaviorTemplate.js");
    final HashMap map = new HashMap();
    map.put("form", getComponent().getMarkupId());
    map.put("channel", channel);
    response.renderJavascript(template.asString(map), template
        .getWidgetUniqueKey(getComponent()));
  }

}
