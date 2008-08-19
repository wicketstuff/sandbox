package org.wicketstuff.push.cometd.sharedform;

import java.util.HashMap;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.push.cometd.CometdAbstractBehavior;
import org.wicketstuff.push.cometd.dojo.DojoPackagedTextTemplate;

public class DojoSharedFormBehavior extends CometdAbstractBehavior {
  private static final long serialVersionUID = 1L;

  public DojoSharedFormBehavior(final String channel) {
    super(channel);
  }

  public void setRequire(final RequireDojoLibs libs) {
    super.setRequire(libs);
    libs.add("dijit.form.Form");
  }

  protected void respond(final AjaxRequestTarget target) {
    // NOTHING TO DO
  }

  public String getCometdInterceptorScript() {
    final HashMap map = new HashMap();
    map.put("form", getComponent().getMarkupId());
    map.put("channel", getChannelId());
    return new DojoPackagedTextTemplate(DojoSharedFormBehavior.class,
        "DojoSharedFromBehaviorTemplate.js")
            .asString(map);
  }

  public CharSequence getPartialSubscriber() {
    return "DojoSharedFormJS.recieve";
  }

}
