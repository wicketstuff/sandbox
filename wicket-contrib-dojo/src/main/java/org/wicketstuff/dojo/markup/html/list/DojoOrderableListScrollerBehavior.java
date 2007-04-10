package org.wicketstuff.dojo.markup.html.list;

import org.apache.wicket.Component;
import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.IBehaviorListener;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;

public class DojoOrderableListScrollerBehavior extends AbstractBehavior implements IHeaderContributor{

	private Component scrollComponent;
	private String scrollId;
	
	public DojoOrderableListScrollerBehavior(Component scrollComponent) {
		super();
		this.scrollComponent = scrollComponent;
	}
	
	public DojoOrderableListScrollerBehavior(String scrollId) {
		super();
		this.scrollId = scrollId;
	}

	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(new ResourceReference(DojoOrderableListScrollerBehavior.class, "DojoOrderableListScrollerBehaviorTemplate.js"));
		String js;
		if (scrollComponent != null){
			js= "dojo.event.connect(dojo, \"loaded\", function(){ lookupScrolling(\"" + scrollComponent.getMarkupId() + "\")});";
		}else{
			js= "dojo.event.connect(dojo, \"loaded\", function(){ lookupScrolling(\"" + scrollId + "\")});";
		}
		response.renderJavascript(js, js);
	}
	
	public void updateScrollComponent(Component scrollComponent){
		this.scrollComponent = scrollComponent;
	}

	public void onRendered(Component component) {
		super.onRendered(component);
		
		IRequestTarget target = RequestCycle.get().getRequestTarget();
		if (target instanceof AjaxRequestTarget){
			AjaxRequestTarget current = (AjaxRequestTarget) target;
			if (scrollComponent != null){
				current.appendJavascript("put = false; lookupScrolling(\"" + scrollComponent.getMarkupId() + "\");");
			}else{
				current.appendJavascript("put = false; lookupScrolling(\"" + scrollId + "\");");
			}
			
		}
	}
}
