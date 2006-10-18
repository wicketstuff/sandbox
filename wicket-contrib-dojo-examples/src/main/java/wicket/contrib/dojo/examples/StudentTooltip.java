package wicket.contrib.dojo.examples;

import wicket.Component;
import wicket.MarkupContainer;
import wicket.contrib.markup.html.tooltip.TooltipPanel;
import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.model.IModel;
import wicket.model.Model;

public class StudentTooltip extends TooltipPanel
{

	
	//and I want a constructor with an x, y and a model
	public StudentTooltip(MarkupContainer parent, IModel model, Component target, int x, int y)
	{
		super(parent, model, target, x, y);
		
		StudentModel thisStudent = (StudentModel)getModelObject();
		
		//simply add the components to match the HTML
		new Label(this, "studentid", thisStudent.getStudentID() + "");

		//add a picture which name matches the studentID
		new Image(this, "photo", new Model(thisStudent.getStudentID() + ".jpg"));
		new Label(this, "lname", thisStudent.getLastName());
		new Label(this, "fname", thisStudent.getFirstName());
		new Label(this, "sex", thisStudent.getSex() + "");
		
	}
}
