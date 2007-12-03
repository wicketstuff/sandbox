package org.wicketstuff.teatime.logbot;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.teatime.WicketApplication;

/**
 * Panel for showing logbot statistics.
 * 
 * @author dashorst
 */
public class LogBotPanel extends Panel {
	/** */
	private static final long serialVersionUID = 1L;

	public LogBotPanel(String id) {
		super(id);
		setModel(new CompoundPropertyModel(this));
		add(new Label("application.bot.server"));
		add(new Label("application.bot.channels[0]"));
		add(new Label("application.bot.nick"));
		add(new CheckBox("application.bot.connected"));
	}
	
	public LogBot getBot() {
		return WicketApplication.get().getBot();
	}
}
