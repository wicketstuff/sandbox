package org.wicketstuff.pickwick.backend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.JavaScriptReference;
import org.apache.wicket.markup.html.resources.StyleSheetReference;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.auth.PickwickLoginPage;
import org.wicketstuff.pickwick.auth.PickwickLogoutPage;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.ext.ExtAnchor;
import org.wicketstuff.pickwick.frontend.pages.BasePage;

public class BaseAdminPage extends WebPage {
	
	private static final String WEST = "west";
	private Panel west;
	
	private static final String EAST = "east";
	private Panel east;
	
	public BaseAdminPage(PageParameters parameters) {
		super();
		add(new JavaScriptReference("extBase", ExtAnchor.class, "2.0/adapter/ext/ext-base.js"));
		add(new JavaScriptReference("extAll", ExtAnchor.class, "2.0/ext-all.js"));
		add(new StyleSheetReference("extAllCss", ExtAnchor.class, "2.0/resources/css/ext-all.css"));
		add(new StyleSheetReference("pickwickCss", BasePage.class, "css/pickwick.css"));
		
		initPage(parameters);
		
		addHeader();
		add(west = getWestPanel(WEST));
		add(east = getEastPanel(EAST));
	}
	
	/**
	 * method to init the page
	 * @param parameters
	 */
	protected void initPage(PageParameters parameters) {
	}

	/**
	 * panel to put on the west
	 * @param id
	 * @return
	 */
	protected Panel getWestPanel(String id) {
		return new EmptyPanel(id);
	}
	
	/**
	 * panel to put on the east
	 * @param id
	 * @return
	 */
	protected Panel getEastPanel(String id) {
		return new EmptyPanel(id);
	}

	/**
	 * top of the application
	 *
	 */
	public void addHeader(){
		BookmarkablePageLink backend = new BookmarkablePageLink("back", BackendLandingPage.class);
		add(backend);
		Image bckImg = new Image("bckImage", new ResourceReference(BasePage.class, "images/backend.png"));
		backend.add(bckImg);
		String name = PickwickSession.get().getUserName();
		if(PickwickSession.get().getUser().isAdmin()){
			backend.setVisible(true);
		}else{
			backend.setVisible(false);
		}
		
		Label userName = new Label("userName", new Model(name));
		PageLink auth = new PageLink("auth", PickwickLoginPage.class);
		PageLink logout = new PageLink("logout", PickwickLogoutPage.class);
		PageLink home = new PageLink("home", getApplication().getHomePage());
		add(home);
		home.add(new Image("homeImage", new ResourceReference(BasePage.class, "images/home.png")));
		add(userName);
		add(auth);
		add(logout);
		
		Image userImg = new Image("userImage", new ResourceReference(BasePage.class, "images/users.png"));
		add(userImg);
		Image logInImg = new Image("logInImage", new ResourceReference(BasePage.class, "images/log-in.png"));
		auth.add(logInImg);
		Image logOutImg = new Image("logOutImage", new ResourceReference(BasePage.class, "images/log-out.png"));
		logout.add(logOutImg);
		
		if (PickwickApplication.get().getPickwickSession().getDefaultUser().getName().equals(name)){
			//anonymous
			userName.setVisible(false);
			userImg.setVisible(false);
			logout.setVisible(false);
		}else{
			auth.setVisible(false);
		}
		
		
	}

}
