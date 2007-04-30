package org.wicketstuff.scriptaculous.dragdrop;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.wicketstuff.scriptaculous.JavascriptBuilder;
import org.wicketstuff.scriptaculous.ScriptaculousAjaxBehavior;

/**
 * defines a image that a user can drag/drop
 * 
 * @TODO: should this be defined as a behavior that can be attached to any component?
 * 
 * @see DraggableTarget
 */
public abstract class DraggableImage extends Image
{
	private static final long serialVersionUID = 1L;

	public DraggableImage(String wicketId, String img)
	{
		super(wicketId, img);

		setOutputMarkupId(true);
		add(ScriptaculousAjaxBehavior.newJavascriptBindingBehavior());
		add(new AttributeAppender("class", new Model(getStyleClass()), " "));
	}

	/**
	 * define the css style used to define this component. used by the draggable
	 * target to declare what it accepts.
	 * 
	 * @see DraggableTarget#accepts(DraggableImage)
	 * @return
	 */
	protected abstract String getStyleClass();

	protected void onRender(MarkupStream markupStream)
	{
		super.onRender(markupStream);

		JavascriptBuilder builder = new JavascriptBuilder();
		Map options = new HashMap()
		{
			private static final long serialVersionUID = 1L;
			{
				put("revert", Boolean.TRUE);
			}
		};
		builder.addLine("new Draggable('" + getMarkupId() + "', ");
		builder.addOptions(options);
		builder.addLine(");");
		getResponse().write(builder.buildScriptTagString());
	}
}
