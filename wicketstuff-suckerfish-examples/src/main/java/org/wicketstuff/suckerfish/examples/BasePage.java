package org.wicketstuff.suckerfish.examples;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.suckerfish.SuckerfishMenuPanel;

/**
 * Base page for all the other pages
 * 
 * Author: Julian Sinai http://javathoughts.capesugarbird.com
 * 
 * This sample code is released under the Apache 2 license.
 * 
 */
public class BasePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	public BasePage(final PageParameters parameters)
	{
		// Create the menubar
		final SuckerfishMenuPanel mb = new SuckerfishMenuPanel("menuBar");
		add(mb);

		// Create a menu. Clicking on the menu itself will take you to the home
		// page
		final SuckerfishMenuPanel.MenuItem mi = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						HomePage.class), "Home");
		mb.addMenu(mi);

		// Create a submenu to point to MyFirstPage
		final SuckerfishMenuPanel.MenuItem submi = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						MyFirstPage.class), "First Page");
		mi.addMenu(submi);

		// Create a submenu to point to MySecondPage
		final SuckerfishMenuPanel.MenuItem submi2 = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						MySecondPage.class), "Second Page");
		mi.addMenu(submi2);

		// Create a second menu without a link to the home
		// page
		final SuckerfishMenuPanel.MenuItem mi2 = new SuckerfishMenuPanel.MenuItem(
				"Home");
		mb.addMenu(mi2);

		// Create a submenu to point to MyFirstPage
		final SuckerfishMenuPanel.MenuItem submi3 = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						MyFirstPage.class), "First Page");
		mi2.addMenu(submi3);

		// Create a submenu to point to MySecondPage without a link
		final SuckerfishMenuPanel.MenuItem submi4 = new SuckerfishMenuPanel.MenuItem(
				"My Sub-Menu");
		mi2.addMenu(submi4);

		// Create a subsubmenu to point to HomePage
		final SuckerfishMenuPanel.MenuItem submi5 = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						HomePage.class), "Home");
		submi4.addMenu(submi5);
	}
}
