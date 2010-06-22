package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

public class AbstractHtml5TextComponent<T> extends TextField<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * HTMLInputElement's attribute 'list'
	 */
	private String datalistId;
	
	/**
	 * HTMLInputElement's attribute 'placeholder'
	 */
	private IModel<String> placeholder;
	
	public AbstractHtml5TextComponent(String id) {
		super(id);
	}
	
	public AbstractHtml5TextComponent(String id, IModel<T> model) {
		super(id, model);
	}
	
	public AbstractHtml5TextComponent(String id, IModel<T> model, Class<T> type) {
		super(id, model, type);
	}

	public AbstractHtml5TextComponent(String id, IModel<T> model, String datalistId) {
		super(id, model);
		
		this.datalistId = datalistId;
	}
	
	public AbstractHtml5TextComponent(String id, IModel<T> model, Class<T> type, String datalistId) {
		super(id, model, type);
		
		this.datalistId = datalistId;
	}
	
	/**
	 * Processes the component tag.
	 * 
	 * @param tag
	 *            Tag to modify
	 * @see org.apache.wicket.Component#onComponentTag(ComponentTag)
	 */
	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);
		
		if (getPlaceholder() != null) {
			final String placeholderValue = getPlaceholder().getObject();
			if (!Strings.isEmpty(placeholderValue)) {
				tag.put("placeholder", placeholderValue);
			}
		}
		

		if (datalistId != null) {
			tag.put("list", datalistId);
		}
	}
	
	public AbstractHtml5TextComponent<T> setPlaceholder(final IModel<String> placeholder) {
		this.placeholder = placeholder;
		return this;
	}
	
	public IModel<String> getPlaceholder() {
		return placeholder;
	}
}
