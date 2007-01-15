package wicket.contrib.scriptaculous.dragdrop;

import java.util.HashMap;
import java.util.Map;

import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.scriptaculous.JavascriptBuilder;
import wicket.contrib.scriptaculous.ScriptaculousAjaxBehavior;
import wicket.contrib.scriptaculous.JavascriptBuilder.JavascriptFunction;
import wicket.contrib.scriptaculous.effects.Effect;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebMarkupContainer;

/**
 * 
 * @see http://wiki.script.aculo.us/scriptaculous/show/Droppables.add
 */
public abstract class DraggableTarget<T> extends WebMarkupContainer<T>
{
	private static final long serialVersionUID = 1L;
	private final ScriptaculousAjaxBehavior onDropBehavior;
	private final Map<String, Object> dropOptions = new HashMap<String, Object>();

	public DraggableTarget(MarkupContainer parent, String id)
	{
		super(parent, id);

		setOutputMarkupId(true);
		this.onDropBehavior = new ScriptaculousAjaxBehavior()
		{
			private static final long serialVersionUID = 1L;

			public void onRequest()
			{
				String input = getRequest().getParameter("id");
				AjaxRequestTarget target = new AjaxRequestTarget();
				getRequestCycle().setRequestTarget(target);
				target.addComponent(DraggableTarget.this);
				target.appendJavascript(new Effect.Highlight(DraggableTarget.this).toJavascript());

				onDrop(input, target);
			}

		};
		add(onDropBehavior);

		dropOptions.put("onDrop", new JavascriptFunction("function() { wicketAjaxGet('"
				+ onDropBehavior.getCallbackUrl() + "'); }"));
	}

	protected abstract void onDrop(String input, AjaxRequestTarget target);

	public void accepts(DraggableImage image)
	{
		// TODO: this should build a string array of classes so that one target
		// can accept multiple classes.
		dropOptions.put("accept", image.getStyleClass());
	}

	protected void onRender(MarkupStream markupStream)
	{
		super.onRender(markupStream);

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("Droppables.add('" + getMarkupId() + "', ");
		builder.addOptions(dropOptions);
		builder.addLine(");");

		getResponse().write(builder.buildScriptTagString());
	}
}
