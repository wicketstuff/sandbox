package wicket.contrib.scriptaculous.inplaceeditor;

import java.util.Map;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.RequestCycle;
import wicket.behavior.AbstractAjaxBehavior;
import wicket.contrib.scriptaculous.JavascriptBuilder;
import wicket.contrib.scriptaculous.ScriptaculousAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.form.AbstractTextComponent;
import wicket.markup.html.form.FormComponent;
import wicket.model.IModel;
import wicket.request.target.basic.StringRequestTarget;

/**
 * 
 * @author <a href="mailto:wireframe6464@users.sourceforge.net">Ryan Sonnek</a>
 */
public class AjaxEditInPlaceLabel extends AbstractTextComponent
{
	private static final long serialVersionUID = 1L;
	private AbstractAjaxBehavior handler;
	private Map options;

	private static class InPlaceEditorAjaxHandler extends ScriptaculousAjaxHandler
	{
		private static final long serialVersionUID = 1L;

		public void onRequest(Component component)
		{
			FormComponent formComponent = (FormComponent)getComponent();
			formComponent.validate();
			if (formComponent.isValid())
			{
				formComponent.updateModel();
			}
			String value = formComponent.getValue();

			RequestCycle.get().setRequestTarget(new StringRequestTarget(value));
		}
	}

	public AjaxEditInPlaceLabel(MarkupContainer parent, String id, IModel model)
	{
		super(parent, id);
		setModel(model);

		this.handler = new InPlaceEditorAjaxHandler();
		add(handler);

		setOutputMarkupId(true);
	}

	public String getInputName()
	{
		return "value";
	}

	/**
	 * Handle the container's body.
	 * 
	 * @param markupStream
	 *            The markup stream
	 * @param openTag
	 *            The open tag for the body
	 * @see wicket.Component#onComponentTagBody(MarkupStream, ComponentTag)
	 */
	protected final void onComponentTagBody(final MarkupStream markupStream,
			final ComponentTag openTag)
	{
		replaceComponentTagBody(markupStream, openTag, getValue());
	}

	protected void onRender(MarkupStream markupStream)
	{
		super.onRender(markupStream);

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("new Ajax.InPlaceEditor('" + getMarkupId() + "', ");
		builder.addLine("  '" + handler.getCallbackUrl() + "', ");
		builder.addOptions(options);
		builder.addLine(");");
		getResponse().write(builder.buildScriptTagString());
	}

	public void setOptions(Map options)
	{
		this.options = options;
	}
}
