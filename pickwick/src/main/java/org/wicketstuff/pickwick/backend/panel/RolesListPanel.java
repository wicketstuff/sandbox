package org.wicketstuff.pickwick.backend.panel;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
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
public class RolesListPanel extends Panel {

	@Inject
	Settings settings;
	
	private RefreshingView roleList;

	public RolesListPanel(String id) {
		super(id, new Model());
		setOutputMarkupId(true);

		add(roleList = new RefreshingView("roles"){

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
	}
}
