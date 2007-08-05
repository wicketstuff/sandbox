package org.wicketstuff.pickwick.backend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.backend.panel.UserListPanel;
import org.wicketstuff.pickwick.backend.panel.UserPanel;
import org.wicketstuff.pickwick.bean.User;
import org.wicketstuff.pickwick.frontend.pages.BasePage;

public class UserEditPage extends BasePage {
	
	private UserListPanel userlistPanel;
	private UserPanel userPanel;
	
	public UserEditPage(PageParameters params) {
		userPanel = new UserPanel("users", null){

			@Override
			public void onSave(AjaxRequestTarget target) {
				target.addComponent(userlistPanel);
			}
			
		};
		
		userlistPanel = new UserListPanel("userList"){

			@Override
			public void onUserSelected(AjaxRequestTarget target, User user) {
				userPanel.setModel(new CompoundPropertyModel(user));
				target.addComponent(userPanel);
				//userPanel.disableName(target);
			}
			
		};
		
		add(userlistPanel);
		add(userPanel);
		
	}
}
