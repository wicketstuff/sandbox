package org.wicketstuff.teatime;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.teatime.logbot.LogBotPanel;
import org.wicketstuff.teatime.menu.CalendarMenu;
import org.wicketstuff.teatime.secure.UserPanel;

/**
 * 
 */
public abstract class BasePage extends WebPage {
	public BasePage(PageParameters parameters) {
		super(parameters);

		CompoundPropertyModel model = new CompoundPropertyModel(this);
        setModel(model);
		
        add(new Label("title"));
        add(new CalendarMenu("menu").setRenderBodyOnly(true));
	}
    public String getTitle() {
        return "Teatime irc logbot";
    }
}
