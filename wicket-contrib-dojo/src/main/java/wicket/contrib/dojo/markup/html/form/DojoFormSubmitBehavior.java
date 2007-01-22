package wicket.contrib.dojo.markup.html.form;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractDefaultDojoBehavior;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.FormComponent;
import wicket.util.string.AppendingStringBuffer;

/**
 * Ajax event behavior that submits a form via ajax when the event it is
 * attached to is invoked.
 * <p>
 * The form must have an id attribute in the markup or have MarkupIdSetter
 * added.
 * </p>
 * 
 * @author vdemay
 *
 */
public abstract class DojoFormSubmitBehavior extends AbstractDefaultDojoBehavior
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Form form;


	/**
	 * Construct.
	 * 
	 * @param form
	 *            form that will be submitted
	 * @param event
	 *            javascript event this behavior is attached to, like onclick
	 */
	public DojoFormSubmitBehavior(Form form)
	{
		super();
		this.form = form;
	}

	protected CharSequence getEventHandler()
	{
		final String formId = form.getMarkupId();
		final CharSequence url = getCallbackUrl();


		AppendingStringBuffer call = new AppendingStringBuffer("wicketSubmitFormById('").append(
				formId).append("', '").append(url).append("', ");

		if (getComponent() instanceof Button)
		{
			call.append("'").append(((FormComponent)getComponent()).getInputName()).append("' ");
		}
		else
		{
			call.append("null");
		}

		return getCallbackScript(call, null, null) + ";";
	}
	
	protected void respond(AjaxRequestTarget target)
	{
		form.onFormSubmitted();
		if (!form.hasError())
		{
			onSubmit(target);
		}
		else
		{
			onError(target);
		}	
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
        // return false to end event processing in case the DojoLink is bound to a <button> contained in a form
        tag.put("onclick", getEventHandler() + "; return false;");
	}

	/**
	 * Listener method that is invoked after the form has ben submitted and
	 * processed without errors
	 * 
	 * @param target
	 */
	protected abstract void onSubmit(AjaxRequestTarget target);

	/**
	 * Listener method invoked when the form has been processed and errors
	 * occured
	 * 
	 * @param target
	 * 
	 * TODO 1.3: make abstract to be consistent with onsubmit()
	 * 
	 */
	protected void onError(AjaxRequestTarget target)
	{

	}

}
