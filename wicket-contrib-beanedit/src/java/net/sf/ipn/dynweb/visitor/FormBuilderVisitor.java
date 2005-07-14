/*
 * Created on Jan 6, 2005
 */
package net.sf.ipn.dynweb.visitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ipn.dynweb.DynDropDownPanel;
import net.sf.ipn.dynweb.DynTextAreaPanel;
import net.sf.ipn.dynweb.DynTextField;
import net.sf.ipn.dynweb.metadata.Attribute;
import net.sf.ipn.dynweb.metadata.DataObjectType;
import net.sf.ipn.dynweb.metadata.DateAttribute;
import net.sf.ipn.dynweb.metadata.LovAttribute;
import net.sf.ipn.dynweb.metadata.NumericAttribute;
import net.sf.ipn.dynweb.metadata.StringAttribute;
import net.sf.ipn.dynweb.metadata.TypeVisitor;
import net.sf.ipn.dynweb.validator.DynPatternValidator;
import net.sf.ipn.dynweb.validator.DynRequiredValidator;
import wicket.AttributeModifier;
import wicket.Component;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.validation.LengthValidator;
import wicket.markup.html.form.validation.PatternValidator;
import wicket.markup.html.list.ListItem;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.model.PropertyModel;

/**
 * @author Jonathan Carlson Builds an entry field for the given attribute for a
 *         DynamicForm
 */
public class FormBuilderVisitor implements TypeVisitor, Serializable
{
	private ListItem listItem;

	private IModel model;

	/**
	 * Construct.
	 * @param formModel
	 */
	public FormBuilderVisitor(IModel formModel)
	{
		this.model = formModel;
	}

	/**
	 * @param listItem
	 */
	public void setListItem(ListItem listItem)
	{
		this.listItem = listItem;
	}

	/**
	 * @see net.sf.ipn.dynweb.metadata.TypeVisitor#handleDataObjectType(net.sf.ipn.dynweb.metadata.DataObjectType)
	 */
	public void handleDataObjectType(DataObjectType type)
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see net.sf.ipn.dynweb.metadata.TypeVisitor#handleNumericAttribute(net.sf.ipn.dynweb.metadata.NumericAttribute)
	 */
	public void handleNumericAttribute(NumericAttribute attr)
	{
		FormComponent formComponent = new DynTextField("dynamicField", new PropertyModel(
				this.model, attr.getName()));
		maybeAddRequiredValidator(formComponent, attr);
		Map map = new HashMap();
		map.put("attrName", attr.getName());
		formComponent.add(new DynPatternValidator("\\d*", "error.fieldNotNumeric", map));
		this.listItem.add(formComponent);
	}

	/**
	 * @see net.sf.ipn.dynweb.metadata.TypeVisitor#handleBooleanAttribute()
	 */
	public void handleBooleanAttribute()
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see net.sf.ipn.dynweb.metadata.TypeVisitor#handleStringAttribute(net.sf.ipn.dynweb.metadata.StringAttribute)
	 */
	public void handleStringAttribute(StringAttribute attr)
	{
		if (attr.isMultiLine())
		{
			Component c = new DynTextAreaPanel("dynamicField", new PropertyModel(this.model, attr
					.getName()));
			this.listItem.add(c);
		}
		else
		{
			FormComponent formComponent = new DynTextField("dynamicField", new PropertyModel(
					this.model, attr.getName()));

			// Sets the size of the text field
			int size = 20;
			if (attr.getMaxLength() > 0 && attr.getMaxLength() < size)
				size = attr.getMaxLength();
			String sizeString = String.valueOf(attr.getMaxLength());
			formComponent.add(new AttributeModifier("size", new Model(sizeString)));

			if (attr.getMaxLength() >= 0)
			{ // Validate both on the server and the browser
				formComponent.add(LengthValidator.max(attr.getMaxLength()));
				formComponent.add(new AttributeModifier("maxlength", new Model(String.valueOf(attr
						.getMaxLength()))));
			}
			// Validate only on the server
			if (attr.getMinLength() > 0)
				formComponent.add(LengthValidator.min(attr.getMinLength()));
			maybeAddRequiredValidator(formComponent, attr);
			if (attr.getPattern() != null)
				formComponent.add(new PatternValidator(attr.getPattern()));
			this.listItem.add(formComponent);
		}
	}

	/**
	 * @see net.sf.ipn.dynweb.metadata.TypeVisitor#handleDateAttribute(net.sf.ipn.dynweb.metadata.DateAttribute)
	 */
	public void handleDateAttribute(DateAttribute attr)
	{
		FormComponent formComponent = new DynTextField("dynamicField", new PropertyModel(
				this.model, attr.getName()), Date.class);
		maybeAddRequiredValidator(formComponent, attr);
		// Map map = new HashMap();
		// map.put("attrName", attr.getName());
		// formComponent.add(new DynPatternValidator("\\d*", "error.fieldNotNumeric",
		// map));
		this.listItem.add(formComponent);
	}

	/**
	 * @see net.sf.ipn.dynweb.metadata.TypeVisitor#handleTimestampAttribute()
	 */
	public void handleTimestampAttribute()
	{
		// TODO Auto-generated method stub
	}

	/**
	 * Handle an attribute whose value is chosen from a list of values/options
	 * @param attr
	 */
	public void handleLovAttribute(final LovAttribute attr)
	{
		List values = new ArrayList(attr.getValues());
		DynDropDownPanel dynDropDownPanel = new DynDropDownPanel("dynamicField", new PropertyModel(
				this.model, attr.getName()), values);
		// Will a required validator do anything?
		// this.maybeAddRequiredValidator(dynDropDownPanel.getDropDownChoice(), attr);
		this.listItem.add(dynDropDownPanel);
	}

	private void maybeAddRequiredValidator(FormComponent formComponent, Attribute attr)
	{
		if (!attr.isRequired())
			return;
		Map map = new HashMap();
		map.put("attrName", attr.getTitle());
		formComponent.add(new DynRequiredValidator("error.fieldRequired", map));
	}

}