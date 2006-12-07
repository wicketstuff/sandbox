package wicket.contrib.dojo.markup.html.inlineeditbox;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_INLINE_EDIT_BOX;
import wicket.MarkupContainer;
import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebComponent;
import wicket.model.Model;

/**
 * Dojo inlineEditBox widget for wicket
 * @author Gregory Maes
 */
public class DojoInlineEditBox extends WebComponent<String> {

	/**
	 * Construct a Dojo Inline Edit Box
	 * @param parent parent where the inlineEditBox will be added
	 * @param id id of the inlineEditBox
	 * @param label Default Value
	 */
	public DojoInlineEditBox(MarkupContainer parent, final String id, String label) {
		super(parent, id, new Model<String>(label));
		add(new DojoInlineEditBoxHandler());
	}
	
	/**
	 * set the dojoType 
	 * @param tag 
	 */
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_INLINE_EDIT_BOX);
		tag.put("templatePath", urlFor(new ResourceReference(DojoInlineEditBox.class, "InlineEditBox.htm")));
	}
	
	/** To initialize the text field with the model value */
	@Override
	protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
		replaceComponentTagBody(markupStream, openTag, getModelObjectAsString());
	}
	
	/**
	 * To be overridden for custom action
	 * This function is called after having updated the model
	 * @param target {@link AjaxRequestTarget}
	 */
	protected void onSave(AjaxRequestTarget target) {
		
	}
	
}
