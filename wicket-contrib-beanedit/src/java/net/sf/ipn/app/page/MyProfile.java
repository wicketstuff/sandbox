/*
 * Created on Apr 19, 2005
 */
package net.sf.ipn.app.page;

import java.util.Arrays;
import java.util.List;

import net.sf.ipn.app.IpnBorderWebPage;
import net.sf.ipn.dynweb.DynEditPanelOld;
import net.sf.ipn.dynweb.metadata.LovAttribute;
import net.sf.ipn.dynweb.metadata.StringAttribute;
import net.sf.ipn.dynweb.metadata.panel.EditPanelData;
import net.sf.ipn.dynweb.metadata.panel.ListPanelData;
import wicket.model.IModel;
import wicket.model.LoadableDetachableModel;

/**
 * @author Jonathan Carlson
 */
public class MyProfile extends IpnBorderWebPage
{

	/**
	 * Constructs the page and its components
	 */
	public MyProfile()
	{
		// add(new IpnHeader("mainHeader"));
		IModel model = new LoadableDetachableModel()
		{
			protected Object load()
			{
				return MyProfile.this.getUser();
			}

			public void save()
			{
				getDataContext().commitChanges();
			}
		};
		add(new DynEditPanelOld("profileEditForm", panelData(), model));
	}

	public String getTabLabel()
	{
		return MY_PROFILE_TAB;
	}


	static EditPanelData panelData()
	{
		/*
		 * Someday the builder will look like this: EditPanelFactory factory = new
		 * EditPanelFactory(); factory.longAttr("id").primaryKey().required().minValue(1);
		 * factory.stringAttr("name").required();
		 * factory.lovAttr("countryCode").values(new String[] {"US","CA","MX"});
		 * EditPanelData panelData = factory.build();
		 */

		EditPanelData editPanelData = new EditPanelData("Profile Edit");

		StringAttribute loginName = new StringAttribute("name");
		loginName.setRequired(true);
		editPanelData.addAttribute(loginName);

		StringAttribute givenName = new StringAttribute("givenName");
		givenName.setRequired(true);
		editPanelData.addAttribute(givenName);

		StringAttribute familyName = new StringAttribute("familyName");
		familyName.setRequired(true);
		editPanelData.addAttribute(familyName);

		StringAttribute denomination = new StringAttribute("denomination");
		editPanelData.addAttribute(denomination);

		List values = Arrays.asList(new String[] { "US", "CA", "MX" });
		LovAttribute countryCode = new LovAttribute("countryCode", values);
		editPanelData.addAttribute(countryCode);


		// E-Mail addresses will be a tab / sub-panel

		ListPanelData emailListData = new ListPanelData("E-Mail Addresses");
		StringAttribute email = new StringAttribute("title");
		emailListData.addAttribute(email);

		// albumListData.setEditPanelData(albumEditPanelData());
		// editPanelData.addSubPanelData("albums", albumListData);

		// Style editing will be a tab, although it's not really a real example.
		/*
		 * EditPanelData stylePanelData = new EditPanelData("Style Edit");
		 * stylePanelData.setHeaderShown(false); StringAttribute styleCode = new
		 * StringAttribute("code"); stylePanelData.addAttribute(styleCode);
		 * StringAttribute styleDescription = new StringAttribute("description");
		 * stylePanelData.addAttribute(styleDescription);
		 * editPanelData.addSubPanelData("style", stylePanelData);
		 */
		return editPanelData;
	}

}
