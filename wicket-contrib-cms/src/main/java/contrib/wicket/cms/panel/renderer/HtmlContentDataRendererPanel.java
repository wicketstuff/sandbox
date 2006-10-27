package contrib.wicket.cms.panel.renderer;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.panel.Panel;
import wicket.spring.injection.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.service.ContentService;

public class HtmlContentDataRendererPanel extends Panel {

	@SpringBean
	ContentService contentService;

	public HtmlContentDataRendererPanel(MarkupContainer<?> parent, final String id, Content content) {
		super(parent, id);
		new Label(this, "dataAsString").setEscapeModelStrings(false);
	}

}
