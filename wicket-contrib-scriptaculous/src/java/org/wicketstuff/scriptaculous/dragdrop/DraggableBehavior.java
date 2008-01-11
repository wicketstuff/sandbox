package org.wicketstuff.scriptaculous.dragdrop;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.scriptaculous.JavascriptBuilder;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;

/**
 * adds draggable behavior to any component.
 *
 * Can use a {@link DraggableTarget} to perform work when a Draggable object
 * is dropped on a component.
 *
 * @see http://wiki.script.aculo.us/scriptaculous/show/Draggable
 * @see DraggableTarget
 */
public class DraggableBehavior extends ScriptaculousAjaxBehavior
{
	private static final long serialVersionUID = 1L;
	private Map options = new HashMap();

	protected void onBind() {
		super.onBind();

		getComponent().setOutputMarkupId(true);
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
		//no callback...yet
	}

	protected void onComponentRendered() {
		super.onComponentRendered();

		Response response = RequestCycle.get().getResponse();

		options.put("revert", Boolean.TRUE);

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("new Draggable('" + getComponent().getMarkupId() + "', ");
		builder.addOptions(options);
		builder.addLine(");");
		response.write(builder.buildScriptTagString());
	}
}
