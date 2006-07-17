package wicket.contrib.data.model.bind;

import java.util.List;

import wicket.MarkupContainer;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

public class MultiColumnPanel<T> extends Panel<T>
{
	private static final long serialVersionUID = 1L;

	public MultiColumnPanel(MarkupContainer parent, String id, IModel<T> model,
			List<IColumn> columns)
	{
		super(parent, id, model);
		setRenderBodyOnly(true);
		new Columns(this, columns);
	}

	private class Columns extends ListView<IColumn>
	{
		private static final long serialVersionUID = 1L;

		public Columns(MarkupContainer parent, List<IColumn> columns)
		{
			super(parent, "columns", columns);
			setReuseItems(true);
			setRenderBodyOnly(true);
		}

		@Override
		@SuppressWarnings("unchecked")
		protected void populateItem(ListItem item)
		{
			IModel<T> model = MultiColumnPanel.this.getModel();
			IColumn col = (IColumn) item.getModelObject();
			col.getComponent(item, "column", model);
			item.setRenderBodyOnly(true);
		}
	}
}
