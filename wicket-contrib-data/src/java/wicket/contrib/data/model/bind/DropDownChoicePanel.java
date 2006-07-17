package wicket.contrib.data.model.bind;

import java.util.List;

import wicket.MarkupContainer;
import wicket.model.IModel;

/**
 * A panel for a drop down choice.
 * 
 * @author Phil Kulak
 */
public class DropDownChoicePanel<T> extends InlineValidatingPanel<T>
{
	private static final long serialVersionUID = 1L;

	public DropDownChoicePanel(MarkupContainer parent, String id, IModel<T> model,
			List<T> choices)
	{
		super(parent, id);
		InlineDropDownChoice<T> choice = new InlineDropDownChoice<T>(this,
				"inlineDropDownChoice", model, choices);
		choice.setRenderBodyOnly(true);
	}
}
