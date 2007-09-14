package wicketstuff.crud;

import org.apache.wicket.IClusterable;
import org.apache.wicket.model.IModel;

public interface ICrudListener extends IClusterable
{
	void onView(IModel selected);

	void onCreate();

	void onEdit(IModel selected);

	void onSave(IModel selected);

	void onDeleteConfirmed(IModel selected);

	void onDelete(IModel selected);

	void onCancel();
}
