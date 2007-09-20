package org.wicketstuff.pickwick.backend.panel;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.Role;
import org.wicketstuff.pickwick.bean.Roles;

import com.google.inject.Inject;

/**
 * a panel to add or edit roles
 * @author Vincent Demay
 *
 */
public class RolesPanel extends Panel {

	@Inject
	Settings settings;
	
	private Form form;
	
	private RefreshingView roleList;
	
	private String newRole;
	
	public RolesPanel(String id) {
		super(id, new Model());
		setOutputMarkupId(true);
		
		form = new Form("roleForm");
		
		form.add(roleList = new RefreshingView("roles"){

			@Override
			protected Iterator getItemModels() {
				Roles roles = settings.getUserManagement().getAllRoles();
				ArrayList<IModel> models = new ArrayList<IModel>();
				for (Role role : roles){
					models.add(new Model(role));
				}
				return models.iterator();
			}

			@Override
			protected void populateItem(Item item) {
				String label  = ((Role)item.getModelObject()).getLabel();
				item.add(new Label("label", new Model(label)));
			}
			
		});
		roleList.setOutputMarkupId(true);
		
		form.add(new TextField("role", new Model(newRole)));
		
		form.add(new AjaxButton("submit", form){

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				Role role = new Role();
				role.setLabel(form.get("role").getModelObjectAsString());
				settings.getUserManagement().addRole(role);
				target.addComponent(form);
			}
			
		});
		add(form);
		form.setOutputMarkupId(true);
	}
}
