package wicketstuff.crud;

import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

public interface Editor
{
	void setRequired(boolean required);

	boolean isRequired();

	void add(IValidator validator);

	void setLabel(IModel label);

	IModel getLabel();
}
