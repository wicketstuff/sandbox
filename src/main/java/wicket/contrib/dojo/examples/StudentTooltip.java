package wicket.contrib.dojo.examples;

import wicket.Component;
import wicket.contrib.markup.html.tooltip.TooltipPanel;
import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.model.IModel;
import wicket.model.Model;

public class StudentTooltip extends TooltipPanel
{

	
	//and I want a constructor with an x, y and a model
	public StudentTooltip(IModel model, Component target, int x, int y)
	{
		super(model, target, x, y);
		
		StudentModel thisStudent = (StudentModel)getModelObject();
		
		//simply add the components to match the HTML
		add(new Label("studentid", thisStudent.getStudentID() + ""));

		//add a picture which name matches the studentID
		add(new Image("photo", new Model(thisStudent.getStudentID() + ".jpg")));
		add(new Label("lname", thisStudent.getLastName()));
		add(new Label("fname", thisStudent.getFirstName()));
		add(new Label("sex", thisStudent.getSex() + ""));
		
	}
}
