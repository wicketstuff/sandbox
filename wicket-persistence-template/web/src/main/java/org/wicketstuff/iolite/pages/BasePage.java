package org.wicketstuff.iolite.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.iolite.persistence.provider.GeneralRepository;

/**
 * Homepage
 */
public class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "generalRepository")
	protected GeneralRepository generalRepository;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public BasePage() {

	}
}
