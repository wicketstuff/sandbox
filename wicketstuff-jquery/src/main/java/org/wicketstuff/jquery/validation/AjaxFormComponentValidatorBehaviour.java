package org.wicketstuff.jquery.validation;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.feedback.ErrorLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.jquery.JQueryBehavior;

import java.util.List;

/**
 *
 * Ajax validation behaviour that triggers when the selected event
 * is fired. The default action is to add a css class to the parent
 * element of the component and add any error messages in a span
 * directly after the component. You can change this behaviour
 * by overriding getErrorJavascript().
 *
 * By default it uses JQuery to do the markup manipulation. You can
 * either skip jquery or load another library by overriding the
 * renderHead method.
 *
 * This behaviour will also update the model object when the selected
 * client side event fires.
 *
 * @author Edvin Syse <edvin@sysedata.no>
 * @created 2008-06-01
 *
 */
public class AjaxFormComponentValidatorBehaviour extends AjaxFormComponentUpdatingBehavior {
	public static final String ERROR_COMPONENT_CLASS = "error";
	public static final String ERROR_MESSAGE_CLASS = "error";
	private boolean errorDetected;

	/* Default constructor, attaches to the onblur clientside event */
	public AjaxFormComponentValidatorBehaviour() {
		this("onblur");
	}

	public AjaxFormComponentValidatorBehaviour(String event) {
		super(event);
		errorDetected = false;
	}

	/**
	 * When the component renders, onUpdate will remove the
	 * markup containing any previous error-message.
	 * @param target
	 */
	protected void onUpdate(AjaxRequestTarget target) {
		if(errorDetected) {
			// Remove the error-class on the surrounding element
			target.appendJavascript("$('#" + getComponent().getMarkupId() + "').removeClass('" + ERROR_COMPONENT_CLASS + "');");

			// Remove the previously added error-messages
			target.appendJavascript(getRemovePreviousErrorsScript());
		}
	}

	/**
	 * Javascript used to remove previously shown error messages.
	 *
	 * @return
	 */
	protected String getRemovePreviousErrorsScript() {
		return "$('#" + getComponent().getMarkupId() + "').next().remove();";
	}

	/**
	 * Adds the error-javascript to the response since the component
	 * has errors.
	 *
	 * @param target The AjaxRequestTarget you can add components to
	 * @param e The exception if any
	 */
	protected void onError(AjaxRequestTarget target, RuntimeException e) {
		super.onError(target, e);
		target.appendJavascript(getErrorJavascript());
		errorDetected = true;
	}

	/**
	 * Returns the javascript that will manipulate the markup to
	 * show the error message. Defaults to adding a CSS-class to the
	 * parent element (nice if you add a <p> or <div> around your
	 * label/component)
	 * @return
	 */
	protected String getErrorJavascript() {
		StringBuilder b = new StringBuilder();

		// Remove the previously added error-messages
		if(errorDetected)
			b.append(getRemovePreviousErrorsScript());

		// Add the ERROR class to the sourrounding component
		b.append("$('#" + getComponent().getMarkupId() + "').addClass('" + ERROR_COMPONENT_CLASS + "');");

		// Create list of error messages, separated by the chosen separator markup
		List<FeedbackMessage> messages = Session.get().getFeedbackMessages().messages(ErrorLevelFeedbackMessageFilter.ALL);
		StringBuilder mb = new StringBuilder("");

		for(int i = 0; i < messages.size(); i++ ) {
			String msg = messages.get(i).getMessage().toString().replace("'", "\\'");
			mb.append(msg + getErrorSeparator());
		}

		// Add the span with the error messages
		b.append("$('#" + getComponent().getMarkupId() + "').after('<span class=\"" + ERROR_MESSAGE_CLASS + "\">" + mb.toString() + "</span>');");
		return b.toString();
	}

	/**
	 * The error separator is used to differentiate each error-message
	 * in case a component has more than one error message
	 *
	 * Defaults to separating using a <br/> tag.
	 *
	 * @return the markup for separating multiple error message
	 */
	protected String getErrorSeparator() {
		return "<br/>";
	}

	/** Add the JQuery library so we can use it in the onError method
	 * to manipulate the markup.
	 *
	 * @param response
	 */
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavascriptReference(JQueryBehavior.JQUERY_JS);
	}
}