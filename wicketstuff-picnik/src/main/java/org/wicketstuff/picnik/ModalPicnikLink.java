package org.wicketstuff.picnik;

import java.util.HashMap;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.PackagedResourceReference;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.template.TextTemplateHeaderContributor;
import org.wicketstuff.extensions.ExternalModalWindow;
import org.wicketstuff.picnik.api.PicnikCall;

/**
 * Panel which creates a button, which will open picnik
 * in a modal window. After successful operation in
 * picnik, the form in the PushTargetPanel or PullPanel must
 * continue on CloseModalPage.
 * <p>Created 19.04.2008 21:44:25</p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class ModalPicnikLink extends Panel {

	/**
	 * Create a new ModalPicnikPanel
	 * @param id
	 * @param picnikLinkModel
	 */
	public ModalPicnikLink(String id, PicnikLinkModel picnikLinkModel) 
	{
		super(id);

    	final ModalWindow modalWindow = new ExternalModalWindow("modalPicnikWindow", picnikLinkModel);
    	configureModalWindow(modalWindow);
    	add(modalWindow);
    	final AjaxLink showModalLink = new AjaxLink("showModalLink")
        {
            @Override
			public void onClick(AjaxRequestTarget target)
            {
            	modalWindow.show(target);
            }
        };
        showModalLink.add(new Image("picnikLogo", new ResourceReference(ModalPicnikLink.class, "picnik_logo_small.jpg")));
        configureShowModalLink(showModalLink);
		add(showModalLink);
    	
    	// this link will be clicked indirectly via javascript from CloseModalPage
    	final AjaxLink closeModalLink = new AjaxLink("closeModalLink")
    	{
            @Override
			public void onClick(AjaxRequestTarget target)
            {
            	modalWindow.close(target);
            	onPicnikFinished(target);
            }

    	};
		add(closeModalLink);
		
		HashMap<String, String> m = new HashMap<String, String>();
		m.put("closeModalLinkId", closeModalLink.getMarkupId());
		add(TextTemplateHeaderContributor.forJavaScript(ModalPicnikLink.class, "closeModalIndirection.js", new Model(m)));
	}

	/**
	 * Subclasses can modify the link with additional behavior.
	 * @param showModalLink
	 */
	protected void configureShowModalLink(final AjaxLink showModalLink) 
	{
		// override in subclass
	}
	
	/**
	 * Here you can make modifications to ModalWindow settings.
	 * @param modalWindow
	 */
	protected void configureModalWindow(ModalWindow modalWindow)
	{
		modalWindow.setResizable(false);
		modalWindow.setUseInitialHeight(true);
		modalWindow.setWidthUnit("%");
		modalWindow.setHeightUnit("%");
		modalWindow.setInitialHeight(95);
		modalWindow.setInitialWidth(95);
		modalWindow.setTitle("Picnik");
	}

	/**
	 * Listener method invoked after the ModalWindow has been closed
	 * via the JavaScript in ClosModalPage.
	 * This method is not called if the ModalWindow is closed with
	 * the close button.
	 * @param target
	 */
	protected void onPicnikFinished(AjaxRequestTarget target) 
	{
		// override in subclass
	}

}

