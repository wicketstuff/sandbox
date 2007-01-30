package wicket.contrib.dojo.markup.html.list;

import wicket.Component;
import wicket.IRequestTarget;
import wicket.RequestCycle;
import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.behavior.AbstractBehavior;
import wicket.behavior.IBehaviorListener;
import wicket.markup.html.IHeaderContributor;
import wicket.markup.html.IHeaderResponse;

public class DojoOrderableListScrollerBehavior extends AbstractBehavior implements IHeaderContributor{

	private Component scrollComponent;
	
	public DojoOrderableListScrollerBehavior(Component scrollComponent) {
		super();
		this.scrollComponent = scrollComponent;
	}

	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(new ResourceReference(DojoOrderableListScrollerBehavior.class, "DojoOrderableListScrollerBehaviorTemplate.js"));
		String js = "dojo.event.connect(dojo, \"loaded\", function(){ lookupScrolling(\"" + scrollComponent.getMarkupId() + "\")});";
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
			current.appendJavascript("lookupScrolling(\"" + scrollComponent.getMarkupId() + "\");");
		}
	}
}
