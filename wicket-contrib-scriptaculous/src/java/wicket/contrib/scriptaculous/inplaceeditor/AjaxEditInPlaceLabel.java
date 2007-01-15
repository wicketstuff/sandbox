package wicket.contrib.scriptaculous.inplaceeditor;

import java.util.HashMap;
import java.util.Map;

import wicket.RequestCycle;
import wicket.ajax.AjaxRequestTarget;
import wicket.behavior.AbstractAjaxBehavior;
import wicket.contrib.scriptaculous.JavascriptBuilder;
import wicket.contrib.scriptaculous.ScriptaculousAjaxBehavior;
import wicket.contrib.scriptaculous.effects.Effect;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.form.AbstractTextComponent;
import wicket.markup.html.form.FormComponent;
import wicket.model.IModel;
import wicket.request.target.basic.StringRequestTarget;

/**
 * @see http://wiki.script.aculo.us/scriptaculous/show/Ajax.InPlaceEditor
 * 
 * @author <a href="mailto:wireframe6464@sf.net">Ryan Sonnek</a>
 */
public class AjaxEditInPlaceLabel<T> extends AbstractTextComponent<T>
{
	private static final long serialVersionUID = 1L;
	private AbstractAjaxBehavior callbackBehavior;
	private AbstractAjaxBehavior onCompleteBehavior;
	private Map<String, Object> options = new HashMap<String, Object>();

	public AjaxEditInPlaceLabel(WebMarkupContainer parent, String wicketId, IModel<T> model)
	{
		super(parent, wicketId);
		setModel(model);

		this.callbackBehavior = new ScriptaculousAjaxBehavior()
		{
			private static final long serialVersionUID = 1L;

			public void onRequest()
			{
				FormComponent formComponent = (FormComponent)getComponent();
				formComponent.validate();
				if (formComponent.isValid())
				{
					formComponent.updateModel();
				}
				RequestCycle.get().setRequestTarget(new StringRequestTarget(getDisplayValue()));
			}
		};
		add(callbackBehavior);

		this.onCompleteBehavior = new ScriptaculousAjaxBehavior()
		{
			private static final long serialVersionUID = 1L;

			public void onRequest()
			{
				AjaxRequestTarget target = new AjaxRequestTarget();
				getRequestCycle().setRequestTarget(target);
				target.appendJavascript(new Effect.Highlight(AjaxEditInPlaceLabel.this)
						.toJavascript());

				onComplete(target);
			}

		};
		add(onCompleteBehavior);

		options.put("onComplete", new JavascriptBuilder.JavascriptFunction(
				"function() { wicketAjaxGet('" + onCompleteBehavior.getCallbackUrl() + "'); }"));
		setOutputMarkupId(true);
	}

	public String getInputName()
	{
		return "value";
	}

	/**
	 * configure use of okButton for InPlaceEditor.
	 * 
	 * @param value
	 */
	public void setOkButton(boolean value)
	{
		options.put("okButton", Boolean.valueOf(value));
	}

	public void setCancelLink(boolean value)
	{
		options.put("cancelLink", Boolean.valueOf(value));
	}

	public void setExternalControl(WebMarkupContainer control)
	{
		options.put("externalControl", control.getMarkupId());
	}

	public void setSubmitOnBlur(boolean value)
	{
		options.put("submitOnBlur", Boolean.valueOf(value));
	}

	public void setRows(int rows)
	{
		options.put("rows", new Integer(rows));
	}

	public void setCols(int cols)
	{
		options.put("cols", new Integer(cols));
	}

	public void setSize(int size)
	{
		options.put("size", new Integer(size));
	}

	/**
	 * extension point for customizing what text is loaded for editing.
	 * 
	 * @see #getDisplayValue()
	 */
	public void setLoadBehavior(AbstractAjaxBehavior loadBehavior)
	{
		add(loadBehavior);

		addOption("loadTextURL", loadBehavior.getCallbackUrl());
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
	protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
	{
		replaceComponentTagBody(markupStream, openTag, getDisplayValue());
	}

	/**
	 * extension point to allow for manipulation of what value is displayed.
	 * Overriding this method allows for the component to display different text
	 * than what is edited. This may be useful when the display text is
	 * formatted differently than the editable text (ex: textile). The default
	 * behavior is to return the same value as the <code>getValue()</code>
	 * method.
	 * 
	 * @see #getValue();
	 * @see #setLoadBehavior(AbstractAjaxBehavior)
	 */
	protected String getDisplayValue()
	{
		return getValue();
	}

	protected void onRender(MarkupStream markupStream)
	{
		super.onRender(markupStream);

		JavascriptBuilder builder = new JavascriptBuilder();
		builder.addLine("new Ajax.InPlaceEditor('" + getMarkupId() + "', ");
		builder.addLine("  '" + callbackBehavior.getCallbackUrl() + "', ");
		builder.addOptions(options);
		builder.addLine(");");
		getResponse().write(builder.buildScriptTagString());
	}

	/**
	 * extension point to override default onComplete behavior.
	 */
	protected void onComplete(final AjaxRequestTarget target)
	{
	}

	protected void addOption(String key, Object value)
	{
		options.put(key, value);
	}
}
