package org.wicketstuff.picnik;

import java.util.HashMap;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.PackageResource;
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
		add(TextTemplateHeaderContributor.forJavaScript(getClass(), "closeModalIndirection.js", new Model(m)));
	}

	/**
	 * Appends a style attribute that will put the picnik logo into this link.
	 * @param showModalLink
	 */
	protected void configureShowModalLink(final AjaxLink showModalLink) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("background-image:url(");
		buf.append(urlFor(new ResourceReference(ModalPicnikLink.class, "picnik_logo_small.jpg")));
		buf.append(");width:66px;height:27px;display:block;");
		showModalLink.add(new SimpleAttributeModifier("style", buf));
	}
	
	/**
	 * Here you can make modifications to ModalWindow settings.
	 * @param modalWindow
	 */
	protected void configureModalWindow(ModalWindow modalWindow)
	{
		modalWindow.setResizable(false);
		modalWindow.setWidthUnit("%");
		modalWindow.setHeightUnit("%");
		modalWindow.setInitialHeight(95);
		modalWindow.setInitialWidth(95);
		modalWindow.setTitle("Picnik");
	}

	
	protected void onPicnikFinished(AjaxRequestTarget target) 
	{
		// override in subclass
	}

}

