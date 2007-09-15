package wicketstuff.crud.property;

import java.util.Arrays;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Enum property
 * 
 * @author igor.vaynberg
 * 
 * @param <T>
 */
public class EnumChoiceProperty<T extends Enum> extends ChoiceProperty
{

	/**
	 * Constructor
	 * 
	 * @param path
	 * @param label
	 * @param type
	 *            enum type
	 */
	public EnumChoiceProperty(String path, IModel label, final Class<T> type)
	{
		super(path, label);
		setChoices(new LoadableDetachableModel()
		{

			@Override
			protected Object load()
			{
				return Arrays.asList(type.getEnumConstants());
			}

		});

		setRenderer(new EnumRenderer());

	}

	/**
	 * Renderer used to render enum
	 * 
	 * @author igor.vaynberg
	 * 
	 * @param <T>
	 */
	private static class EnumRenderer<T extends Enum> implements IChoiceRenderer
	{

		private static final long serialVersionUID = 1L;

		/** {@inheritDoc} */
		public Object getDisplayValue(Object object)
		{
			return (object == null) ? "" : object.toString();
		}

		/** {@inheritDoc} */
		public String getIdValue(Object object, int index)
		{
			return (object == null) ? "" : ((Enum)object).name();
		}

	}

}
