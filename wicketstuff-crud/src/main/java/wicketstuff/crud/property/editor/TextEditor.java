package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import wicketstuff.crud.Editor;

/**
 * Text editor
 * 
 * @author igor.vaynberg
 * 
 */
public class TextEditor extends FormComponentEditor implements Editor
{
	private final TextField field;
	private int maxLength;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param model
	 */
	public TextEditor(String id, IModel model)
	{
		super(id);
		add(field = new TextField("text", model)
		{
			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				if (maxLength > 0)
				{
					tag.put("maxlen", maxLength);
				}
			}
		});
	}

	/**
	 * Sets max length
	 * 
	 * @param maxLength
	 * @return
	 */
	public TextEditor setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	protected FormComponent getFormComponent()
	{
		return field;
	}


}
