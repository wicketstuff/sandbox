package org.wicketstuff.scriptaculous.dragdrop;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.Model;
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
public abstract class DraggableBehavior extends ScriptaculousAjaxBehavior
{
	private static final long serialVersionUID = 1L;
	private Map options = new HashMap();

	protected void onBind() {
		super.onBind();

		getComponent().setOutputMarkupId(true);
		getComponent().add(new AttributeAppender("class", new Model(getDraggableClassName()), " "));
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
		//no callback...yet
	}

	/**
	 * define the css style used to define this component.
	 * used by the {@link DraggableTarget} to declare what
	 * classes it accepts.
	 *
	 * @see DraggableTarget#accepts(DraggableImage)
	 * @return
	 */
	public abstract String getDraggableClassName();

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
