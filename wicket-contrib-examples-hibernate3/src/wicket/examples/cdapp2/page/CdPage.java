package wicket.examples.cdapp2.page;

import wicket.examples.cdapp2.CdRequestCycle;
import wicket.examples.cdapp2.model.Dao;
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
