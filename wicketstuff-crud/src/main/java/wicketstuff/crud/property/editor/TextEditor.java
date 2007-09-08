package wicketstuff.crud.property.editor;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class TextEditor extends Panel
{
	private final TextField field;
	private int maxLength;

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

	public int getMaxLength()
	{
		return maxLength;
	}

	public TextEditor setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
		return this;
	}


}
