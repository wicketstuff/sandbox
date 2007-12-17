package org.wicketstuff.teatime;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.wicket.Application;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.IndexedHybridUrlCodingStrategy;
import org.apache.wicket.settings.ISecuritySettings;
import org.jibble.pircbot.IrcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.teatime.logbot.LogBot;
import org.wicketstuff.teatime.logbot.StatusPage;
import org.wicketstuff.teatime.secure.AdminPage;
import org.wicketstuff.teatime.secure.SignInPage;
import org.wicketstuff.teatime.secure.TeatimeAuthorizationStrategy;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	private static final Logger logger = LoggerFactory
			.getLogger(WicketApplication.class);
	private CacheManager manager;
	private String ircLogDir = "/tmp/logbot/";
	private String nick = "Teh-ah-time-eh";
	private String joinMessage = "It's pronounced Teh-ah-tim-eh";
	private String server = "irc.freenode.net";
	private String channel = "##wicket";
	private String password = "admin";
	private String username = "admin";

	private LogBot bot;

	/**
	 * Constructor
	 */
	public WicketApplication() {
	}

	protected void init() {
		super.init();

		initLogBot();
		initCache();
		initSecurity();

		mount(new IndexedHybridUrlCodingStrategy("/log", HomePage.class));
		mount(new IndexedHybridUrlCodingStrategy("/login", SignInPage.class));
		mount(new IndexedHybridUrlCodingStrategy("/admin", AdminPage.class));
		mount(new IndexedHybridUrlCodingStrategy("/status", StatusPage.class));
	}

	private void initSecurity() {
		String value = getInitParameter("teatime.username");
		if (value != null) {
			username = value;
		}
		value = getInitParameter("teatime.password");
		if (value != null) {
			password = value;
		}
		ISecuritySettings security = getSecuritySettings();

		TeatimeAuthorizationStrategy strategy = new TeatimeAuthorizationStrategy();
		security.setAuthorizationStrategy(strategy);
		security.setUnauthorizedComponentInstantiationListener(strategy);
	}

	private void initCache() {
		// Create a CacheManager using defaults
		manager = new CacheManager(getClass().getResourceAsStream(
				"/ehcache.xml"));
		manager.addCache("##wicket");
		String[] cacheNamesForManager = manager.getCacheNames();
		for (String name : cacheNamesForManager)
			logger.info("Cache: " + name);
	}

	private void initLogBot() {
		String value = getInitParameter("irc.output");
		if (value != null) {
			ircLogDir = value;
		}
		if (!ircLogDir.endsWith("/")) {
			ircLogDir = ircLogDir + "/";
		}
		File dir = new File(ircLogDir);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				logger.error("Unable to create the " + ircLogDir
						+ " to write logfiles to.");
				throw new RuntimeException("Unable to create " + ircLogDir);
			}
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
		if (value != null && "true".equalsIgnoreCase(value)) {
			connect();
		}
	}

	private void connect() {
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

	public String getIrcLogDir() {
		return ircLogDir;
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

	public LogBot getBot() {
		return bot;
	}

	public static WicketApplication get() {
		return (WicketApplication) Application.get();
	}

	public Class getHomePage() {
		return HomePage.class;
	}

	private void close(Closeable closeable) {
		if (closeable != null)
			try {
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

	/**
	 * Reconnects the logbot to the irc channel. Basically a complete restart of
	 * the bot.
	 */
	public void reconnect() {
		if (bot != null) {
			try {
				if (bot.isConnected())
					bot.partChannel(channel,
							"Don't worry, I'll return momentarily.");
			} catch (Exception e) {
				logger.warn("Couldn't part channel", e);
			}
			try {
				bot.disconnect();
			} catch (Exception e) {
				logger.warn("Couldn't disconnect from server", e);
			}
			try {
				bot.dispose();
			} catch (Exception e) {
				logger.warn("Couldn't dispose of bot", e);
			}
			bot = null;
		}
		connect();
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new TeatimeSession(request);
	}

	public boolean login(String username, String password) {
		return this.username.equals(username) && this.password.equals(password);
	}
}
