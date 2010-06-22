package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.model.IModel;

public class Html5TextField<T> extends AbstractHtml5TextComponent<T> {

	private static final long serialVersionUID = 1L;

	public Html5TextField(String id) {
		super(id);
	}

	public Html5TextField(String id, IModel<T> model) {
		super(id, model);
	}
	
	public Html5TextField(String id, IModel<T> model, Class<T> type) {
		super(id, model, type);
	}

	public Html5TextField(String id, IModel<T> model, Class<T> type, String datalistId) {
		super(id, model, type, datalistId);
	}
}
