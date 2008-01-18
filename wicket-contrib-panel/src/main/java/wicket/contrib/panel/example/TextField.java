package wicket.contrib.panel.example;

import org.apache.log4j.Logger;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IOnChangeListener;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class TextField extends org.apache.wicket.markup.html.form.TextField implements IOnChangeListener {

	protected int size = 20;
	protected int maxLength = 70;
	private boolean wantOnSelectionChangedNotifications;
	protected static Logger logger = Logger.getLogger(TextField.class);

	/**
	 * @see wicket.Component#Component(String)
	 */
	public TextField(final String id)
	{
		this(id, null);
	}

	/**
	 * @see wicket.Component#Component(String, IModel)
	 */
	public TextField(final String id, final IModel object)
	{
		super(id, object);
		add(new AttributeModifier("maxlength", true, new PropertyModel(this, "maxLengthValue")));
		add(new AttributeModifier("size", true, new PropertyModel(this, "sizeValue")));
	}

	public TextField setMaxLength(int i) {
		maxLength = i;
		return this;
	}

	public TextField setSize(int i) {
		size = i;
		return this;
	}

	public int getSize() {
		return size;
	}

	public String getSizeValue() {
		return String.valueOf(size);
	}

	public int getMaxLength() {
		return maxLength;
	}

	public String getMaxLengthValue() {
		return String.valueOf(maxLength);
	}



	@Override
	protected void onComponentTag(final ComponentTag tag)
	{

		// Should a roundtrip be made (have onSelectionChanged called) when the selection changed?
		if (getWantOnSelectionChangedNotifications())
		{
			// url that points to this components IOnChangeListener method
			final CharSequence url = urlFor(IOnChangeListener.INTERFACE);

			try
			{
				Form form = getForm();
				tag.put("onchange", form.getJsForInterfaceUrl(url) );
			}
			catch (WicketRuntimeException ex)
			{
				// NOTE: do not encode the url as that would give invalid JavaScript
				tag.put("onchange", "location.href='" + url + "&" + getInputName()
						+ "=' + this.options[this.selectedIndex].value;");
			}
		}
		super.onComponentTag(tag);
	}
	/**
	 * Template method that can be overriden by clients that implement
	 * IOnChangeListener to be notified by onChange events of a select element.
	 * This method does nothing by default.
	 * <p>
	 * Called when a option is selected of a dropdown list that wants to be
	 * notified of this event. This method is to be implemented by clients that
	 * want to be notified of selection events.
	 *
	 * @param newSelection
	 *			  The newly selected object of the backing model NOTE this is
	 *			  the same as you would get by calling getModelObject() if the
	 *			  new selection were current
	 */
	protected void onChanged()
	{
	}

	public void onSelectionChanged() {
		try {
//FIXME			convert();
			updateModel();
			onChanged();
		} catch (Exception e) {
			logger.error("Textfield onSelectionChanged error " + e.getMessage());
		}
	}

	public boolean getWantOnSelectionChangedNotifications() {
		return wantOnSelectionChangedNotifications;
	}

	public void setWantOnSelectionChangedNotifications(
			boolean value) {
		wantOnSelectionChangedNotifications = value;
	}

}
