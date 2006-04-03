/**
 * 
 */
package contrib.wicket.cms.panel.editor;

import wicket.contrib.tinymce.TinyMCEPanel;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.markup.html.form.TextArea;
import wicket.model.Model;
import wicket.spring.injection.annot.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.service.ContentService;

class HtmlContentDataEditorPanel extends ContentDataEditorPanel {

	@SpringBean
	private ContentService contentService;

	public HtmlContentDataEditorPanel(String id, Content content) {
		super(id, content);

		TinyMCESettings settings = new TinyMCESettings(
				TinyMCESettings.Theme.simple);

		settings.setToolbarLocation(TinyMCESettings.Location.top);

		add(new TinyMCEPanel("tinyMCE", settings));

		TextArea dataAsString = new TextArea("dataAsString");
		dataAsString.setRequired(true);
		add(dataAsString);
	}

	public void onSubmit(Content content) {
		contentService.saveContent(content);
	}

}