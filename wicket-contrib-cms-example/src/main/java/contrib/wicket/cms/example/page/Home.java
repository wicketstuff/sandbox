package contrib.wicket.cms.example.page;

import wicket.spring.injection.annot.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.model.ContentType;
import contrib.wicket.cms.panel.renderer.ContentRendererPanel;
import contrib.wicket.cms.service.ContentService;

public class Home extends Template {

	@SpringBean
	ContentService contentService;

	public Home() {
		init(contentService.findContentByName(contentService.getRootFolder(),
				"HomePage"));
	}

	public Home(Content content) {
		init(content);
	}

	public void init(Content content) {

		if (content == null) {
			content = new Content();
			content.setContentType(contentService
					.getContentType(ContentType.HTML));
			content.setFolder(contentService.getRootFolder());
			content.setDataAsString("Edit Here...");
			content.setName("HomePage");
		}

		ContentRendererPanel contentPanel = new ContentRendererPanel("content",
				content);
		add(contentPanel);
	}

}
