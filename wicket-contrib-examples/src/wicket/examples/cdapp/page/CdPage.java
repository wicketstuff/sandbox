package wicket.examples.cdapp.page;

import wicket.examples.cdapp.CdRequestCycle;
import wicket.examples.cdapp.model.Dao;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;

public class CdPage extends WebPage {
	public CdPage(String title) {
		add(new Label("cdPage_title", title));
	}
	
	public Dao getDao() {
		return ((CdRequestCycle) getRequestCycle()).getDao();
	}
}
