package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.markup.html.DojoLink;
import wicket.contrib.dojo.markup.html.Bubble.DojoBubble;
import wicket.markup.html.WebPage;

public class BubbleSample extends WebPage {
	
	public BubbleSample(PageParameters parameters){
		final DojoBubble bubble = new DojoBubble("bubble");
		add(bubble);
		
		add(new DojoLink("link"){
			public void onClick(AjaxRequestTarget target) {
				bubble.place(target, "stick");
				bubble.show(target);
			}
		});
	}
}
