package wicket.contrib.data.model.bind;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

public class MultiColumn extends AbstractColumn
{
	private List allColumns = new ArrayList();
	
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

	public Component getComponent(String id, IModel model)
	{
		return new MultiColumnPanel(id, model, allColumns);
	}
}
