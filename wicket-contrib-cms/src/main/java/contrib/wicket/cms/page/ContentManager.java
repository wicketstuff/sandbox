package contrib.wicket.cms.page;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import wicket.PageParameters;
import wicket.markup.html.WebPage;
import wicket.spring.injection.annot.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.model.ContentType;
import contrib.wicket.cms.panel.ContentNavigatorPanel;
import contrib.wicket.cms.panel.renderer.ContentRendererPanel;
import contrib.wicket.cms.service.ContentService;

public class ContentManager extends WebPage {

	@SpringBean
	private ContentService contentService;

	public ContentManager(PageParameters params) {
		String[] path = new String[params.size()];

		params.values().toArray(path);

		Content content = contentService.findFolderByPath(path);

		if (content == null) {
			// TODO: Create parent folders
			content = new Content();
			content.setFolder(contentService.getRootFolder());
			content.setContentType(contentService
					.getContentType(ContentType.TEXT));
			content.setDataAsString("Edit new content...");
			content.setName(path[path.length - 1]);
		}

		final ContentRendererPanel contentPanel = new ContentRendererPanel(
				"content", content);
		add(contentPanel);

		add(new ContentNavigatorPanel("contentNavigator", content) {

			@Override
			public void onClick(Content content) {
				contentPanel.setContent(content);
			}

			@Override
			public int getPageSize() {
				return 10;
			}
		});

	}

}
