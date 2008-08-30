package org.wicketstuff.iolite.pages;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	/**
	 * Constructor
	 */

	private SpringComponentInjector springComponentInjector;

	public SpringComponentInjector getSpringComponentInjector() {
		return springComponentInjector;
	}

	public void setSpringComponentInjector(
			SpringComponentInjector springComponentInjector) {
		this.springComponentInjector = springComponentInjector;
	}

	public WicketApplication() {

	}

	@Override
	protected void init() {
		super.init();
		if (springComponentInjector == null) {
			this.springComponentInjector = new SpringComponentInjector(this);
		}
		addComponentInstantiationListener(springComponentInjector);

	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	public Class getHomePage() {
		return HomePage.class;
	}

}
