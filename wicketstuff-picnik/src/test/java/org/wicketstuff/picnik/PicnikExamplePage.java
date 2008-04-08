package org.wicketstuff.picnik;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Request;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.WicketRuntimeException;
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
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.PackageResourceStream;
import org.apache.wicket.util.string.Strings;
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

    	String picnikApi = System.getProperty("picnik.apikey");
    	if (Strings.isEmpty(picnikApi) && LOG.isWarnEnabled()) {
    		LOG.warn("Your Picnik API key must be in Java System property picnik.apikey");
    	}
    	
    	LOG.debug("new PicnikExamplePage in Session " + getSession().getId() + " from "  + ((WebRequest)getRequest()).getHttpServletRequest().getRemoteAddr());
    	
    	add(new NonCachingImage("picnikImage1", new WebResource() {
			@Override
			public IResourceStream getResourceStream() {
				return new FileResourceStream(imageFile1);
			}
    	}));

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
						}						
					}
				};
			}
    	};
    	add(pullPanel);

    	PicnikCall call1 = new PicnikCall();
    	call1.getGeneralSettings().setApiKey(picnikApi);

    	ExportSettings exportSettings1 = pullPanel.getExportSettings();
    	exportSettings1.setTitle("IndyPhone!");
		call1.setExportSettings(exportSettings1);
		
		NavigationSettings navSettings = new NavigationSettings();
		navSettings.setPage("/in/upload");
		call1.setNavigationSettings(navSettings);
		
    	ExternalLink picnikLink1 = new ExternalLink("importLink1", new PicnikLinkModel(call1));
		add(picnikLink1);
    	
		final Image logoImage = new Image("logoImage", new ResourceReference(PullPanel.class, "picnik_logo_small.jpg"));
		picnikLink1.add(logoImage);
    	
    	
    	// ========================================================================

    	add(new NonCachingImage("picnikImage2", new WebResource() {
			@Override
			public IResourceStream getResourceStream() {
				return new FileResourceStream(imageFile2);
			}
    	}));
    	
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
    	exportSettings2.setTitle("IndyPhone!");
		call2.setExportSettings(exportSettings2);
    	
    	add(new ExternalLink("importLink2", new PicnikLinkModel(call2)));

    }
}
