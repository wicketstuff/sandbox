package org.wicketstuff.pickwick.backend.panel;

import java.io.File;
import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitButton;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.Role;
import org.wicketstuff.pickwick.bean.Roles;
import org.wicketstuff.pickwick.bean.Sequence;
import org.wicketstuff.yui.markup.html.editor.YuiEditor;

import com.google.inject.Inject;

/**
 * FIXME get image directory using wrapped model to avoid replacing the whole
 * panel
 */
public abstract class SequencePropertiesPanel extends Panel {
	private static final Logger log = LoggerFactory.getLogger(SequencePropertiesPanel.class);
	private File imageDirectory;

	@Inject
	Settings settings;
	
	public static final String FORM = "sequenceForm";

	public static final String TITLE = "title";

	public static final String DESCRIPTION = "description";

	public static final String DATE = "date";
	
	public static final String ROLE = "roles";

	private Form form;
	private TextArea description;

	public SequencePropertiesPanel(String id) {
		super(id);

		form = new Form(FORM);
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		TextField title = new TextField(TITLE);
		description = new TextArea(DESCRIPTION);
		DateField date = new DateField(DATE);
		

		form.add(title);
		form.add(description);
		form.add(date);
		
		Roles roles = settings.getUserManagement().getAllRoles();
		Palette role = new Palette("roles", new Model(roles), new ChoiceRenderer("label", "label"), 6, false);
		form.add(role);
		
		form.add(new AjaxSubmitButton("save", form) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				Sequence sequence = (Sequence) form.getModelObject();
				ImageUtils.writeSequence(sequence, imageDirectory);
				log.info("Wrote sequence: " + sequence + " to image directory: " + imageDirectory);
				onSave(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form form) {
				super.onError(target, form);
				target.addComponent(feedbackPanel);
			}

		});

		add(form);
		add(feedbackPanel);
	}

	public void setImageDirectory(File file) {
		this.imageDirectory = file;
		Sequence sequenceProperties = ImageUtils.readSequence(file);
		if (sequenceProperties == null){
			sequenceProperties = new Sequence();
			sequenceProperties.setRoles(new ArrayList<Role>());
		}
		log.debug("sequence: " + sequenceProperties);
		form.setModel(new CompoundPropertyModel(sequenceProperties));
		//description.setModel(new PropertyModel(form.getModelObject(), "description"));
	}

	@Override
	public boolean isVisible() {
		return imageDirectory != null;
	}

	public abstract void onSave(AjaxRequestTarget target);
}
