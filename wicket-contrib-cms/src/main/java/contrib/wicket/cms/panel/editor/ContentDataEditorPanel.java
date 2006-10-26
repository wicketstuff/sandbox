/**
 * 
 */
package contrib.wicket.cms.panel.editor;

import wicket.MarkupContainer;
import wicket.markup.html.panel.Panel;
import wicket.model.CompoundPropertyModel;
import contrib.wicket.cms.model.Content;

abstract public class ContentDataEditorPanel extends Panel {

	public ContentDataEditorPanel(MarkupContainer<?> parent, String id, Content content) {
		super(parent, id, new CompoundPropertyModel(content));
	}

	public Content getContent() {
		return (Content) getModelObject();
	}

	public void setContent(Content content) {
		setModel(new CompoundPropertyModel(content));
	}

	abstract void onSubmit(Content content);
	
}