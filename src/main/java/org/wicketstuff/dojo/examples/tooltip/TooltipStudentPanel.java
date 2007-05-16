package org.wicketstuff.dojo.examples.tooltip;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class TooltipStudentPanel extends Panel
{

	
	//and I want a constructor with an x, y and a model
	public TooltipStudentPanel(String id, IModel model)
	{
		super(id, model);
		
		TooltipStudentModel thisStudent = (TooltipStudentModel)getModelObject();
		
		//simply add the components to match the HTML
		add(new Label("studentid", thisStudent.getStudentID() + ""));

		//add a picture which name matches the studentID
		add(new Image("photo", new Model(thisStudent.getStudentID() + ".jpg")));
		add(new Label("lname", thisStudent.getLastName()));
		add(new Label("fname", thisStudent.getFirstName()));
		add(new Label("sex", thisStudent.getSex() + ""));
		
	}
}
