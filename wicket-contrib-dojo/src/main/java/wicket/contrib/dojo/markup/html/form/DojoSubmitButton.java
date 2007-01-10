package wicket.contrib.dojo.markup.html.form;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.indicator.behavior.DojoIndicatorBehavior;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;

/**
 * * A button that submits the form via ajax. Since this button takes the form as
 * a constructor argument it does not need to be added to it unlike the
 * {@link Button} component.
 * 
 * This Button can accept a {@link DojoIndicatorBehavior} to display a special indicator 
 * between the ajax request and the respond
 * 
 * @author vdemay
 *
 */
public abstract class DojoSubmitButton extends Button
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param id id in the markup
	 * @param form form associated with this Submit
	 */
	public DojoSubmitButton(String id, final Form form)
	{
		super(id);
		add(new DojoFormSubmitBehavior(form){
			private static final long serialVersionUID = 1L;

			protected void onSubmit(AjaxRequestTarget target)
			{
				DojoSubmitButton.this.onSubmit(target, form);
			}
			
			protected void onError(AjaxRequestTarget target)
			{
				DojoSubmitButton.this.onError(target, form);
			}
		});
	}
	
	protected void onComponentTag(ComponentTag tag)
	{
		checkComponentTag(tag, "input");

		final String type = tag.getAttributes().getString("type");
		if (!"button".equals(type) && !"image".equals(type) && !"submit".equals(type))
		{
			findMarkupStream().throwMarkupException(
					"Component " + getId() + " must be applied to a tag with 'type'"
							+ " attribute matching 'submit', 'button' or 'image', not '" + type
							+ "'");
		}

		super.onComponentTag(tag);
	}

	/**
	 * Listener method invoked on form submit with no errors
	 * 
	 * @param target
	 * @param form
	 */
	protected abstract void onSubmit(AjaxRequestTarget target, Form form);

	/**
	 * Listener method invoked on form submit with errors
	 * 
	 * @param target
	 * @param form
	 * 
	 * TODO 1.3: Make abstract to be consistent with onsubmit()
	 */
	protected void onError(AjaxRequestTarget target, Form form) {
		
	}

}
