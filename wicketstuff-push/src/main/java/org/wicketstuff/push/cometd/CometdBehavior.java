package org.wicketstuff.push.cometd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.cometd.dojo.DojoPackagedTextTemplate;

public class CometdBehavior extends CometdAbstractBehavior {
	private static final long serialVersionUID = 1L;

	private final IChannelListener listener;

	public CometdBehavior(final String channelId, final IChannelListener listener) {
		super(channelId);
		this.listener = listener;
	}

	@Override
	public final String getCometdInterceptorScript() {
		final HashMap<String, String> map = new HashMap<String, String>();
		map.put("markupId", getComponent().getMarkupId());
		map.put("url", getCallbackUrl().toString());
		return new DojoPackagedTextTemplate(CometdBehavior.class, "CometdDefaultBehaviorTemplate.js")
						.asString(map);
	}

	@Override
	public final CharSequence getPartialSubscriber() {
		return "'onEventFor"+ getComponent().getMarkupId() + "'";
	}

	@SuppressWarnings("unchecked")
  @Override
	protected final void respond(final AjaxRequestTarget target) {
		final Map map = ((WebRequestCycle)RequestCycle.get()).getRequest().getParameterMap();
		final Iterator it = map.keySet().iterator();
		final HashMap<String, String> eventAttribute = new HashMap<String, String>();
		while(it.hasNext()){
			final String key = (String)it.next();
			eventAttribute.put(key, ((String[])map.get(key))[0]);
		}
		final CometdTarget cTarget = new CometdTarget(target);
		listener.onEvent(getChannelId(), eventAttribute, cTarget);
	}

}
