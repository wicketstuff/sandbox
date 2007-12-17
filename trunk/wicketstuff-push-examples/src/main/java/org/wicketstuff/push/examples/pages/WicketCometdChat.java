package org.wicketstuff.push.examples.pages;

import org.apache.wicket.PageParameters;
import org.wicketstuff.push.IChannelService;

public class WicketCometdChat extends WicketAbstractChat {
	private static final long serialVersionUID = 1L;

	public WicketCometdChat(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected IChannelService getChannelService() {
		return getCometdService();
	}
}
