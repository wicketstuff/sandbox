/**
 * 
 */
package contrib.wicket.cms.panel.editor;

import wicket.MarkupContainer;
import wicket.markup.html.form.TextArea;
import wicket.spring.injection.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.service.ContentService;

class HtmlContentDataEditorPanel extends ContentDataEditorPanel {

	@SpringBean
	private ContentService contentService;

	public HtmlContentDataEditorPanel(MarkupContainer<?> parent, String id,
			Content content) {
		super(parent, id, content);

		// TinyMCESettings settings = new TinyMCESettings(Theme.advanced);
		//
		// settings.setToolbarLocation(TinyMCESettings.Location.top);
		// settings.setHorizontalResizing(true);
		// settings.setVerticalResizing(true);
		//		
		// add(new TinyMCEPanel("tinyMCE", settings));

		TextArea dataAsString = new TextArea(this, "dataAsString");
		dataAsString.setRequired(true);
	}

	public void onSubmit(Content content) {
		contentService.saveContent(content);
	}

}