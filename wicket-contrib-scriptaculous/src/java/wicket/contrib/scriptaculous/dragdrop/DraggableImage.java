package wicket.contrib.scriptaculous.dragdrop;

import java.util.HashMap;
import java.util.Map;

import wicket.MarkupContainer;
import wicket.contrib.scriptaculous.JavascriptBuilder;
import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.image.Image;

public class DraggableImage extends Image
{
	private static final long serialVersionUID = 1L;
	private final String id;

	public DraggableImage(MarkupContainer parent, String wicketId, String id, String img)
	{
		super(parent, wicketId, img);
		this.id = id;

		add(ScriptaculousAjaxHandler.newJavascriptBindingHandler());
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("id", id);
		tag.put("class", getId());
	}

	protected void onRender(MarkupStream markupStream)
	{
		super.onRender(markupStream);

		JavascriptBuilder builder = new JavascriptBuilder();
		Map options = new HashMap() {{
			put("revert", Boolean.TRUE);
		}};
		builder.addLine("new Draggable('" + id + "', ");
		builder.addOptions(options);
		builder.addLine(");");
		getResponse().write(builder.buildScriptTagString());
	}
}
