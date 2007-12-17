package org.wicketstuff.pickwick.backend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.pickwick.backend.panel.RolesEditPanel;
import org.wicketstuff.pickwick.backend.panel.RolesListPanel;

public class RoleEditPage extends BaseAdminPage {
	
	private RolesListPanel listPanel;
	private RolesEditPanel editPanel;
	
	public RoleEditPage(PageParameters params) {
		super(params);
		add(new RolesEditPanel("roleEdit"){
			@Override
			public void onSave(AjaxRequestTarget target) {
				target.addComponent(listPanel);
			}
		});
	}
	
	@Override
	protected Panel getWestPanel(String id) {
		listPanel = new RolesListPanel(id);
		listPanel.setOutputMarkupId(true);
		return listPanel;
	}
}
