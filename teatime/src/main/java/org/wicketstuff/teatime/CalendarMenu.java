package org.wicketstuff.teatime;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.wicketstuff.teatime.HomePage;

import java.io.File;
import java.io.FilenameFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: dashorst
 * Date: Nov 24, 2007
 * Time: 8:08:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalendarMenu extends Panel {
    public CalendarMenu(String id) {
        super(id);
        add(new BookmarkablePageLink("today", HomePage.class, new PageParameters("0=today")){
            protected CharSequence getURL() {
                return new AppendingStringBuffer(super.getURL()).append("#now");
            }
        });
        IModel files = new LoadableDetachableModel() {
            protected Object load() {
                String dir = WicketApplication.get().getIrcLogDir();
                List<File> list = Arrays.asList(new File(dir).listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.startsWith("20") && name.endsWith(".log");
                    }
                }));
                Collections.sort(list, new Comparator<File>(){
                    public int compare(File o1, File o2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String f1 = o1.getName();
                        f1 = f1.substring(0, Math.max(0, f1.length() - 4));
                        String f2 = o2.getName();
                        f2 = f2.substring(0, Math.max(0, f2.length() - 4));
                        Date d1 = null;
                        Date d2 = null;
                        try {
                            d1 = sdf.parse(f1);
                            d2 = sdf.parse(f2);
                        } catch (ParseException e) {
                            return 0;
                        }
                        return d2.compareTo(d1);
                    }
                });
                return list;
            }
        };
        add(new PropertyListView("files", files) {
            protected void populateItem(ListItem item) {
                File log = (File) item.getModelObject();
                String name = log.getName();
                name = name.substring(0, Math.max(0, name.length() - 4));
                PageParameters pars = new PageParameters("0=" + name);
                item.add(new BookmarkablePageLink("link", HomePage.class, pars).add(new Label("name", name)));
            }
        });

    }
}
