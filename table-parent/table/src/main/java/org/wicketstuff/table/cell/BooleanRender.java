package org.wicketstuff.table.cell;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.model.IModel;
import org.wicketstuff.table.SelectableListItem;

public class BooleanRender implements CellEditor, CellRender
{

	@Override
	public Component getEditorComponent(String id, IModel model, SelectableListItem parent,
			int row, int column)
	{
		LenientCheckBox checkBox = new LenientCheckBox(id, model);
		checkBox.add(new AjaxFormComponentUpdatingBehavior("onchange")
		{
			protected void onUpdate(AjaxRequestTarget target)
			{
			}
		});
		return checkBox;
	}

	@Override
	public Component getRenderComponent(String id, IModel model, SelectableListItem parent,
			int row, int column)
	{
		return new LenientCheckBox(id, model)
		{
			@Override
			public boolean isEnabled()
			{
				return false;
			}
		};
	}

}
