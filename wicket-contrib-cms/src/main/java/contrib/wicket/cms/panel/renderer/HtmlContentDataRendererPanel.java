package contrib.wicket.cms.panel.renderer;

import wicket.markup.html.basic.Label;
import wicket.markup.html.panel.Panel;
import wicket.spring.injection.annot.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.service.ContentService;

public class HtmlContentDataRendererPanel extends Panel {

	@SpringBean
	ContentService contentService;

	public HtmlContentDataRendererPanel(final String id, Content content) {
		super(id);
		add(new Label("dataAsString"));
	}

}
