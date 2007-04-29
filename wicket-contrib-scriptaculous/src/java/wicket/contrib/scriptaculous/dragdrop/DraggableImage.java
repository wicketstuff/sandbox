package wicket.contrib.scriptaculous.dragdrop;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.image.Image;

import wicket.contrib.scriptaculous.JavascriptBuilder;
import wicket.contrib.scriptaculous.ScriptaculousAjaxBehavior;

public abstract class DraggableImage extends Image
{
	private static final long serialVersionUID = 1L;

	public DraggableImage(String wicketId, String img)
	{
		super(wicketId, img);

		setOutputMarkupId(true);
		add(ScriptaculousAjaxBehavior.newJavascriptBindingBehavior());
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("class", getStyleClass());
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
