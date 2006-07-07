/* ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.datepicker.datepicker;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.WicketRuntimeException;
import wicket.behavior.HeaderContributor;
import wicket.extensions.markup.html.form.DateTextField;
import wicket.model.IModel;

/*
 * @author Igor Vaynberg (ivaynberg)
 * @author Janne Hietam&auml;ki (jannehietamaki)
 */
public class DatepickerField extends DateTextField {
	private final static Log log = LogFactory.getLog(DatepickerField.class);
	
	private static final long serialVersionUID = 1L;

    /** all date formats. */
    private static Properties dateformats = new Properties();
    
	static
	{
		InputStream resourceAsStream = null;
		try
		{
			resourceAsStream = DatepickerField.class.getResourceAsStream("dateformats.properties");
			dateformats.load(resourceAsStream);
		}
		catch (IOException e)
		{
			throw new WicketRuntimeException(e);
		}
		finally
		{
			try
			{
				if (resourceAsStream != null)
				{
					resourceAsStream.close();
				}
			}
			catch (IOException ex)
			{
				// ignore
			}
		}
	}

	private static String getDatePattern(Locale locale){
		String datePattern=dateformats.getProperty(locale.toString());
		if(datePattern!=null)return datePattern;
		return "YYYY-mm-dd";  // default to ISO-8601
	}
	
	private String format=null;

	// TODO: locale parameter can be removed when moving to wicket2 constructors
	public DatepickerField(String id, IModel object, Locale locale) {
		super(id, object, getDatePattern(locale));
		init();
		this.format=getDatePattern(locale);
	}

	
	public DatepickerField(String id, IModel object, String datePattern) {
		super(id, object, datePattern);
		init();
		this.format=datePattern;
	}

	public DatepickerField(String id, IModel object) {
		this(id, object, "YYYY-mm-dd");
	}

	public DatepickerField(String id, String datePattern) {
		super(id, datePattern);
		init();
		format=datePattern;
	}

	public DatepickerField(String id) {
		this(id, "YYYY-mm-dd");
	}

	private void init() {
		add(HeaderContributor.forJavaScript(DatepickerField.class, "calendar.js"));
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
		getResponse().write("\n<script>new Wicket.Calendar(\""+getMarkupId()+"\",\""+getPickerId()+"\",\""+format+"\",new Wicket.DateLocale({");

		DateFormatSymbols s=new DateFormatSymbols(getLocale());
		GregorianCalendar cal=new GregorianCalendar(getLocale());
		String weekNumbering=cal.getMinimalDaysInFirstWeek()==4?"ISO":"US";
		getResponse().write("\nmonths:["+printFields(s.getMonths(),Calendar.JANUARY)+"],");
		getResponse().write("\nshortMonths : ["+printFields(s.getShortMonths(),Calendar.JANUARY)+"],");
		getResponse().write("\nweekdays:["+printFields(s.getWeekdays(),Calendar.SUNDAY)+"],");
		getResponse().write("\nshortWeekdays:["+printFields(s.getShortWeekdays(),Calendar.SUNDAY)+"],");
		getResponse().write("\nfirstDayOfWeek:"+(cal.getFirstDayOfWeek()-1)+",");
		getResponse().write("\nweekNumbering: '"+weekNumbering+"'");
		getResponse().write("}));</script>\n");
	}

	private CharSequence printFields(String values[],int startidx){
		StringBuffer ret=new StringBuffer();
		for(int a=startidx;a<values.length;a++){
			if(a>startidx)ret.append(",");
			ret.append("\"");
			ret.append(values[a]);
			ret.append("\"");
		}
		return ret;
	}		
}