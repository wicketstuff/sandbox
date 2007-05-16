package org.wicketstuff.dojo.examples.bubble;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.markup.html.DojoLink;
import org.wicketstuff.dojo.markup.html.Bubble.DojoBubble;
import org.apache.wicket.markup.html.WebPage;

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
