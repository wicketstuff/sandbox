// $Id: $
package org.stickywicket.misc;

import java.io.IOException;

import org.apache.jackrabbit.core.TransientRepository;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.stickywicket.admin.AdminPage;
import org.stickywicket.renderer.Renderer;

import wicket.Page;
import wicket.Session;
import wicket.protocol.http.WebApplication;
import wicket.request.target.coding.IndexedParamUrlCodingStrategy;

public class StickyWicketApplication extends WebApplication {

	static String JCR_NAMESPACE_PREFIX = "http://stickywicket.org";

	private TransientRepository repository;

	@Override
	protected void init() {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
		mountBookmarkablePage("/admin", AdminPage.class);

		mount(new IndexedParamUrlCodingStrategy("/render", Renderer.class));
		try {
			repository = new TransientRepository();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Session newSession() {
		return new StickyWicketSession(StickyWicketApplication.this);
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return AdminPage.class;
	}

	public static StickyWicketApplication get() {
		return (StickyWicketApplication) WebApplication.get();
	}

	public TransientRepository getRepository() {
		return repository;
	}
}
