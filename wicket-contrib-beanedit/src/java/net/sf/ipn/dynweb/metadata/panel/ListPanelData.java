/*
 * Created on Jan 04, 2005
 */
package net.sf.ipn.dynweb.metadata.panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.ipn.dynweb.metadata.Attribute;

/**
 * @author Jonathan Carlson Provides information on which columns to display in a list of
 *         data objects.
 */
public class ListPanelData implements PanelData
{

	/** If not null, user allowed to edit each row */
	EditPanelData editPanelData;
	String name;

	List attributes = new ArrayList();

	public ListPanelData(String name)
	{
		this.name = name;
	}

	public void setEditPanelData(EditPanelData panelData)
	{
		this.editPanelData = panelData;
	}

	public EditPanelData getEditPanelData()
	{
		return this.editPanelData;
	}

	public List getAttributes()
	{
		return Collections.unmodifiableList(this.attributes);
	}

	public void addAttribute(Attribute attr)
	{
		this.attributes.add(attr);
	}

	public String getName()
	{
		return this.name;
	}

	public List getSubPanelDatas()
	{
		// TODO implement getSubPanels
		return Collections.EMPTY_LIST;
	}

}