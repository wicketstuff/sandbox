package wicket.contrib.dojo.examples;

import wicket.contrib.dojo.markup.html.container.DojoSimpleContainer;
import wicket.contrib.dojo.markup.html.container.accordion.DojoAccordionContainer;
import wicket.contrib.dojo.markup.html.container.page.DojoPageContainer;
import wicket.markup.html.WebPage;

public class AccordionContainerSample extends WebPage {

	public AccordionContainerSample() {
		super();
		DojoAccordionContainer container = new DojoAccordionContainer(this,"tabContainer");
		container.setHeight("500px");
		new DojoSimpleContainer(container, "tab1", "title1");
		new DojoSimpleContainer(container, "tab2", "title2");
		new DojoPageContainer(container, "tab3", DatePickerShower.class).setTitle("title3");
		
	}

}
