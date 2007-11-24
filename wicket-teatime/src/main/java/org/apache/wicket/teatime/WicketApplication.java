package org.apache.wicket.teatime;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.IndexedHybridUrlCodingStrategy;
import org.apache.wicket.teatime.logbot.LogBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jibble.pircbot.IrcException;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 * @see wicket.myproject.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    private static final Logger logger = LoggerFactory.getLogger(WicketApplication.class);
    private CacheManager manager;
    private String ircLogDir = "/tmp/logbot/";
    private String nick = "teatime4";
    private String joinMessage = "It's pronounced Teh-ah-tim-eh";
    private String server = "irc.freenode.net";
    private String channel = "##wicket";
    private LogBot bot;

    /**
     * Constructor
     */
    public WicketApplication() {
    }

    protected void init() {
        super.init();

        String value = getInitParameter("irc.output");
        if (value != null) {
            ircLogDir = value;
        }
        value = getInitParameter("irc.nick");
        if (value != null) {
            nick = value;
        }
        value = getInitParameter("irc.server");
        if (value != null) {
            server = value;
        }
        value = getInitParameter("irc.channel");
        if (value != null) {
            channel = value;
        }
        value = getInitParameter("irc.joinMessage");
        if (value != null) {
            joinMessage = value;
        }

        value = getInitParameter("irc.start");
        if(value != null && "true".equalsIgnoreCase(value)) {
            bot = new LogBot(nick, new File(ircLogDir), joinMessage);
            try {
                bot.setAutoNickChange(true);
                bot.connect(server);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IrcException e) {
                throw new RuntimeException("Unable to start logbot", e);
            }
            bot.joinChannel(channel);
        }
        //Create a CacheManager using defaults
        manager = new CacheManager(getClass().getResourceAsStream("/ehcache.xml"));
        manager.addCache("##wicket");
        String[] cacheNamesForManager = manager.getCacheNames();
        for (String name : cacheNamesForManager) logger.info("Cache: " + name);
        mount(new IndexedHybridUrlCodingStrategy("/log", HomePage.class));
    }

    protected void onDestroy() {
        if (bot != null) {
            try {
                bot.partChannel(channel, "Let's meet again, shall we?");
                bot.disconnect();
                bot.dispose();
            } catch (Exception e) {
                logger.error("Stopping logbot caused an error", e);
            }
        }
        if (manager != null) {
            try {
                manager.shutdown();
            } catch (Exception e) {
                logger.error("Stopping ehcache caused an error", e);
            }
        }
        super.onDestroy();
    }

    public static WicketApplication get() {
        return (WicketApplication) Application.get();
    }

    public Class getHomePage() {
        return HomePage.class;
    }

    private void close(Closeable closeable) {
        if (closeable != null) try {
            closeable.close();
        } catch (IOException e) {
            // ignore.
        }
    }

    public String loadFromDisk(String filename) {
        File log = new File(ircLogDir + filename + ".log");
        if (!log.exists()) {
            return "Unable to find logs for " + filename;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(log);
            return org.apache.wicket.util.io.Streams.readString(fis);
        } catch (IOException e) {
            return "";
        } finally {
            close(fis);
        }
    }

    public Cache getCache() {
        return manager.getCache("##wicket");
    }
}
