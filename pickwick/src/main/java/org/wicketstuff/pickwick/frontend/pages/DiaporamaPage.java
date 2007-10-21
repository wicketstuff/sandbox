package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.resources.StyleSheetReference;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.Utils;
import org.wicketstuff.pickwick.auth.PickwickAuthorization;
import org.wicketstuff.pickwick.auth.PickwickLoginPage;
import org.wicketstuff.pickwick.auth.PickwickLogoutPage;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.backend.pages.BackendLandingPage;
import org.wicketstuff.pickwick.frontend.diaporama.DiaporamaPanel;

import com.google.inject.Inject;


/**
 * Page to display a single image
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class DiaporamaPage extends WebPage {
	@Inject
	Settings settings;
	
	public DiaporamaPage(PageParameters parameters) {
		super();
		String uri = Utils.getUri(parameters);
		//check user authorisation
		if(uri != ""){
			PickwickAuthorization.check(settings.getImageDirectoryRoot() + "/" + uri, PickwickSession.get());
		}
		add(new StyleSheetReference("pickwickCss", BasePage.class, "css/pickwick.css"));
		
		addHeader();
		add(new DiaporamaPanel("diapo", new Model(uri)));
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
		WebMarkupContainer home = new WebMarkupContainer("home"){
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("href", "./");
			}
		};
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
