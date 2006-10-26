/**
 * 
 */
package contrib.wicket.cms.panel.editor;

import wicket.MarkupContainer;
import wicket.markup.html.form.TextArea;
import wicket.spring.injection.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.service.ContentService;

class TextContentDataEditorPanel extends ContentDataEditorPanel {

	@SpringBean
	private ContentService contentService;

	public TextContentDataEditorPanel(MarkupContainer<?> parent, String id,
			final Content content) {
		super(parent, id, content);

		TextArea dataAsString = new TextArea(parent, "dataAsString");
		dataAsString.setRequired(true);
	}

	public void onSubmit(Content content) {
		contentService.saveContent(content);
	}

}