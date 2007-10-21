package org.wicketstuff.pickwick.frontend.pages;

import java.util.ArrayList;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.JavaScriptReference;
import org.apache.wicket.markup.html.resources.StyleSheetReference;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.PickwickApplication;
import org.wicketstuff.pickwick.auth.PickwickLoginPage;
import org.wicketstuff.pickwick.auth.PickwickLogoutPage;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.backend.pages.BackendLandingPage;
import org.wicketstuff.pickwick.bean.Role;
import org.wicketstuff.pickwick.bean.User;
import org.wicketstuff.pickwick.ext.ExtAnchor;
import org.wicketstuff.pickwick.frontend.panel.UserInscription;
import org.wicketstuff.yui.markup.html.dialog.Dialog;
import org.wicketstuff.yui.markup.html.dialog.DialogSettings;
import org.wicketstuff.yui.markup.html.dialog.DialogSettings.UnderlayType;

import com.google.inject.Inject;

public class BasePage extends WebPage {
	

	@Inject
	Settings settings;
	
	private static final String WEST = "west";
	private Panel west;
	
	private static final String EAST = "east";
	private Panel east;
	
	private static final String SOUTH = "south";
	private Panel south;
	
	private static final String INSCRIPTION_DIALOG = "inscriptionDialog";
	private Dialog inscriptionDialog;
	
	public BasePage(PageParameters parameters) {
		super();
		add(new JavaScriptReference("extBase", ExtAnchor.class, "2.0/adapter/ext/ext-base.js"));
		add(new JavaScriptReference("extAll", ExtAnchor.class, "2.0/ext-all.js"));
		add(new StyleSheetReference("extAllCss", ExtAnchor.class, "2.0/resources/css/ext-all.css"));
		add(new StyleSheetReference("pickwickCss", BasePage.class, "css/pickwick.css"));
		
		initPage(parameters);
		
		addHeader();
		add(west = getWestPanel(WEST));
		add(east = getEastPanel(EAST));
		add(south = getSouthPanel(SOUTH));
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
	 * panel to put on the south
	 * @param id
	 * @return
	 */
	protected Panel getSouthPanel(String id) {
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
		add(logout);
		
		Image userImg = new Image("userImage", new ResourceReference(BasePage.class, "images/users.png"));
		add(userImg);
		Image logOutImg = new Image("logOutImage", new ResourceReference(BasePage.class, "images/log-out.png"));
		logout.add(logOutImg);
		

		PageLink auth = new PageLink("auth", PickwickLoginPage.class);
		add(auth);
		Image logInImg = new Image("logInImage", new ResourceReference(BasePage.class, "images/log-in.png"));
		auth.add(logInImg);
		
		AjaxLink inscription = new AjaxLink("inscription"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				inscriptionDialog.show(target);
			}
		};
		add(inscription);
		Image inscriptionImg = new Image("inscriptionImage", new ResourceReference(BasePage.class, "images/inscription.png"));
		inscription.add(inscriptionImg);
		add(inscriptionDialog = getInscriptionDialog(INSCRIPTION_DIALOG));
		
		
		if (PickwickApplication.get().getPickwickSession().getDefaultUser().getName().equals(name)){
			//anonymous
			userName.setVisible(false);
			userImg.setVisible(false);
			logout.setVisible(false);
			inscription.setVisible(true);
		}else{
			auth.setVisible(false);
			inscription.setVisible(false);
		}
		
		
	}
	
	/**
	 * return the Dialog for inscription
	 * @param inscriptionDialogId
	 * @return
	 */
	public Dialog getInscriptionDialog(String inscriptionDialogId){
		DialogSettings settings = new DialogSettings();
        settings.setModal(true);
        settings.setDraggable(true);
        settings.setFixCenter(true);
        settings.setUnderlay(UnderlayType.SHADOW);
        settings.setVisible(false);
        settings.setModal(true);
        
		Dialog userInscription = new Dialog(inscriptionDialogId, new Model("Inscription"), settings){

			@Override
			public Panel createContent(String arg0) {
				return new UserInscription(arg0, new CompoundPropertyModel(new User())){

					@Override
					public void onSave(AjaxRequestTarget target, Form form) {
						User user = (User)form.getModelObject();
						user.setRoles(new ArrayList<Role>());
						user.setAdmin(false);
						if (getPasswordRepeated() != user.getPassword()){
							form.error("Passwords should be the same");
						} if (BasePage.this.settings.getUserManagement().getUser(user.getName()) != null){
							form.error("User " + user.getName() + "already exists");
						} else{
							BasePage.this.settings.getUserManagement().addUser(user);
							BasePage.this.inscriptionDialog.hide(target);
						}
					}
					
				};
			}
			
		};
		
		return userInscription;
	}

}
