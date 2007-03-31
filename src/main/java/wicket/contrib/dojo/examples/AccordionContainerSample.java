package wicket.contrib.dojo.examples;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.markup.html.container.DojoSimpleContainer;
import wicket.contrib.dojo.markup.html.container.IDojoContainer;
import wicket.contrib.dojo.markup.html.container.accordion.DojoAccordionContainer;
import wicket.contrib.dojo.markup.html.container.page.DojoPageContainer;
import wicket.markup.html.WebPage;

public class AccordionContainerSample extends WebPage {

	public AccordionContainerSample() {
		super();
		DojoAccordionContainer container = new DojoAccordionContainer("tabContainer"){
			@Override
			public void onSelectionChange(IDojoContainer selected, AjaxRequestTarget target) {
				System.out.println(selected.getMarkupId());
			}
		};
		container.setHeight("500px");
		container.add(new DojoSimpleContainer("tab1", "title1"));
		container.add(new DojoSimpleContainer("tab2", "title2"));
		container.add(new DojoPageContainer("tab3", "an other page", SliderSample.class));
		this.add(container);
	}

}
