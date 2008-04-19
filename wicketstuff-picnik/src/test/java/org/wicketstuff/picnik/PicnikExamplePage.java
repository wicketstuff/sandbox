package org.wicketstuff.picnik;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Request;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.PackageResourceStream;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.apache.wicket.util.template.TextTemplate;
import org.apache.wicket.util.template.TextTemplateHeaderContributor;
import org.wicketstuff.extensions.ExternalInlineFrame;
import org.wicketstuff.extensions.ExternalModalWindow;
import org.wicketstuff.picnik.api.BoxSettings;
import org.wicketstuff.picnik.api.ExportSettings;
import org.wicketstuff.picnik.api.NavigationSettings;
import org.wicketstuff.picnik.api.PicnikCall;

/**
 * Simple page for testing the Picnic control.
 */
public class PicnikExamplePage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(PicnikExamplePage.class);

	private File imageFile1 = new File("/tmp/picniktest1.jpg");
	private File imageFile2 = new File("/tmp/picniktest2.jpg");

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public PicnikExamplePage(final PageParameters parameters) {
    	
    	add(new Navbar("navbar"));

    	String picnikApi = System.getProperty("picnik.apikey");
    	if (Strings.isEmpty(picnikApi) && LOG.isWarnEnabled()) {
    		LOG.warn("Your Picnik API key must be in Java System property picnik.apikey");
    	}
    	
    	LOG.debug("new PicnikExamplePage in Session " + getSession().getId() + " from "  + ((WebRequest)getRequest()).getHttpServletRequest().getRemoteAddr());
    	
    	final NonCachingImage nonCachingImage1 = new NonCachingImage("picnikImage1", new WebResource() {
			@Override
			public IResourceStream getResourceStream() {
				return new FileResourceStream(imageFile1);
			}
    	});
    	nonCachingImage1.setOutputMarkupId(true);
		add(nonCachingImage1);

    	PullPanel pullPanel = new PullPanel("pullPanel") {
			@Override
			protected Form createForm(String id, IModel model) {
				return new Form(id, model) {
					@Override
					protected void onSubmit() {
						PicnikFileUpload upload = (PicnikFileUpload) getModelObject();
						
				    	LOG.debug("File received in Session " + getSession().getId() + " from "  + ((WebRequest)getRequest()).getHttpServletRequest().getRemoteAddr());
				    	
						FileUpload fileUpload = upload.getFileUpload();
						if (fileUpload != null) {
							LOG.debug("Size: " + fileUpload.getSize());
							LOG.debug("Name: " + fileUpload.getClientFileName());
							LOG.debug("Type: " + fileUpload.getContentType());
							try {
								fileUpload.writeTo(imageFile1);
							} catch (IOException e) {
								throw new WicketRuntimeException(e);
							}
							// following line is actually only required when doing the modal way
							setResponsePage(CloseModalPage.class);							
						}						
					}
				};
			}
    	};
    	add(pullPanel);

    	PicnikCall call1 = new PicnikCall();
    	call1.getGeneralSettings().setApiKey(picnikApi);

    	ExportSettings exportSettings1 = pullPanel.getExportSettings();
    	exportSettings1.setTitle("Wicket!");
		call1.setExportSettings(exportSettings1);
		
		NavigationSettings navSettings = new NavigationSettings();
		navSettings.setPage("/in/upload");
		call1.setNavigationSettings(navSettings);
		
    	PicnikLinkModel picnikLinkModel1 = new PicnikLinkModel(call1);
		ExternalLink picnikLink1 = new ExternalLink("importLink1", picnikLinkModel1);
		add(picnikLink1);
    	
		final Image logoImage = new Image("logoImage", new ResourceReference(PullPanel.class, "picnik_logo_small.jpg"));
		picnikLink1.add(logoImage);

    	// ========================================================================
		// same thing, but in modal window
    	PicnikCall call3 = new PicnikCall();
    	call3.getGeneralSettings().setApiKey(picnikApi);
    	call3.getImportSettings().setImportSource("http://www.indyphone.de/static/media/89e9e0a2-0d17-49db-97fb-e46a2e8c68ea.png");
    	
    	NavigationSettings navSettings3 = new NavigationSettings();
    	navSettings3.setPage("/edit");
    	
    	ExportSettings exportSettings3 = pullPanel.getExportSettings();
    	exportSettings3.setTitle("Wicket!");
		call3.setExportSettings(exportSettings3);
		
		BoxSettings boxSettings3 = new BoxSettings();
		boxSettings3.setExpandButton(false);
		boxSettings3.setCloseTarget("http://rue.dnsdojo.net:42000/wicketstuff-picnik/");
    	
    	PicnikLinkModel picnikLinkModel3 = new PicnikLinkModel(call3);
    	
		ModalPicnikLink modalPicnikLink = new ModalPicnikLink("modalPicnikLink", picnikLinkModel3)
		{
			@Override
			protected void onPicnikFinished(AjaxRequestTarget target) {
            	target.addComponent(nonCachingImage1);
			}
			
		};
		add(modalPicnikLink);
		
    	// ========================================================================

    	final NonCachingImage picnikImage2 = new NonCachingImage("picnikImage2", new WebResource() {
			@Override
			public IResourceStream getResourceStream() {
				return new FileResourceStream(imageFile2);
			}
    	});
    	picnikImage2.setOutputMarkupId(true);
		add(picnikImage2);
    	
    	PushTargetPanel pushtarget = new PushTargetPanel("pushtargetPanel") {
			/**
			 * @see org.wicketstuff.picnik.PushTargetPanel#createForm(java.lang.String)
			 */
			@Override
			protected StatelessForm createForm(String id, IModel model) {
				return new StatelessForm(id, model) {
					/**
					 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
					 */
					@Override
					protected void onSubmit() {
						PicnikFileUpload upload = (PicnikFileUpload) getModelObject();
						
				    	LOG.debug("File received in Session " + getSession().getId() + " from "  + ((WebRequest)getRequest()).getHttpServletRequest().getRemoteAddr());
				    	
						FileUpload fileUpload = upload.getFileUpload();
						if (fileUpload != null) {
							LOG.debug("Size: " + fileUpload.getSize());
							LOG.debug("Name: " + fileUpload.getClientFileName());
							LOG.debug("Type: " + fileUpload.getContentType());
							try {
								fileUpload.writeTo(imageFile2);
							} catch (IOException e) {
								throw new WicketRuntimeException(e);
							}
						}
						setResponsePage(CloseModalPage.class);
					}
					
				};
			}
    	};
    	add(pushtarget);
    	
    	// import of static image to Picnik
    	PicnikCall call2 = new PicnikCall();
    	call2.getGeneralSettings().setApiKey(picnikApi);
    	call2.getImportSettings().setImportSource("http://www.indyphone.de/static/media/89e9e0a2-0d17-49db-97fb-e46a2e8c68ea.png");
    	
    	ExportSettings exportSettings2 = pushtarget.getExportSettings();
    	exportSettings2.setTitle("Wicket!");
		call2.setExportSettings(exportSettings2);
    	
    	PicnikLinkModel picnikLinkModel2 = new PicnikLinkModel(call2);
		add(new ExternalLink("importLink2", picnikLinkModel2));
    }
}
