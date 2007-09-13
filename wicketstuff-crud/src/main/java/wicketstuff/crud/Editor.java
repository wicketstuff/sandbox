package wicketstuff.crud;

import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

public interface Editor
{
	void setRequired(boolean required);

	void add(IValidator validator);

	void setLabel(IModel label);
}
