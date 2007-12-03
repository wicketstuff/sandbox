package org.wicketstuff.teatime.secure;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.teatime.TeatimeSession;

/**
 * 
 */
public class UserPanel extends Panel {
	/** */
	private static final long serialVersionUID = 1L;

	public UserPanel(String id) {
		super(id);
		setModel(new CompoundPropertyModel(this));
		setMarkupId("login");
		setOutputMarkupId(true);
		add(new LoginFragment("login"));
		add(new LogoutFragment("logout"));
	}

	public class LoginFragment extends Fragment {
		/** */
		private static final long serialVersionUID = 1L;
		private String username;
		private String password;

		public LoginFragment(String id) {
			super(id, "login", UserPanel.this);
			final Form form = new Form("form", new CompoundPropertyModel(this)) {
				/** */
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit() {
					TeatimeSession.get().login(username, password);
				}
			};
			final FormComponent field1 = new TextField("username")
					.setRequired(true);
			field1.add(new ErrorClassBehavior());
			form.add(field1);
			final FormComponent field2 = new PasswordTextField("password")
					.setRequired(true);
			field2.add(new ErrorClassBehavior());
			form.add(field2);
			form.add(new AbstractFormValidator() {
				private static final long serialVersionUID = 1L;

				public FormComponent[] getDependentFormComponents() {
					FormComponent[] result = new FormComponent[2];
					result[0] = field1;
					result[1] = field2;
					return result;
				}

				public void validate(Form form) {
					if (!TeatimeSession.get().login(
							(String) field1.getConvertedInput(),
							(String) field2.getConvertedInput())) {
						error(field1, "Incorrect username/password");
						error(field2, "Incorrect username/password");
					}
				}
			});
			form.add(new AjaxFallbackButton("logon", form) {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {
					if(!continueToOriginalDestination()) {
						setResponsePage(getPage());
					}
				}

				@Override
				protected void onError(AjaxRequestTarget target, Form form) {
					target.addComponent(UserPanel.this);
				}
			});
			add(form);
		}

		@Override
		public boolean isVisible() {
			return !TeatimeSession.get().isAuthenticated();
		}
	}

	public class LogoutFragment extends Fragment {
		/** */
		private static final long serialVersionUID = 1L;

		public LogoutFragment(String id) {
			super(id, "logout", UserPanel.this);
			add(new Label("session.username"));
			add(new Link("logout") {
				/** */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick() {
					TeatimeSession.get().logout();
				}
			});
		}

		@Override
		public boolean isVisible() {
			return TeatimeSession.get().isAuthenticated();
		}
	}

	public static class ErrorClassBehavior extends AbstractBehavior {
		private static final long serialVersionUID = 1L;

		@Override
		public void onComponentTag(Component component, ComponentTag tag) {
			if (component instanceof FormComponent) {
				FormComponent fc = (FormComponent) component;
				if (fc.isValid()) {
					tag.getAttributes().remove("style");
				} else {
					tag.getAttributes().put("style", "background-color:red");
				}
			}
			super.onComponentTag(component, tag);
		}
	}
}
