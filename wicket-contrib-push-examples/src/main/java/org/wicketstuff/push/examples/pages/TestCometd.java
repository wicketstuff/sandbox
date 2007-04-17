package org.wicketstuff.push.examples.pages;

import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.dojo.markup.html.DojoLink;
import org.wicketstuff.push.IPushTarget;
import org.wicketstuff.push.cometd.CometdBehavior;
import org.wicketstuff.push.cometd.CometdEvent;
import org.wicketstuff.push.cometd.CometdPublisher;

public class TestCometd extends WebPage{

	private TextField field;
	private String val;
	
	public TestCometd(PageParameters parameters)
	{
		DojoLink link = new DojoLink("link"){

			public void onClick(AjaxRequestTarget target) {
				/*RequestCycle requestCycle = RequestCycle.get();
				
				Response savedResponse = requestCycle.getResponse();
				
				StringResponse response = new StringResponse();
				requestCycle.setResponse(response);
				field.renderComponent();
				
				requestCycle.setResponse(savedResponse);
				CometdPublisher publisher = new CometdPublisher((WebApplication)getApplication());
				HashMap message = new HashMap();
				message.put(field.getMarkupId().toString(),response.toString());
				publisher.publish("myChannel", message);*/
				
				CometdPublisher publisher = new CometdPublisher((WebApplication)getApplication());
				CometdEvent event = new CometdEvent("myChannel");
				publisher.publish(event);
				
			}
			
		};	
		add(link);
		
		field = new TextField("text", new Model(val));
		field.setOutputMarkupId(true);
		
		field.add(new CometdBehavior("myChannel"){

			public void onEvent(String channel, Map<String, String> datas, IPushTarget target) {
				field.setModel(new Model("updated"));
				target.addComponent(field);
			}
			
		});
		add(field);
	}
}
