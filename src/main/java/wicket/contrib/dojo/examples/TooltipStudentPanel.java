package wicket.contrib.dojo.examples;

import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.Model;

public class TooltipStudentPanel extends Panel
{

	
	//and I want a constructor with an x, y and a model
	public TooltipStudentPanel(MarkupContainer parent, String id, IModel model)
	{
		super(parent, id, model);
		
		TooltipStudentModel thisStudent = (TooltipStudentModel)getModelObject();
		
		//simply add the components to match the HTML
		new Label(this, "studentid", thisStudent.getStudentID() + "");

		//add a picture which name matches the studentID
		new Image(this, "photo", new Model(thisStudent.getStudentID() + ".jpg"));
		new Label(this, "lname", thisStudent.getLastName());
		new Label(this, "fname", thisStudent.getFirstName());
		new Label(this, "sex", thisStudent.getSex() + "");
		
	}
}
