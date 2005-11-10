package wicket.contrib.markup.html.form.validation;

import wicket.Component;
import wicket.contrib.markup.html.tooltip.Tooltip;
import wicket.feedback.ComponentFeedbackMessageFilter;
import wicket.feedback.IFeedbackMessageFilter;
import wicket.markup.html.panel.FeedbackPanel;

public class FXFeedbackTooltip extends Tooltip
{
	
		
	public FXFeedbackTooltip(String id, Component target)
	{
		super(id, target, 10,20);
		final FXTooltipFeedbackPanel feedback = new FXTooltipFeedbackPanel("feedbackpanel", getTarget());
		add(feedback);
	}
	
	private class FXTooltipFeedbackPanel extends FeedbackPanel
	{
		/** The message filter for this indicator component */
		private IFeedbackMessageFilter filter;
				
		public FXTooltipFeedbackPanel(String id, Component c)
		{
			super(id);
			 filter = new ComponentFeedbackMessageFilter(c);
		}
		
		/**
		 * @return Let subclass specify some other filter
		 */
		protected IFeedbackMessageFilter getFeedbackMessageFilter()
		{
			return filter;
		}
		
	}
}
