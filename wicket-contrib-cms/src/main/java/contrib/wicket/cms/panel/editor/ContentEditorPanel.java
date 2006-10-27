/**
 * 
 */
package contrib.wicket.cms.panel.editor;

import wicket.MarkupContainer;
import wicket.WicketRuntimeException;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.markup.html.panel.Panel;
import wicket.model.CompoundPropertyModel;
import wicket.spring.injection.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.model.ContentType;
import contrib.wicket.cms.service.ContentService;
import contrib.wicket.cms.util.WicketUtil;

abstract public class ContentEditorPanel extends Panel {

	ContentDataEditorPanel contentDataEditorPanel;

	public ContentEditorPanel(MarkupContainer<?> parent, String id,
			Content content) {
		super(parent, id, new CompoundPropertyModel(content));
		new ContentEditorForm(this, "form", content);
	}

	public class ContentEditorForm extends Form {

		@SpringBean
		ContentService contentService;

		public ContentEditorForm(MarkupContainer<?> parent, String id,
				Content content) {
			super(parent, id);

			if (content == null || content.getContentType() == null
					|| content.getFolder() == null) {
				throw new WicketRuntimeException(
						"content, content.contentType and content.folder must not be null");
			}

			new FeedbackPanel(this, "feedback");

			TextField name = new TextField(this, "name");
			name.setRequired(true);
			WicketUtil.addErrorClassAttributeModifier(name);

			if (content.getContentType().getId().equals(ContentType.TEXT)) {
				contentDataEditorPanel = new TextContentDataEditorPanel(this,
						"contentEditor", content);
			} else if (content.getContentType().getId()
					.equals(ContentType.HTML)) {
				contentDataEditorPanel = new HtmlContentDataEditorPanel(this,
						"contentEditor", content);
			} else if (content.getContentType().getId().equals(
					ContentType.FOLDER)) {
				contentDataEditorPanel = new FolderContentDataEditorPanel(this,
						"contentEditor", content);
			}
		}

		@Override
		protected void onSubmit() {
			contentDataEditorPanel.onSubmit(getContent());
			ContentEditorPanel.this.onSubmit();
		}

	}

	public Content getContent() {
		return (Content) getModelObject();
	}

	public void setContent(Content content) {
		setModel(new CompoundPropertyModel(content));
		contentDataEditorPanel.setContent(content);
	}

	public abstract void onSubmit();
	
}