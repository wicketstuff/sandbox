package org.wicketstuff.table.cell;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.wicketstuff.table.SelectableListItem;

/**
 * Default implementation for CellRender and CellEditor
 * 
 * @author Pedro Henrique Oliveira dos Santos
 * 
 */
public class ObjectRender implements CellRender, CellEditor
{

	@Override
	public Component getRenderComponent(String id, IModel model, SelectableListItem parent,
			int row, int column)
	{
		return new Label(id, model);
	}

	@Override
	public Component getEditorComponent(String id, IModel model, SelectableListItem parent,
			int row, int column)
	{
		return new SelfSubmitTextFieldPanel(id, model);
	}

}
