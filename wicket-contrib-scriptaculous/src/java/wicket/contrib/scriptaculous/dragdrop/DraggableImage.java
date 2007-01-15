package wicket.contrib.scriptaculous.dragdrop;

import java.util.HashMap;
import java.util.Map;

import wicket.MarkupContainer;
import wicket.contrib.scriptaculous.JavascriptBuilder;
import wicket.contrib.scriptaculous.ScriptaculousAjaxBehavior;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.image.Image;

public abstract class DraggableImage extends Image
{
	private static final long serialVersionUID = 1L;

	public DraggableImage(MarkupContainer parent, String wicketId, String img)
	{
		super(parent, wicketId, img);

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
		Map<String, Object> options = new HashMap<String, Object>()
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
