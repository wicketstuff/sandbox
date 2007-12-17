package org.wicketstuff.teatime;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.wicketstuff.teatime.WicketApplication;
import org.wicketstuff.teatime.logbot.LogBotPanel;
import org.wicketstuff.teatime.menu.CalendarMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Homepage
 */
public class HomePage extends BasePage {

    private static final long serialVersionUID = 1L;

    private Date selectedDate;

    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public HomePage(final PageParameters parameters) {
    	super(parameters);

		CompoundPropertyModel model = new CompoundPropertyModel(this);
        setModel(model);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String value = parameters.getString("0", "today");
        if ("today".equalsIgnoreCase(value)) {
            selectedDate = new Date();
        } else {
            try {
                selectedDate = sdf.parse(value);
            } catch (ParseException e) {
                throw new RestartResponseException(HomePage.class);
            }
        }
        IModel dailyLog = new ChannelLogModel(sdf.format(selectedDate));
        add(new Label("logTitle", model.bind("title")));
        add(new Label("log", dailyLog).setEscapeModelStrings(false));
    }
    public String getTitle() {
        return "##wicket logs for " + getDate();
    }
    public String getDate() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Calendar compare = Calendar.getInstance();
        compare.setTime(selectedDate);
        compare.set(Calendar.HOUR, 0);
        compare.set(Calendar.MINUTE, 0);
        compare.set(Calendar.SECOND, 0);
        compare.set(Calendar.MILLISECOND, 0);

        if (today.equals(compare))
            return "today";

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        return sdf.format(selectedDate);
    }

    public static class ChannelLogModel extends LoadableDetachableModel {
        private String logdate;

        public ChannelLogModel(String logdate) {
            this.logdate = logdate;
        }

        private boolean isToday(String text) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(text);
                Date today = sdf.parse(sdf.format(new Date()));
                return today.compareTo(date) == 0;
            } catch (ParseException e) {
                return false;
            }
        }
        protected Object load() {
            String contents = null;
            WicketApplication application = WicketApplication.get();
            if (isToday(logdate)) {
                contents = application.loadFromDisk(logdate);
            } else {
                Cache cache = WicketApplication.get().getCache();
                Element element = cache.get(logdate);
                if (element != null)
                    contents = (String) element.getValue();
                if (contents == null) {
                    contents = application.loadFromDisk(logdate);
                    element = new Element(logdate, contents);

                    application.getCache().put(element);
                }
            }
            return contents;
        }
    }
}
