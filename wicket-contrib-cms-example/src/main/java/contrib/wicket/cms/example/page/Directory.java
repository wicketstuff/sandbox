package contrib.wicket.cms.example.page;

import wicket.PageParameters;
import wicket.spring.injection.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.model.ContentType;
import contrib.wicket.cms.panel.ContentNavigatorPanel;
import contrib.wicket.cms.service.ContentService;

public class Directory extends Template {

	@SpringBean
	private ContentService contentService;

	public Directory(PageParameters params) {

		String[] path = new String[params.size()];

		params.values().toArray(path);

		Content content = contentService.findFolderByPath(path);

		new ContentNavigatorPanel(this, "contentNavigator", content) {

			@Override
			public void onClick(Content content) {
				if (content.getContentType().getId().equals(ContentType.FOLDER)) {
					setFolder(content);
				} else {
					Home home = new Home(content);
					setResponsePage(home);
				}
			}

			@Override
			public int getPageSize() {
				return 10;
			}
		};

	}
}
