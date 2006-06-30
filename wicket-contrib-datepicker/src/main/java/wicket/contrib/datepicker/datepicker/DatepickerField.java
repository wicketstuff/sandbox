package wicket.contrib.datepicker.datepicker;

import wicket.behavior.HeaderContributor;
import wicket.behavior.StringHeaderContributor;
import wicket.extensions.markup.html.form.DateTextField;
import wicket.model.IModel;

public class DatepickerField extends DateTextField {

	private static final long serialVersionUID = 1L;
	private String format;

	public DatepickerField(String id, IModel object, String datePattern) {
		super(id, object, datePattern);
		init(datePattern);
	}

	public DatepickerField(String id, IModel object) {
		this(id, object, "MM/dd/yyyy");
	}

	public DatepickerField(String id, String datePattern) {
		super(id, datePattern);
		init(datePattern);
	}

	public DatepickerField(String id) {
		this(id, "MM/dd/yyyy");
	}

	private void init(String format) {
		this.format=format;

		add(HeaderContributor.forJavaScript(DatepickerField.class,
				"calendar.js"));
		add(HeaderContributor.forJavaScript(DatepickerField.class, "date.js"));
		add(HeaderContributor.forJavaScript(DatepickerField.class, "locale.js"));
		add(HeaderContributor.forJavaScript(DatepickerField.class, "sdf.js"));
		// TODO support different css for provided skins or possibly not include at all if skin is custom
		// TODO support for default as well as custom images, need a settings object
		// TODO support for custom markup of the activation button/link/image
		add(HeaderContributor.forCss(DatepickerField.class,"calendar.css"));
		setOutputMarkupId(true);
		
	}
	
	private String getPickerId() {
		return getMarkupId()+"-dp";
	}
	
	@Override
	protected void onAfterRender() {
		super.onAfterRender();
		getResponse().write("\n<a href=\"#\" onclick=\"Wicket.Calendar.getInstance('"+getPickerId()+"').toggle();\">pop</a>");
		getResponse().write("\n<script>new Wicket.Calendar(\""+getMarkupId()+"\",\""+getPickerId()+"\",\""+format+"\");</script>\n");
	}

}
