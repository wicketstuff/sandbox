package org.wicketstuff.dojo.examples;

import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.page.DojoPageContainer;
import org.wicketstuff.dojo.markup.html.container.split.DojoSplitContainer;
import org.apache.wicket.markup.html.WebPage;

public class SplitContainerSample extends WebPage {

	public SplitContainerSample() {
		super();
		DojoSplitContainer container = new DojoSplitContainer("splitContainer");
		add(container);
		container.setOrientation(DojoSplitContainer.ORIENTATION_VERTICAL);
		container.setHeight("500px");
		container.add(new DojoSimpleContainer("tab1", "title1"));
		container.add(new DojoSimpleContainer("tab2", "title2"));
		DojoPageContainer page = new DojoPageContainer("tab3", DatePickerShower.class);
		page.setTitle("title3");
		container.add(page);
		
	}

}
