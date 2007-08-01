package org.wicketstuff.pickwick.backend.panel;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.User;

import com.google.inject.Inject;

/**
 * a panel to add or edit a user.
 * @author Vincent Demay
 *
 */
public class UserPanel extends Panel {

	@Inject
	Settings settings;
	
	public UserPanel(String id, String UserEmail) {
		super(id);
		
		//get real user from userName
		User user = null;
		if (settings.getUserManagementModule() != null){
			user = settings.getUserManagementModule().getUser(UserEmail);
		}
		if(user == null){
			user = new User();
		}
		
		Form form = new Form("userForm", new CompoundPropertyModel(user)){
			@Override
			protected void onSubmit() {
				User user = (User)getModelObject();
				settings.getUserManagementModule().addUser(user);
			}
		};
		add(form);
		
		TextField name = new TextField("name");
		form.add(name);
		
		TextField role = new TextField("role");
		form.add(role);
	}

}
