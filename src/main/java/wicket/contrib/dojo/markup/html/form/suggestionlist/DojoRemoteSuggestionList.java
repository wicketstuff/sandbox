package wicket.contrib.dojo.markup.html.form.suggestionlist;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_COMBOBOX;
import wicket.MarkupContainer;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.TextField;
import wicket.model.IModel;

/**
 * Suggestion list that request the server to know which item
 * sould be display with the user input in the widget
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public abstract class DojoRemoteSuggestionList extends TextField
{

	/**
	 * Construct a suggestion list
	 * @param parent parent where the suggestion is rendered
	 * @param id component id
	 * @param model model associated with the widget : TODO pt here a suggestionList???
	 */
	public DojoRemoteSuggestionList(MarkupContainer parent, String id, IModel model)
	{
		super(parent, id, model);
		add(new DojoRemoteSuggestionListHandler());
	}

	/**
	 * Construct a suggestion list
	 * @param parent parent where the suggestion is rendered
	 * @param id component id
	 */
	public DojoRemoteSuggestionList(MarkupContainer parent, String id)
	{
		super(parent, id);
		add(new DojoRemoteSuggestionListHandler());
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_COMBOBOX);
	}
	
	/**
	 * Return a {@link SuggestionList} filtered by the input in pattern
	 * @param pattern user input
	 * @return list of elemtn to displayed
	 */
	public abstract SuggestionList getMatchingValues(String pattern);
	
}
