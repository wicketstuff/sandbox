/*
 * Created on Dec 22, 2004
 */
package net.sf.ipn.dynweb.metadata.panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.ipn.dynweb.metadata.Attribute;

/**
 * @author Jonathan Carlson Provides information on what to display in a UI view to edit a
 *         data object.
 */
public class EditPanelData implements PanelData
{

	String name;
	List attributes = new ArrayList();
	List subPanelDatas = new ArrayList();
	boolean headerShown = true;

	public EditPanelData(String name)
	{
		this.name = name;
	}

	public List getAttributes()
	{
		return Collections.unmodifiableList(this.attributes);
	}

	public void addAttribute(Attribute attr)
	{
		this.attributes.add(attr);
	}

	public List getSubPanelDatas()
	{
		return Collections.unmodifiableList(this.subPanelDatas);
	}

	/**
	 * @param expression an OGNL expression for retrieving the related item(s) from the
	 *            model
	 * @param panelData
	 */
	public void addSubPanelData(String expression, PanelData panelData)
	{
		if (panelData instanceof EditPanelData)
			((EditPanelData)panelData).setHeaderShown(false);
		this.subPanelDatas.add(new SubPanelData(expression, panelData));
	}

	public String getName()
	{
		return this.name;
	}

	public boolean isHeaderShown()
	{
		return headerShown;
	}

	public void setHeaderShown(boolean headerShown)
	{
		this.headerShown = headerShown;
	}
}