package org.wicketstuff.pickwick.backend.panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.User;

import com.google.inject.Inject;

/**
 * a panel to add or edit a user.
 * @author Vincent Demay
 *
 */
public abstract class UserListPanel extends Panel {

	@Inject
	Settings settings;
	
	public UserListPanel(String id) {
		super(id);
		setOutputMarkupId(true);
		
		RefreshingView userList = new RefreshingView("userList"){

			@Override
			protected Iterator getItemModels() {
				List<User> users = settings.getUserManagement().getAllUsers();
				Collections.sort(users);
				Iterator<User> it = users.iterator();
				List<Model> usersModel = new ArrayList<Model>();
				while(it.hasNext()){
					usersModel.add(new Model(it.next()));
				}
				return usersModel.iterator();
			}

			@Override
			protected void populateItem(final Item item) {
				AjaxLink link;
				item.add(link = new AjaxLink("link"){

					@Override
					public void onClick(AjaxRequestTarget target) {
						
						onUserSelected(target, ((User)item.getModelObject()));
					}
					
				});
				
				link.add(new Label("name", new PropertyModel(item.getModelObject(), "name")));
				WebMarkupContainer image;
				link.add(image = new WebMarkupContainer("isAdmin"){
					@Override
					protected void onComponentTag(ComponentTag tag) {
						super.onComponentTag(tag);
						tag.put("src", urlFor(new ResourceReference(UserListPanel.class, "images/admin_icon.gif")));
					}
				});
				image.setVisible(((User)item.getModelObject()).isAdmin());
			}
			
		};
		
		add(userList);
	}
	
	public abstract void onUserSelected(AjaxRequestTarget target, User user);
	

}
