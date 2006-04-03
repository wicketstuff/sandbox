/**
 * 
 */
package contrib.wicket.cms.panel.editor;

import wicket.spring.injection.annot.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.service.ContentService;

class FolderContentDataEditorPanel extends ContentDataEditorPanel {

	@SpringBean
	private ContentService contentService;

	public FolderContentDataEditorPanel(String id, final Content content) {
		super(id, content);

		// Currently no custom editing
	}

	public void onSubmit(Content content) {
		contentService.saveContent(content);
	}

}