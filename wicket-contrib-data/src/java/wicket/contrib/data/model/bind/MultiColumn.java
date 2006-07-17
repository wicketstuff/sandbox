package wicket.contrib.data.model.bind;

import java.util.ArrayList;
import java.util.List;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.model.IModel;

public class MultiColumn<T> extends AbstractColumn<T>
{
	private static final long serialVersionUID = 1L;

	private List<IColumn> allColumns = new ArrayList<IColumn>();

	public MultiColumn()
	{
		super(null, null);
	}

	public MultiColumn(String displayName, String ognlPath)
	{
		super(displayName, ognlPath);
	}

	public MultiColumn add(IColumn column)
	{
		allColumns.add(column);
		return this;
	}

	public Component getComponent(MarkupContainer parent, String id, IModel<T> model)
	{
		return new MultiColumnPanel(parent, id, model, allColumns);
	}
}
