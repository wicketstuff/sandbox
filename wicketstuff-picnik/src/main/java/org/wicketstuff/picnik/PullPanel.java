/**
 * 
 */
package org.wicketstuff.picnik;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.IFormSubmitListener;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.PackagedResourceReference;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.util.convert.ConversionException;
import org.wicketstuff.picnik.api.ExportSettings;
import org.wicketstuff.picnik.api.ExportSettings.Agent;
import org.wicketstuff.picnik.api.ExportSettings.Method;
import org.wicketstuff.pullupload.PulledFileUpload;

/**
 * 
 * <p>Created 06.04.2008 15:42:39</p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class PullPanel extends Panel {

	private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(PullPanel.class);
	
	private final Form form;

	private final HiddenField urlField;
	
	/**
	 * Create a new PullPanel
	 * @param id
	 */
	public PullPanel(String id) {
		this(id, new Model(new PicnikFileUpload()));
	}

	/**
	 * Create a new PullPanel
	 * @param id
	 * @param model
	 */
	public PullPanel(final String id, final IModel model) {
		super(id, model);
		
		form = createForm("form", model);
		add(form);
		
		urlField = new HiddenField("urlField", new PropertyModel(model, "fileUpload"));
		urlField.setType(PulledFileUpload.class);
		form.add(urlField);
	}
	
	public ExportSettings getExportSettings() {
		ExportSettings ret = new ExportSettings();
		ret.setAgent(Agent.browser);
		ret.setMethod(Method.POST);
		ret.setField(urlField.getInputName());
		ret.setTarget(getFormUrl());
		return ret;
	}

	private String getFormUrl() {
		// copied from org.apache.wicket.markup.html.form.Form
		return RequestUtils.toAbsolutePath(form.urlFor(IFormSubmitListener.INTERFACE).toString());
	}
	
	/**
	 * Creates the form used for the pull handling. Overwrite in subclasses and
	 * provide your own Form with behavior. Especially, you should do something with the
	 * FileUpload from the model object, as it will be gone after the request.
	 * @param id
	 * @param model
	 * @return
	 */
	protected Form createForm(String id, IModel model) {
		return new Form(id, model);
	}
}
