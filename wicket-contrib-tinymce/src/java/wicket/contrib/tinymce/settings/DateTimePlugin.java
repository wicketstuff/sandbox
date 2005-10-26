/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package wicket.contrib.tinymce.settings;

/**
 * The datetime plugin is able to insert date and time into the TinyMCE editable area.
 * <p/>
 * Replacement variables:
 * <ul>
 * <li><b>%y</b> year as a decimal number without a century (range 00 to 99)</li>
 * <li>%Y year as a decimal number including the century</li>
 * %d day of the month as a decimal number (range 01 to 31)
 * %m month as a decimal number (range 01 to 12)
 * %D same as %m/%d/%y
 * %r time in a.m. and p.m. notation
 * %H hour as a decimal number using a 24-hour clock (range 00 to 23)
 * %I hour as a decimal number using a 12-hour clock (range 01 to 12)
 * %M minute as a decimal number (range 00-59)
 * %S second as a decimal number (range 00-59)
 * %p either "am" or "pm" according to the given time value
 * %% a literal "%" character
 * </ul>
 *
 * @author Iulian-Corneliu COSTAN
 */
public class DateTimePlugin extends Plugin {

    private PluginButton dateButton;
    private PluginButton timeButton;

    private String timeFormat;
    private String dateFormat;

    public DateTimePlugin() {
        super("insertdatetime");
        dateButton = new PluginButton("inserttime", this);
        timeButton = new PluginButton("insertdate", this);
    }

    public PluginButton getDateButton() {
        return dateButton;
    }

    public PluginButton getTimeButton() {
        return timeButton;
    }

    private void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String defineProperties() {
        return "";
    }
}
