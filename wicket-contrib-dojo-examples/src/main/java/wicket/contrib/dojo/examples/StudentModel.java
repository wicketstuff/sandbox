package wicket.contrib.dojo.examples;

import java.io.Serializable;

import wicket.model.IModel;

/**
 * @author vandehaar
 * student Pojo for demo purposes
 */
public class StudentModel implements Serializable
{
	//instant fields are set to create a simple student for testing purposes
	private int studentID;
	private String lastName;
	private String firstName;
	private char sex;
	
	//testing constructor
	public StudentModel()
	{
		this.studentID = 1234;
		this.lastName = "van de Haar";
		this.firstName = "Marco";
		this.sex = 'm';
	}
	
	public StudentModel(int SID, String lname, String fname, char sex)
	{
		this.studentID = SID;
		this.lastName = lname;
		this.firstName = fname;
		this.sex = sex;
	}
	
	//
	
	public int getStudentID()
	{
		return studentID;
	}
	
	public void setStudentID(int id)
	{
		this.studentID = id;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String ln)
	{
		this.lastName = ln;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String fn)
	{
		this.firstName = fn;
	}
	
	public void setSex(char sex)
	{
		this.sex = sex;
	}
	
	public char getSex()
	{
		return sex;
	}
}
