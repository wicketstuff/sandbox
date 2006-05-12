/*
 *  Copyright (C) 2005  Iulian-Corneliu Costan
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package wicket.contrib.tinymce.settings;

/**
 * The datetime plugin is able to insert date and time into the TinyMCE editable area.
 * <p/>
 *
 * @author Iulian-Corneliu Costan (iulian.costan@gmail.com)
 */
public class DateTimePlugin extends Plugin
{
	private static final long serialVersionUID = 1L;

    private PluginButton dateButton;
    private PluginButton timeButton;

    private String timeFormat;
    private String dateFormat;

    public DateTimePlugin()
    {
        super("insertdatetime");
        dateButton = new PluginButton("inserttime", this);
        timeButton = new PluginButton("insertdate", this);
    }

    public PluginButton getDateButton()
    {
        return dateButton;
    }

    public PluginButton getTimeButton()
    {
        return timeButton;
    }

    /**
	 * Time variables:
	 * <ul>
	 * <li>%r time in a.m. and p.m. notation</li>
	 * <li>%H hour as a decimal number using a 24-hour clock (range 00 to 23)</li>
	 * <li>%I hour as a decimal number using a 12-hour clock (range 01 to 12)</li>
	 * <li>%M minute as a decimal number (range 00-59)</li>
	 * <li>%S second as a decimal number (range 00-59)</li>
	 * <li>%p either "am" or "pm" according to the given time value</li>
	 * <li>%% a literal "%" character</li>
	 * </ul>
     * @param timeFormat
     */
    public void setTimeFormat(String timeFormat)
    {
        this.timeFormat = timeFormat;
    }

    /**
	 * Date variables:
	 * <ul>
	 * <li>%y year as a decimal number without a century (range 00 to 99)</li>
	 * <li>%Y year as a decimal number including the century</li>
	 * <li>%d day of the month as a decimal number (range 01 to 31)</li>
	 * <li>%m month as a decimal number (range 01 to 12)</li>
	 * <li>%D same as %m/%d/%y</li>
	 * <li>%% a literal "%" character</li>
	 * </ul>
     * @param timeFormat
     */
    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

	protected void definePluginSettings(StringBuffer buffer)
	{
		if (timeFormat != null)
		{
			define(buffer, "plugin_insertdate_timeFormat", timeFormat);
		}
		if (dateFormat != null) 
		{
			define(buffer, "plugin_insertdate_dateFormat", dateFormat);
		}
	}
    
}
