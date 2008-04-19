/**
 * 
 */
package org.wicketstuff.picnik;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.IPageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.picnik.api.BoxSettings;
import org.wicketstuff.picnik.api.ExportSettings;
import org.wicketstuff.picnik.api.NavigationSettings;
import org.wicketstuff.picnik.api.PicnikCall;

/**
 * Test page were the standard iframe implementation from the
 * picnik.com tutorials is used. From there we can analyse how
 * to integrate with wicket's modalwindow.
 * <p>Created 13.04.2008 09:55:34</p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class PicnikInABoxPage extends WebPage {

	private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(PicnikInABoxPage.class);

	private File imageFile2 = new File("/tmp/picniktest3.jpg");
	
	/**
	 * Create a new PicnikInABoxPage
	 * @param parameters
	 */
	public PicnikInABoxPage(PageParameters parameters) {
		super(parameters);
    	add(new Navbar("navbar"));

    	String picnikApi = System.getProperty("picnik.apikey");
    	if (Strings.isEmpty(picnikApi) && LOG.isWarnEnabled()) {
    		LOG.warn("Your Picnik API key must be in Java System property picnik.apikey");
    	}

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
						
						setResponsePage(PicnikBoxClosePage.class);
					}
					
				};
			}
    	};
    	add(pushtarget);		
    	PicnikCall call3 = new PicnikCall();
    	call3.getGeneralSettings().setApiKey(picnikApi);
    	call3.getImportSettings().setImportSource("http://www.indyphone.de/static/media/89e9e0a2-0d17-49db-97fb-e46a2e8c68ea.png");
    	
    	NavigationSettings navSettings3 = new NavigationSettings();
    	navSettings3.setPage("/edit");
    	
    	ExportSettings exportSettings3 = pushtarget.getExportSettings();
    	exportSettings3.setTitle("IndyPhone!");
		call3.setExportSettings(exportSettings3);
		
		BoxSettings boxSettings3 = new BoxSettings();
		boxSettings3.setExpandButton(false);
		boxSettings3.setCloseTarget("http://rue.dnsdojo.net:42000/wicketstuff-picnik/");
    	
    	PicnikLinkModel picnikLinkModel3 = new PicnikLinkModel(call3);	
		ExternalLink picnikLink1 = new ExternalLink("pboxLink", picnikLinkModel3);
		add(picnikLink1);
    	
	}

}
