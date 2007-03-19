package wicket.contrib.push.cometd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import wicket.RequestCycle;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.templates.DojoPackagedTextTemplate;
import wicket.contrib.push.IPushBehavior;
import wicket.protocol.http.WebRequestCycle;

public abstract class CometdBehavior extends CometdAbstractBehavior implements IPushBehavior{

	public CometdBehavior(String channelId) {
		super(channelId);
	}

	@Override
	public final String getCometdIntercepteurScript() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("markupId", getComponent().getMarkupId());
		map.put("url", getCallbackUrl().toString());
		return new DojoPackagedTextTemplate(CometdBehavior.class, "CometdDefaultBehaviorTemplate.js")
						.asString(map);
	}

	@Override
	public final CharSequence getPartialSubscriber() {
		return "'onEventFor"+ getComponent().getMarkupId() + "'";
	}

	@Override
	protected final void respond(AjaxRequestTarget target) {
		Map map = ((WebRequestCycle)RequestCycle.get()).getRequest().getParameterMap();
		Iterator it = map.keySet().iterator();
		HashMap<String, String> eventAttribute = new HashMap<String, String>();
		while(it.hasNext()){
			String key = (String)it.next();
			eventAttribute.put(key, ((String[])map.get(key))[0]);
		}
		CometdTarget cTarget = new CometdTarget(target);
		onEvent(getChannelId(), eventAttribute, cTarget);
	}

}
