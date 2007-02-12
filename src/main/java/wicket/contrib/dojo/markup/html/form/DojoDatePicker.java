package wicket.contrib.dojo.markup.html.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import wicket.behavior.AttributeAppender;
import wicket.contrib.dojo.DojoLocaleManager;
import wicket.contrib.dojo.toggle.DojoToggle;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.TextField;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.util.convert.ConversionException;

/**
 * <p>
 * A DatePicker to select a Date in a popup (not navigator but Js)
 * You can add effect to this popup adding a {@link DojoToggle} with the setToggle method.<br/>
 * This component should be associated to an <b>input</b> in the markup
 * </p>
 * <p>
 * <b><u>Sample</u></b>
 * <pre>
 * [...]
 * DojoDatePicker datePicker = new DojoDatePicker("date", new Model(date));
 * datePicker.setToggle(new DojoWipeToggle(200));
 * form.add(datePicker);
 * </pre>
 * </p>
 * 
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 */
public class DojoDatePicker extends TextField{
	
	private SimpleDateFormat formatter;
	private String displayFormat;
	private Locale locale;

	/**
	 * @param parent
	 * @param id
	 * @param model
	 * @param pattern
	 */
	public DojoDatePicker(String id, IModel model, String displayFormat)
	{
		this(id, model, displayFormat, null);
	}
	
	/**
	 * @param parent
	 * @param id
	 * @param model
	 * @param pattern
	 * @param timeZone 
	 */
	public DojoDatePicker(String id, IModel model, String displayFormat, TimeZone timeZone){
		super(id, model);
		this.displayFormat = displayFormat;
		add(new DojoDatePickerHandler());
		this.setOutputMarkupId(true);
		formatter = new SimpleDateFormat(getInternalDatePattern());
		if (timeZone != null){
			formatter.setTimeZone(timeZone);
		}
	}
	
	public static String getInternalDatePattern() {
		return "yyyy-MM-dd";
	}

	public DojoDatePicker(String id, String displayFormat){
		this(id, null, displayFormat);
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.put("dojoType", "DropdownDatePicker");
		tag.put("displayFormat", getDisplayFormat());
		String localeString = getLocaleAsString();
		if (localeString != null){
			tag.put("lang", localeString);
		}
	}

	/**
	 * Set the date picker effect
	 * @param toggle
	 */
	public void setToggle(DojoToggle toggle){
		this.add(new AttributeAppender("containerToggle", new Model(toggle.getToggle()),""));
		this.add(new AttributeAppender("containerToggleDuration", new Model(toggle.getDuration() + ""),""));
	}

	/**
	 * @see FormComponent#getModelValue()
	 */
	public final String getModelValue()
	{
		if (getModelObject() != null){
			return formatter.format((Date)getModelObject());
		}
		return null;
	}


	protected Object convertValue(String[] value) throws ConversionException
	{
		if (value == null || "".equals(value[0]))
			return null;
		
		try
		{
			return formatter.parse(value[0]);
		}
		catch (ParseException e)
		{
			throw new ConversionException(e);
		}
		
	}


	public String getDisplayFormat()
	{
		return displayFormat;
	}


	public void setDisplayFormat(String displayFormat)
	{
		this.displayFormat = displayFormat;
	}

	public Locale getLocale() {
		return locale;
	}
	
	public String getLocaleAsString() {
		if (locale == null) return null;
		return locale.toString().replace("_", "-").toLowerCase();
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
		DojoLocaleManager.getInstance().addLocale(locale);
	}
	
}
