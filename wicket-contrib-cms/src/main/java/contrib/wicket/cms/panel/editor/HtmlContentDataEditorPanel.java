/**
 * 
 */
package contrib.wicket.cms.panel.editor;

import wicket.markup.html.form.TextArea;
import wicket.spring.injection.annot.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.service.ContentService;

class HtmlContentDataEditorPanel extends ContentDataEditorPanel {

	@SpringBean
	private ContentService contentService;

	public HtmlContentDataEditorPanel(String id, Content content) {
		super(id, content);

//		TinyMCESettings settings = new TinyMCESettings(Theme.advanced);
//
//		settings.setToolbarLocation(TinyMCESettings.Location.top);
//		settings.setHorizontalResizing(true);
//		settings.setVerticalResizing(true);
//		
//		add(new TinyMCEPanel("tinyMCE", settings));

		TextArea dataAsString = new TextArea("dataAsString");
		dataAsString.setRequired(true);
		add(dataAsString);
	}

	public void onSubmit(Content content) {
		contentService.saveContent(content);
	}

}