package wicket.contrib.dojo.markup.html.form.suggestionlist;

import wicket.MarkupContainer;
import wicket.WicketRuntimeException;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.IModel;
import static wicket.contrib.dojo.DojoIdConstants.*;

/**
 * DojoInline suggestion list is a suggestion list withour server request.
 * It use a select tag and option tag to render the componant.
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public class DojoInlineSuggestionList extends WebMarkupContainer
{
	/**
	 * Create a DojoInlineSuggestionList
	 * @param parent parent where this component is rendered 
	 * @param id component id
	 * @param model model associated with the component
	 */
	@SuppressWarnings("unchecked")
	public DojoInlineSuggestionList(MarkupContainer parent, String id, IModel model){
		super(parent, id, model);
		add(new DojoInlineSuggestionListHandler());
	}

	/**
	 * Create a DojoInlineSuggestionList
	 * @param parent parent where this component is rendered 
	 * @param id component id
	 */
	public DojoInlineSuggestionList(MarkupContainer parent, String id){
		super(parent, id);
		add(new DojoInlineSuggestionListHandler());
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		checkTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_COMBOBOX);
	}
	
	protected void checkTag(ComponentTag tag){
		if ("select".equals(tag.getName())){
			throw new WicketRuntimeException("DojoInlineSuggestionList " + getMarkupId() + " expected a select tag but found a " + tag.getName() + "tag.");
		}
	}

}
