package org.wicketstuff.pickwick.frontend.panel;

import java.util.ArrayList;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.bean.Role;
import org.wicketstuff.pickwick.bean.User;

/**
 * a panel to add or edit a user.
 * @author Vincent Demay
 *
 */
public abstract class UserInscription extends Panel {
	
	private Form form;
	private TextField name;
	private PasswordTextField password;
	private PasswordTextField passwordRepeat;
	
	private String passwordRepeated;
	
	public UserInscription(String id, CompoundPropertyModel userModel) {
		super(id, new Model());
		setOutputMarkupId(true);
		
		form = new Form("userForm", userModel);
		
		if (userModel == null){
			User user = new User();
			user.setRoles(new ArrayList<Role>());
			form.setModel(new CompoundPropertyModel(user));
		}
		add(form);
		
		name = new TextField("name");
		password = new PasswordTextField("password");
		passwordRepeat = new PasswordTextField("passwordRepeat", new Model(passwordRepeated));
		name.setOutputMarkupId(true);
		
		name.setRequired(true);
		password.setRequired(true);
		passwordRepeat.setRequired(true);
		
		form.add(name);
		form.add(password);
		form.add(passwordRepeat);
		
		form.add(new FeedbackPanel("feedback"));

		form.add(new AjaxButton("save",form){

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				onSave(target, form);
			}

		});
	}
	
	public abstract void onSave(AjaxRequestTarget target, Form form);
	
	@Override
	public MarkupContainer setDefaultModel(IModel model) {
		if (!(model instanceof CompoundPropertyModel)){
			throw new WicketRuntimeException("model should be an instnceof CompoundPropertyModel");
		}
		super.setDefaultModel(new Model());
		form.setModel(model);
		return this;
	}

  public String getPasswordRepeated() {
		return passwordRepeated;
	}
}
