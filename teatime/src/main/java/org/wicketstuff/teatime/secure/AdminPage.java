package org.wicketstuff.teatime.secure;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.teatime.BasePage;
import org.wicketstuff.teatime.WicketApplication;
import org.wicketstuff.teatime.logbot.LogBot;

/**
 * Page for administration of the bot.
 */
@AuthorizeInstantiation(Roles.USER)
public class AdminPage extends BasePage {
	private static final long serialVersionUID = 1L;

	public AdminPage(PageParameters parameters) {
		super(parameters);
		setModel(new CompoundPropertyModel(this));
		add(new Label("application.server"));
		add(new Label("application.channel"));
		add(new Label("application.nick"));
		add(new Label("application.ircLogDir"));

		add(new CheckBox("application.bot.connected"));
		add(new Label("application.bot.server"));
		add(new Label("application.bot.channels[0]"));
		add(new Label("application.bot.nick"));

		add(new IndicatingAjaxFallbackLink("reconnect") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				WicketApplication.get().reconnect();
			}
		});

		add(new TextField("send", new Model("")).add(
				new AjaxFormComponentUpdatingBehavior("onchange") {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						String message = getFormComponent()
								.getModelObjectAsString();
						LogBot bot = WicketApplication.get().getBot();
						bot.sendMessage(bot.getChannels()[0], message);
						getFormComponent().setModelObject("");
						target.addComponent(getFormComponent());
					}
				}).setOutputMarkupId(true));
	}

	public String getTitle() {
		return "Admin page for Wicket logbot";
	}
}
