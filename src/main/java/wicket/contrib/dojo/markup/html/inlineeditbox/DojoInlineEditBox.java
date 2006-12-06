package wicket.contrib.dojo.markup.html.inlineeditbox;

import wicket.Component;
import wicket.ResourceReference;
import wicket.WicketRuntimeException;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.markup.ComponentTag;
import wicket.markup.MarkupStream;
import wicket.markup.html.WebComponent;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * Dojo inlineEditBox widget for wicket
 */
public class DojoInlineEditBox extends WebComponent {

	public DojoInlineEditBox(final String id, String label) {
		super(id, new Model(label));
		add(new DojoInlineEditBoxHandler());
	}
	
	/** Only the String object is allowed */
	public Component setModel(IModel model)	{
		if (!(model.getObject(this) instanceof String)) {
			throw new WicketRuntimeException("Model for a DojoInlineEditBox should be a String instance");
		}
		return super.setModel(model);
	}
	

	/** set the dojoType */
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_INLINE_EDIT_BOX);
		tag.put("templatePath", urlFor(new ResourceReference(DojoInlineEditBox.class, "InlineEditBox.htm")));
	}
	
	/** To initialize the text field with the model value */
	protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
		replaceComponentTagBody(markupStream, openTag, getModelObjectAsString());
	}
	
	/**
	 * To be overridden for custom action
	 * This function is called after having updated the model
	 */
	protected void onSave(AjaxRequestTarget target) {
		
	}
	
}
