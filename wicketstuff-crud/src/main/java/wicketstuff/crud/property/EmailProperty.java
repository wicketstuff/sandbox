package wicketstuff.crud.property;

import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

/**
 * Email property
 * 
 * @author igor.vaynberg
 * 
 */
public class EmailProperty extends StringProperty
{

	/**
	 * Constructor
	 * 
	 * @param path
	 * @param label
	 */
	public EmailProperty(String path, IModel label)
	{
		super(path, label);
		add(EmailAddressValidator.getInstance());
	}

}
