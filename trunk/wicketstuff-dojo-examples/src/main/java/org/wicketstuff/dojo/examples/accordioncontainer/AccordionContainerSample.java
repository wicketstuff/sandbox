package org.wicketstuff.dojo.examples.accordioncontainer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.examples.slider.SliderSample;
import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
import org.wicketstuff.dojo.markup.html.container.IDojoContainer;
import org.wicketstuff.dojo.markup.html.container.accordion.DojoAccordionContainer;
import org.wicketstuff.dojo.markup.html.container.page.DojoPageContainer;
import org.apache.wicket.markup.html.WebPage;

public class AccordionContainerSample extends WicketExamplePage {

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
