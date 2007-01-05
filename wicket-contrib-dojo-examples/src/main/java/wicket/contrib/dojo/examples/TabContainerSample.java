package wicket.contrib.dojo.examples;

import wicket.contrib.dojo.markup.html.container.DojoSimpleContainer;
import wicket.contrib.dojo.markup.html.container.page.DojoPageContainer;
import wicket.contrib.dojo.markup.html.container.tab.DojoTabContainer;
import wicket.markup.html.WebPage;

public class TabContainerSample extends WebPage {

	public TabContainerSample() {
		super();
		DojoTabContainer container = new DojoTabContainer("tabContainer");
		add(container);
		container.setHeight("500px");
		container.add(new DojoSimpleContainer("tab1", "title1"));
		container.add(new DojoSimpleContainer("tab2", "title2"));
		
		DojoPageContainer page = new DojoPageContainer("tab3", DatePickerShower.class);
		page.setTitle("title3");
		container.add(page);
		
	}

}
