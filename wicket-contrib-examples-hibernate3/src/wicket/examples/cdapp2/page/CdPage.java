package wicket.examples.cdapp2.page;

import wicket.examples.cdapp2.CdRequestCycle;
import wicket.examples.cdapp2.model.Dao;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;

/**
 * The base class for pages in this application. This uses markup inheritance
 * to supply a header and footer.
 * 
 * @author Phil Kulak
 */
public class CdPage extends WebPage {
	public CdPage(String title) {
		add(new Label("cdPage_title", title));
	}
	
    /**
     * A convenient way to make our DAO available to our pages.
     * 
     * @return the DAO for this request
     */
	public Dao getDao() {
		return ((CdRequestCycle) getRequestCycle()).getDao();
	}
}
