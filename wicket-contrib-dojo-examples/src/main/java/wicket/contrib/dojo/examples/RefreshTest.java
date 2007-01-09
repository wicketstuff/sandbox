package wicket.contrib.dojo.examples;

import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.markup.html.AjaxLink;
import wicket.contrib.dojo.markup.html.container.DojoSimpleContainer;
import wicket.contrib.dojo.markup.html.container.split.DojoSplitContainer;
import wicket.contrib.dojo.markup.html.container.tab.DojoTabContainer;
import wicket.markup.html.WebPage;

public class RefreshTest extends WebPage {

	public RefreshTest() {
		
		super();
		
		final DojoTabContainer page= new DojoTabContainer("tab3", "tab inside");
		
		
		final DojoSplitContainer container = new DojoSplitContainer("splitContainer");
		add(container);
		container.setOrientation(DojoSplitContainer.ORIENTATION_VERTICAL);
		container.setHeight("500px");
		
		DojoSimpleContainer first = new DojoSimpleContainer("tab1", "title1");
		first.add(new AjaxLink("ajaxlink"){
			public void onClick(AjaxRequestTarget target) {
				//THIS A HACK TO AVOID A RESIZE BUG
				target.appendJavascript("dojo.event.connect(dojo.widget.byId('" + container.getMarkupId() + "'), 'onResize', function() {dojo.widget.byId('" + page.getMarkupId() + "').onResized()});");
				target.addComponent(page);
			}
		});
		container.add(first);
		
		container.add(new DojoSimpleContainer("tab2", "title2"));
		
		DojoSimpleContainer containerOuter = new DojoSimpleContainer("tab3outer","tab3");
		page.add(new DojoSimpleContainer("tab31", "tab31"));
		page.add(new DojoSimpleContainer("tab32", "tab32"));
		page.add(new DojoSimpleContainer("tab33", "tab33"));
		page.add(new DojoSimpleContainer("tab34", "tab34"));
		containerOuter.add(page);
		page.setHeight("100%");
		container.add(containerOuter);
		containerOuter.setHeight("100%");
		
	}

}
