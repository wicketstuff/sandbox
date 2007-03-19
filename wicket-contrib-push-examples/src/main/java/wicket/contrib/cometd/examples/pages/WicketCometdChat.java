package wicket.contrib.cometd.examples.pages;

import java.util.Map;

import wicket.PageParameters;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.markup.html.form.AjaxSubmitButton;
import wicket.contrib.push.IPushTarget;
import wicket.contrib.push.cometd.CometdBehavior;
import wicket.contrib.push.cometd.CometdEvent;
import wicket.contrib.push.cometd.CometdPublisher;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.form.TextField;
import wicket.model.CompoundPropertyModel;
import wicket.protocol.http.WebApplication;

public class WicketCometdChat extends WebPage{

	public WicketCometdChat(PageParameters parameters)
	{
		Message model = new Message();
		
		final Form formChat = new Form("chatForm", new CompoundPropertyModel(model));
		
		TextField field = new TextField("user");
		field.setOutputMarkupId(false);
		formChat.add(field);
		
		final Label chat = new Label("chat");
		chat.setOutputMarkupId(true);
		chat.add(new CometdBehavior("chat/message"){
			
			public void onEvent(String channel, Map<String, String> datas, IPushTarget target) {
				target.appendJavascript("document.getElementById('" + chat.getMarkupId() + "').innerHTML += '<br/>" + datas.get("message") + "'");
			}
		});
		formChat.add(chat);
		
		final TextField mess = new TextField("message");
		mess.setOutputMarkupId(true);
		formChat.add(mess);
		
		formChat.add(new AjaxSubmitButton("send", formChat){
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				//Update message
				String currentChat =  
							((Message)form.getModelObject()).getUser() + " said " +
							((Message)form.getModelObject()).getMessage();
				//send an event to refesh the chat area
				CometdEvent event = new CometdEvent("chat/message");
				event.addData("message", currentChat);
				new CometdPublisher((WebApplication)getApplication()).publish(event);
				
				//clear message area add focus it
				target.appendJavascript("document.getElementById('" + mess.getMarkupId() + "').value =''");
				target.focusComponent(mess);
			}
		});
		add(formChat);
	}
	
	
	public class Message{
		private String chat;
		private String user;
		private String message;
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getChat() {
			return chat;
		}
		public void setChat(String chat) {
			this.chat = chat;
		}
	}
}
