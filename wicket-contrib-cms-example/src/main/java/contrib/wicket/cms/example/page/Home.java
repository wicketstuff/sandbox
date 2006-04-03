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
		Content resource = contentService.findContentByName(contentService
				.getRootFolder(), "HomePage");

		if (resource == null) {
			resource = new Content();
			resource.setContentType(contentService
					.getContentType(ContentType.HTML));
			resource.setFolder(contentService.getRootFolder());
			resource.setDataAsString("Edit Here...");
			resource.setName("HomePage");
		}

		ContentRendererPanel contentPanel = new ContentRendererPanel(
				"content", resource);
		add(contentPanel);

		// add(new TinyMCEPanel("tinyMCE"));
		//
		// add(new TextArea("dataAsString", new
		// Model(resource.getDataAsString())));

	}

}
