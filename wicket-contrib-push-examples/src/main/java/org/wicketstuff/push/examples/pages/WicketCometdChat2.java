package org.wicketstuff.push.examples.pages;

import java.util.Map;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.push.IPushTarget;
import org.wicketstuff.push.PushEvent;
import org.wicketstuff.push.timer.TimerPushBehavior;
import org.wicketstuff.push.timer.TimerPushPublisher;

public class WicketCometdChat2 extends WebPage{

	public WicketCometdChat2(PageParameters parameters)
	{
		Message model = new Message();
		
		final Form formChat = new Form("chatForm", new CompoundPropertyModel(model));
		
		TextField field = new TextField("user");
		field.setOutputMarkupId(false);
		formChat.add(field);
		
		final Label chat = new Label("chat");
		chat.setOutputMarkupId(true);
		chat.add(new TimerPushBehavior(Duration.seconds(2), "chat/message"){
			
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
				PushEvent event = new PushEvent("chat/message");
				event.addData("message", currentChat);
				new TimerPushPublisher().publish(event);
				
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
