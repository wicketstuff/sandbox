package wicket.contrib.markup.html.form.validation;

import wicket.markup.html.form.validation.FormComponentFeedbackIndicator;

public class FXFeedbackIndicator extends FormComponentFeedbackIndicator
{
	private FXFeedbackTooltip feedbackTooltip;
	
	public FXFeedbackIndicator(String id)
	{
		super(id);
		feedbackTooltip = new FXFeedbackTooltip("feedbacktooltip", this);
		add(feedbackTooltip);
	}

}
