/*
 * Created on Jan 6, 2005
 */
package net.sf.ipn.dynweb.visitor;

import java.io.Serializable;

import net.sf.ipn.dynweb.metadata.DataObjectType;
import net.sf.ipn.dynweb.metadata.DateAttribute;
import net.sf.ipn.dynweb.metadata.LovAttribute;
import net.sf.ipn.dynweb.metadata.NumericAttribute;
import net.sf.ipn.dynweb.metadata.StringAttribute;
import net.sf.ipn.dynweb.metadata.TypeVisitor;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.model.PropertyModel;

/**
 * @author Jonathan Carlson Builds a table cell for a given row ListItem (which must be
 *         set with the setter). Used by DynamicList.
 */
public class CellBuilderVisitor implements TypeVisitor
{
	private ListItem listItem;
	private Serializable rowBean;

	/**
	 * Sets the serializable bean that represents a row in the list
	 * @param model
	 */
	public void setRowBean(Serializable rowBean)
	{
		this.rowBean = rowBean;
	}

	/**
	 * Sets the
	 * @param listItemItem
	 */
	public void setListItem(ListItem listItem)
	{
		this.listItem = listItem;
	}

	public void handleDataObjectType(DataObjectType type)
	{
		// TODO Auto-generated method stub
	}

	public void handleNumericAttribute(NumericAttribute attr)
	{
		Label label = new Label("cell", new PropertyModel(this.rowBean, attr.getName()));
		this.listItem.add(label);
	}

	public void handleBooleanAttribute()
	{
		// TODO Auto-generated method stub
	}

	public void handleStringAttribute(StringAttribute attr)
	{
		Label label = new Label("cell", new PropertyModel(this.rowBean, attr.getName()));
		this.listItem.add(label);
	}

	public void handleDateAttribute(DateAttribute attr)
	{
		Label label = new Label("cell", new PropertyModel(this.rowBean, attr.getName()));
		this.listItem.add(label);
	}

	public void handleTimestampAttribute()
	{
		// TODO Auto-generated method stub
	}

	public void handleLovAttribute(LovAttribute attr)
	{
		// TODO Auto-generated method stub
	}

}