package org.wicketstuff.table.cell;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.IModel;

public class LenientCheckBox extends CheckBox
{

	public LenientCheckBox(String id, IModel<Boolean> model)
	{
		super(id, model);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		tag.setName("input");
		tag.put("type", "checkbox");
		super.onComponentTag(tag);
	}

}
