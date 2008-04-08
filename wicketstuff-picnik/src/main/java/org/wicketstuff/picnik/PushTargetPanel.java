/**
 * 
 */
package org.wicketstuff.picnik;

import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.IFormSubmitListener;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.RequestUtils;
import org.wicketstuff.picnik.api.ExportSettings;
import org.wicketstuff.picnik.api.ExportSettings.Agent;
import org.wicketstuff.picnik.api.ExportSettings.Method;

/**
 * Form with FileuploadField, which will receive push events from Picnik. The Picnik call starting
 * the push-request should use {@link #getExportSettings()}, which will provide the necessary
 * API-parameters.
 * <p>
 * Created 30.03.2008 19:48:30
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class PushTargetPanel extends Panel {

	private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(PushTargetPanel.class);

	private StatelessForm form;

	private FileUploadField fileUploadField;

	private HiddenField idField;

	/**
	 * Create a new PushtargetPanel
	 * @param id
	 */
	public PushTargetPanel(final String id) {
		this(id, new Model(new PicnikFileUpload()));
	}

	/**
	 * Create a new PushtargetPanel. The model is passed through to the internal Form, which in turn
	 * is created by {@link #createForm(String, IModel)}.
	 * @param id
	 * @param model providing a PicnikFileUpload
	 */
	public PushTargetPanel(final String id, final IModel/* <PicnikFileUpload> */ model) {
		super(id);
		form = createForm("pushForm", model);
		form.setMultiPart(true);
		add(form);

		fileUploadField = new FileUploadField("fileUploadField", new PropertyModel(model, "fileUpload"));
		form.add(fileUploadField);

		idField = new HiddenField("idField", new PropertyModel(model, "id")) {
			/**
			 * Name attribute of this field must be &quot;_imageid&quot; because Picnik sends the
			 * imageid with this HTTP parameter.
			 * @see org.apache.wicket.markup.html.form.FormComponent#getInputName()
			 */
			@Override
			public String getInputName() {
				return "_imageid";
			}
		};
		form.add(idField);
	}

	/**
	 * Creates the form in the Panel. It will be submitted via Picnik. Overwrite in subclasses and
	 * provide your own StatelessForm with behavior. Especially, you should do something with the
	 * FileUpload from the model object, as it will be gone after the request.
	 * <p>
	 * This form will receive a POST directly from Picnik server. So it must be a Stateless-Form;
	 * the session would not be the same as the one starting the action. The only way you can
	 * preserve state is via the imageid parameter.
	 * </p>
	 * @param id the id of the form
	 * @param model model passed through to the form.
	 * @return
	 */
	protected StatelessForm createForm(final String id, IModel model) {
		return new StatelessForm(id, model);
	}

	/**
	 * Get Picnik Exportsettings matching this Panel. Use it in a PicnikCall, so that the resulting
	 * image can be pushed to this PushTargetPanel.
	 * @return
	 */
	public ExportSettings getExportSettings() {
		ExportSettings ret = new ExportSettings();
		ret.setAgent(Agent.picnik);
		ret.setMethod(Method.POST);
		ret.setField(fileUploadField.getInputName());
		ret.setTarget(getFormUrl());
		return ret;
	}

	private String getFormUrl() {
		// copied from org.apache.wicket.markup.html.form.Form
		return RequestUtils.toAbsolutePath(form.urlFor(IFormSubmitListener.INTERFACE).toString());
	}
}
