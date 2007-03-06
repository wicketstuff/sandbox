package wicket.contrib.dojo.cometd;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.markup.html.IHeaderResponse;
/**
 * <b>WARNING : This component is a prototype, Do NOT use it. It is still internal</b><br/>
 * {@link DojoSharedForm} uses Cometd to make it usable you should use an other servlet named cometd:
 * <pre>
 */
public class CometdBehavior extends AbstractRequireDojoBehavior {

	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		//FIXME, make it mode generic
		response.renderJavascript("cometd.init({}, \"/cometd\");cometd.subscribe(\"/test\", false, 'receive');function receive(message){alert(message.data.test)}", "cometd");
	}

	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.io.cometd");

	}

	protected void respond(AjaxRequestTarget target) {
		// TODO Auto-generated method stub

	}

}
