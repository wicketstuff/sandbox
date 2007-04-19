package wicket.examples.cdapp.page;

import org.apache.wicket.examples.cdapp.CdRequestCycle;
import org.apache.wicket.examples.cdapp.model.Dao;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class CdPage extends WebPage {
	public CdPage(String title) {
		add(new Label("cdPage_title", title));
	}
	
	public Dao getDao() {
		return ((CdRequestCycle) getRequestCycle()).getDao();
	}
}
