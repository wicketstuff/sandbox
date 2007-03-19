package wicket.contrib.cometd.examples.pages;

import java.util.Map;

import wicket.PageParameters;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.markup.html.DojoLink;
import wicket.contrib.push.IPushTarget;
import wicket.contrib.push.cometd.CometdBehavior;
import wicket.contrib.push.cometd.CometdEvent;
import wicket.contrib.push.cometd.CometdPublisher;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.TextField;
import wicket.model.Model;
import wicket.protocol.http.WebApplication;

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
