package org.wicketstuff.pickwick.backend.panel;

import java.util.ArrayList;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.Role;
import org.wicketstuff.pickwick.bean.Roles;
import org.wicketstuff.pickwick.bean.User;

import com.google.inject.Inject;

/**
 * a panel to add or edit a user.
 * @author Vincent Demay
 *
 */
public abstract class UserPanel extends Panel {

	@Inject
	Settings settings;
	
	private Form form;
	private TextField name;
	private TextField password;
	
	public UserPanel(String id, CompoundPropertyModel userModel) {
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
		password = new TextField("password");
		name.setOutputMarkupId(true);
		form.add(name);
		form.add(password);

		Roles roles = settings.getUserManagement().getAllRoles();
		Palette role = new Palette("roles", new Model(roles), new ChoiceRenderer("label", "label"), 6, false);
		form.add(role);
		
		CheckBox admin = new CheckBox("admin");
		form.add(admin);
		
		form.add(new AjaxButton("save",form){

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				User user = (User)form.getModelObject();
				settings.getUserManagement().addUser(user);
				onSave(target);
			}

		});
	}
	
	public abstract void onSave(AjaxRequestTarget target);
	
	@Override
	public MarkupContainer setDefaultModel(IModel model) {
		if (!(model instanceof CompoundPropertyModel)){
			throw new WicketRuntimeException("model should be an instnceof CompoundPropertyModel");
		}
		super.setDefaultModel(new Model());
		form.setModel(model);
		return this;
	}
	
	public void disableName(AjaxRequestTarget target){
		target.appendJavascript("document.getElementById('" + name.getMarkupId() + "').disabled='disabled'");
	}
	
	public void enableName(AjaxRequestTarget target){
		target.appendJavascript("document.getElementById('" + name.getMarkupId() + "').disabled='false'");
	}
	

}
