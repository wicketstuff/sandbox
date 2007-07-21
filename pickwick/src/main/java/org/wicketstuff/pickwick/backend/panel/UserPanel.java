package org.wicketstuff.pickwick.backend.panel;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.backend.users.UserBean;

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
		UserBean user = null;
		if (settings.getUserManagementModule() != null){
			user = settings.getUserManagementModule().getUser(UserEmail);
		}
		if(user == null){
			user = new UserBean();
		}
		
		Form form = new Form("userForm", new CompoundPropertyModel(user)){
			@Override
			protected void onSubmit() {
				UserBean user = (UserBean)getModelObject();
				settings.getUserManagementModule().addUser(user);
			}
		};
		add(form);
		
		TextField name = new TextField("name");
		form.add(name);
		
		TextField email = new TextField("email");
		email.add(EmailAddressValidator.getInstance());
		form.add(email);
		
		TextField role = new TextField("role");
		form.add(role);
	}

}
