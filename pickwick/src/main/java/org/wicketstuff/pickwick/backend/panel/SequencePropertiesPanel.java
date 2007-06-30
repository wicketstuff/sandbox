package org.wicketstuff.pickwick.backend.panel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.markup.html.form.DojoDatePicker;
import org.wicketstuff.dojo.markup.html.richtexteditor.DojoRichTextEditorBehavior;
import org.wicketstuff.pickwick.backend.DefaultSettings;
import org.wicketstuff.pickwick.backend.XmlBeanMapper;
import org.wicketstuff.pickwick.bean.Sequence;

public class SequencePropertiesPanel extends Panel {

	public static final String FORM = "sequenceForm";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String DATE = "date";
	
	public Sequence sequenceProperties;
	
	
	public transient XmlBeanMapper<Sequence> mapper;
	
	public SequencePropertiesPanel(String id, final String uri) {
		super(id);	

		mapper = new XmlBeanMapper<Sequence>(Sequence.class);
		sequenceProperties = readXml(uri);
		
		Form form = new Form(FORM, new CompoundPropertyModel(sequenceProperties)){

			@Override
			protected void onSubmit() {
				super.onSubmit();
				try {
					mapper.serializeInXml((Sequence)getModelObject(), new FileOutputStream(new File(uri)));
				} catch (FileNotFoundException e) {
					throw new RuntimeException("Can not writte file for " + uri,e);
				}
			}
			
		};
		
		TextField title = new TextField(TITLE);
		TextArea description = new TextArea(DESCRIPTION);
		description.add(new DojoRichTextEditorBehavior());
		DojoDatePicker date = new DojoDatePicker(DATE, "mm/dd/yyyy");
		
		form.add(title);
		form.add(description);
		form.add(date);
		
		add(form);
	}

	private Sequence readXml(String uri) {
		Sequence sequence = new Sequence();
		try {
			sequence = mapper.bindInBean(new FileInputStream(new File(uri)));
		} catch (FileNotFoundException e) {	
			//no file found : Do nothink
			//TODO : log 
		}
		return sequence;
	}

}
