package wicket.contrib.data.model.hibernate.binding;

import java.io.Serializable;

import wicket.Component;
import wicket.model.IModel;

public interface IColumn extends Serializable {
	public String getDisplayName();
	
	public String getModelPath();
	
	public String getOrderByPath();
	
	public boolean allowOrderBy();
	
	public Component getComponent(String id, IModel model);
}
