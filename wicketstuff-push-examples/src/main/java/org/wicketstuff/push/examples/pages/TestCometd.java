package org.wicketstuff.push.examples.pages;

import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.dojo.markup.html.DojoLink;
import org.wicketstuff.push.ChannelEvent;
import org.wicketstuff.push.IChannelListener;
import org.wicketstuff.push.IChannelService;
import org.wicketstuff.push.IChannelTarget;
import org.wicketstuff.push.cometd.CometdBehavior;
import org.wicketstuff.push.cometd.CometdEvent;
import org.wicketstuff.push.cometd.CometdPublisher;
import org.wicketstuff.push.cometd.CometdService;

public class TestCometd extends ExamplePage {

	private TextField field;
	private String val;
	
	public TestCometd(PageParameters parameters)
	{
		final IChannelService channelService = getCometdService();
		
		DojoLink link = new DojoLink("link"){

			public void onClick(AjaxRequestTarget target) {
				channelService.publish(new ChannelEvent("myChannel"));
			}
			
		};	
		add(link);
		
		field = new TextField("text", new Model(val));
		field.setOutputMarkupId(true);
		channelService.addChannelListener(this, "myChannel", new IChannelListener() {
			public void onEvent(String channel, Map<String, String> datas, IChannelTarget target) {
				field.setModel(new Model("updated"));
				target.addComponent(field);
			}
		});
		add(field);
	}
}
