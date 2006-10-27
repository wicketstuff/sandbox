package contrib.wicket.cms.panel.renderer;

import wicket.Application;
import wicket.AttributeModifier;
import wicket.MarkupContainer;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.Panel;
import wicket.model.CompoundPropertyModel;
import wicket.model.Model;
import wicket.spring.injection.SpringBean;
import contrib.wicket.cms.initializer.CMSInitializer;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.model.ContentType;
import contrib.wicket.cms.panel.editor.ContentEditorPanel;
import contrib.wicket.cms.security.ContentAuthorizationStrategy;
import contrib.wicket.cms.service.ContentService;

public class ContentRendererPanel extends Panel {

	@SpringBean
	ContentService contentService;

	boolean showForm = false;

	ContentEditorPanel contentEditorPanel;

	HtmlContentDataRendererPanel contentDataRendererPanel;

	public ContentRendererPanel(MarkupContainer<?> parent, final String id,
			final Content content) {
		super(parent, id, new CompoundPropertyModel(content));

		setOutputMarkupId(true);

		contentEditorPanel = new ContentEditorPanel(this, "contentEditor",
				content) {
			@Override
			public boolean isVisible() {
				return showForm;
			}
		};

		// Renderer Panel
		final WebMarkupContainer highlightContainer = new WebMarkupContainer(
				this, "highlightContainer");
		highlightContainer.add(new AttributeModifier("class", true, new Model(
				"highlight")) {
			@Override
			public boolean isEnabled() {
				// TODO: Add a preference for users to determine if they want
				// this behavior
				ContentAuthorizationStrategy strategy = (ContentAuthorizationStrategy) Application
						.get()
						.getMetaData(
								CMSInitializer.CONTENT_AUTHORIZATION_STRATEGY_KEY);
				return strategy.hasWriteAccess(content);
			}
		});

		if (content.getContentType().getId().equals(ContentType.TEXT)
				|| content.getContentType().getId().equals(ContentType.HTML)) {
			contentDataRendererPanel = new HtmlContentDataRendererPanel(
					highlightContainer, "content", content);
		}

		// Edit Link
		Link edit = new Link(this, "edit") {

			@Override
			public void onClick() {
				showForm = true;
				contentEditorPanel.setVisible(false);
				highlightContainer.setVisible(false);
			}

			@Override
			public boolean isVisible() {
				ContentAuthorizationStrategy strategy = (ContentAuthorizationStrategy) Application
						.get()
						.getMetaData(
								CMSInitializer.CONTENT_AUTHORIZATION_STRATEGY_KEY);

				return !showForm && strategy.hasWriteAccess(content);
			}
		};
	}

	public void setContent(Content content) {
		showForm = false;
		contentEditorPanel.setContent(content);
		setModel(new CompoundPropertyModel(content));
	}

}
