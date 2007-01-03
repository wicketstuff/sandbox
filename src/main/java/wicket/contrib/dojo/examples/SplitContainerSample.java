package wicket.contrib.dojo.examples;

import wicket.contrib.dojo.markup.html.container.DojoSimpleContainer;
import wicket.contrib.dojo.markup.html.container.page.DojoPageContainer;
import wicket.contrib.dojo.markup.html.container.split.DojoSplitContainer;
import wicket.markup.html.WebPage;

public class SplitContainerSample extends WebPage {

	public SplitContainerSample() {
		super();
		DojoSplitContainer container = new DojoSplitContainer(this,"splitContainer");
		container.setOrientation(DojoSplitContainer.ORIENTATION_VERTICAL);
		container.setHeight("500px");
		new DojoSimpleContainer(container, "tab1", "title1");
		new DojoSimpleContainer(container, "tab2", "title2");
		new DojoPageContainer(container, "tab3", DatePickerShower.class).setTitle("title3");
		
	}

}
