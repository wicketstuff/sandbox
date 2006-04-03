package contrib.wicket.cms.panel.renderer;

import wicket.Application;
import wicket.AttributeModifier;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.Panel;
import wicket.model.CompoundPropertyModel;
import wicket.model.Model;
import wicket.spring.injection.annot.SpringBean;
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

	public ContentRendererPanel(final String id, final Content content) {
		super(id, new CompoundPropertyModel(content));

		setOutputMarkupId(true);

		showForm = content.getId() == null;

		contentEditorPanel = new ContentEditorPanel("contentEditor", content) {
			@Override
			public boolean isVisible() {
				return showForm;
			}
		};
		add(contentEditorPanel);

		Link edit = new Link("edit") {

			@Override
			public void onClick() {
				showForm = true;
				contentEditorPanel.setVisible(false);
			}

			@Override
			public boolean isVisible() {
				ContentAuthorizationStrategy strategy = (ContentAuthorizationStrategy) Application
						.get()
						.getMetaData(
								CMSInitializer.CONTENT_AUTHORIZATION_STRATEGY_KEY);
				return strategy.hasWriteAccess(content);
			}
		};
		add(edit);

		WebMarkupContainer highlightContainer = new WebMarkupContainer(
				"highlightContainer");
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
		add(highlightContainer);

		if (content.getContentType().getId().equals(ContentType.TEXT)
				|| content.getContentType().getId().equals(ContentType.HTML)) {
			contentDataRendererPanel = new HtmlContentDataRendererPanel(
					"content", content);
		}

		highlightContainer.add(contentDataRendererPanel);
	}

	public void setContent(Content content) {
		showForm = false;
		contentEditorPanel.setContent(content);
		setModel(new CompoundPropertyModel(content));
	}

}
