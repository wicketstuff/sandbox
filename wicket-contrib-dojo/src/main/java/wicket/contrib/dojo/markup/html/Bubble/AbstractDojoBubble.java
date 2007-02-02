package wicket.contrib.dojo.markup.html.Bubble;

import wicket.Component;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.widgets.StylingWebMarkupContainer;
import wicket.model.IModel;

public  class AbstractDojoBubble extends StylingWebMarkupContainer{
	
	public AbstractDojoBubble(String id, IModel model) {
		super(id, model);
	}

	public AbstractDojoBubble(String id) {
		super(id);
	}

	public void show(AjaxRequestTarget target){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').show()");
	}
	
	public void hide(AjaxRequestTarget target){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').hide()");
	}
	
	public void place(AjaxRequestTarget target, Component c){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').stickTo('" + c.getMarkupId() + "')");
	}
	
	public void place(AjaxRequestTarget target, String s){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').stickTo('" + s + "')");
	}
	
	public void setMessage (AjaxRequestTarget target, String mess){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').setMessage(\"" + mess + "\")");
	}
}
