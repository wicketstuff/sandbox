/**
 * 
 */
package contrib.wicket.cms.panel.editor;

import wicket.MarkupContainer;
import wicket.spring.injection.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.service.ContentService;

class FolderContentDataEditorPanel extends ContentDataEditorPanel {

	@SpringBean
	private ContentService contentService;

	public FolderContentDataEditorPanel(MarkupContainer<?> parent, String id,
			final Content content) {
		super(parent, id, content);

		// Currently no custom editing
	}

	public void onSubmit(Content content) {
		contentService.saveContent(content);
	}

}