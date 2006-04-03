/**
 * 
 */
package contrib.wicket.cms.panel.editor;

import wicket.WicketRuntimeException;
import wicket.contrib.tinymce.TinyMCEPanel;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.markup.html.panel.Panel;
import wicket.model.CompoundPropertyModel;
import wicket.model.Model;
import wicket.spring.injection.annot.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.model.ContentType;
import contrib.wicket.cms.service.ContentService;
import contrib.wicket.cms.util.WicketUtil;

public class ContentEditorPanel extends Panel {

	ContentDataEditorPanel contentDataEditorPanel;
	
	public ContentEditorPanel(String id, Content content) {
		super(id, new CompoundPropertyModel(content));
		add(new ContentEditorForm("form", content));
	}

	public class ContentEditorForm extends Form {

		@SpringBean
		ContentService contentService;

		public ContentEditorForm(String id, Content content) {
			super(id);

			if (content == null || content.getContentType() == null
					|| content.getFolder() == null) {
				throw new WicketRuntimeException(
						"content, content.contentType and content.folder must not be null");
			}

			add(new FeedbackPanel("feedback"));

			TextField name = new TextField("name");
			name.setRequired(true);
			WicketUtil.addErrorClassAttributeModifier(name);
			add(name);

			if (content.getContentType().getId().equals(ContentType.TEXT)) {
				contentDataEditorPanel = new TextContentDataEditorPanel(
						"contentEditor", content);
			} else if (content.getContentType().getId().equals(
					ContentType.HTML)) {
				contentDataEditorPanel = new HtmlContentDataEditorPanel(
						"contentEditor", content);
			} else if (content.getContentType().getId().equals(
					ContentType.FOLDER)) {
				contentDataEditorPanel = new FolderContentDataEditorPanel(
						"contentEditor", content);
			}

			add(contentDataEditorPanel);			
		}

		@Override
		protected void onSubmit() {
			contentDataEditorPanel.onSubmit(getContent());
		}

	}

	public Content getContent() {
		return (Content) getModelObject();
	}

	public void setContent(Content content) {
		setModel(new CompoundPropertyModel(content));
		contentDataEditorPanel.setContent(content);
	}

}