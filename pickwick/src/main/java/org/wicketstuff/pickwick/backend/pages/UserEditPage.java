package org.wicketstuff.pickwick.backend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.pickwick.backend.panel.UserListPanel;
import org.wicketstuff.pickwick.backend.panel.UserPanel;
import org.wicketstuff.pickwick.bean.User;

public class UserEditPage extends BaseAdminPage {
	
	private UserListPanel userlistPanel;
	private UserPanel userPanel;
	
	public UserEditPage(PageParameters params) {
		super(params);
		userPanel = new UserPanel("users", null){

			@Override
			public void onSave(AjaxRequestTarget target) {
				target.addComponent(userlistPanel);
			}
			
		};
		add(userPanel);
		
	}
	
	@Override
	protected Panel getWestPanel(String id) {
		userlistPanel = new UserListPanel(id){

			@Override
			public void onUserSelected(AjaxRequestTarget target, User user) {
				userPanel.setDefaultModel(new CompoundPropertyModel(user));
				target.addComponent(userPanel);
				//userPanel.disableName(target);
			}
			
		};
		return userlistPanel;
	}
}
