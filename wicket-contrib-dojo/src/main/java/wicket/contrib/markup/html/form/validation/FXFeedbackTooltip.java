package wicket.contrib.markup.html.form.validation;

import wicket.Component;
import wicket.contrib.markup.html.tooltip.Tooltip;
import wicket.contrib.markup.html.tooltip.TooltipPanel;
import wicket.markup.html.form.FormComponent;


/**
 * Tooltip class used by FXFeedbackIndicator.
 * This class can also be used to create a 
 * Tooltip which displays errormessages for 
 * a chosen formcomponent.
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 *
 */
public class FXFeedbackTooltip extends TooltipPanel
{
	
		
	/**
	 * @param target target Component @see Tooltip.java
	 */
	public FXFeedbackTooltip(Component target)
	{
		super(target, 10,20);
		
	}
	

	/**
	 * @see Tooltip constructor
	 * 
	 * @param target target Component
	 * @param x x offset
	 * @param y y offset
	 */
	public FXFeedbackTooltip(Component target, int x, int y)
	{
		super(target, x,y);
		
	}


	public void setComponentToCheck(Component component)
	{
		final FXTooltipFeedbackPanel feedback = new FXTooltipFeedbackPanel("feedbackpanel", (FormComponent)component);
		add(feedback);
	}

}
