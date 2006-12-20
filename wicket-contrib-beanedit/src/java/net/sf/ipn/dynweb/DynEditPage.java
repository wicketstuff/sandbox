package net.sf.ipn.dynweb;

import net.sf.ipn.dynweb.metadata.panel.EditPanelData;
import wicket.markup.html.WebPage;
import wicket.model.IModel;

/**
 * Displays a page to edit the attributes of the given model bean. The attributes are
 * specified in the EditPanelData supplied to the constructor.
 */
public final class DynEditPage extends WebPage
{

	/**
	 * Main constructor
	 * @param panelData - an instance of EditPanelData
	 * @param bean - the bean/model to edit
	 */
	public DynEditPage(EditPanelData panelData, IModel bean)
	{
		add(new DynEditPanelOld("editPanel", panelData, bean));
	}

}