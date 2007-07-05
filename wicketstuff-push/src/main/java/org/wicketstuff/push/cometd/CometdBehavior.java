package org.wicketstuff.push.cometd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.dojo.templates.DojoPackagedTextTemplate;
import org.wicketstuff.push.IChannelListener;

public class CometdBehavior extends CometdAbstractBehavior {
	private static final long serialVersionUID = 1L;

	private IChannelListener listener;

	public CometdBehavior(String channelId, IChannelListener listener) {
		super(channelId);
		this.listener = listener;
	}

	@Override
	public final String getCometdInterceptorScript() {
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
		listener.onEvent(getChannelId(), eventAttribute, cTarget);
	}

}
