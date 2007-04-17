package wicket.contrib.data.model.bind;

import java.util.List;

import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

public class MultiColumnPanel extends Panel
{
	public MultiColumnPanel(String id, IModel model, List columns)
	{
		super(id, model);
		setRenderBodyOnly(true);
		add(new Columns(columns));
	}
	
	private class Columns extends ListView
	{
		public Columns(List columns)
		{
			super("columns", columns);
			setOptimizeItemRemoval(true);
			setRenderBodyOnly(true);
		}
		
		protected void populateItem(ListItem item)
		{
			IModel model = MultiColumnPanel.this.getModel();
			IColumn col = (IColumn) item.getModelObject();
			
			item.add(col.getComponent("column", model));
			item.setRenderBodyOnly(true);
		}
	}
}
