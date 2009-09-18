package org.wicketstuff.pickwick.backend.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.Role;

import com.google.inject.Inject;

/**
 * a panel to add or edit roles
 * @author Vincent Demay
 *
 */
public abstract class RolesEditPanel extends Panel {

	@Inject
	Settings settings;
	
	private Form form;
	
	private String newRole;
	
	public RolesEditPanel(String id) {
		super(id, new Model());
		setOutputMarkupId(true);
		
		form = new Form("roleForm");
		
		form.add(new TextField("role", new Model(newRole)));
		
		form.add(new AjaxButton("submit", form){

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				Role role = new Role();
				role.setLabel(form.get("role").getDefaultModelObjectAsString());
				settings.getUserManagement().addRole(role);
				target.addComponent(form);
				onSave(target);
			}
			
		});
		add(form);
		form.setOutputMarkupId(true);
	}
	
	public abstract void onSave(AjaxRequestTarget target);
}
