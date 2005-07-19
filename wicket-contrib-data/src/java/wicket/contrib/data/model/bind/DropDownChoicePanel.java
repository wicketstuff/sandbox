package wicket.contrib.data.model.bind;

import java.util.Collection;

import wicket.model.IModel;

/**
 * A panel for a drop down choice.
 * 
 * @author Phil Kulak
 */
public class DropDownChoicePanel extends InlineValidatingPanel
{
	public DropDownChoicePanel(String id, IModel model, Collection choices)
	{
		super(id);
		InlineDropDownChoice choice = new InlineDropDownChoice(
			"inlineDropDownChoice", model, choices);
		choice.setRenderBodyOnly(true);
		add(choice);
	}
}
