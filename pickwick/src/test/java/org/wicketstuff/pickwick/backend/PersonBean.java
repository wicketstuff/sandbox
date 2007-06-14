package org.wicketstuff.pickwick.backend;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("person")
public class PersonBean {
    
    private String name;
    private int age;
    private boolean isM; 
    private ArrayList<String> names;
    
    /** Need to allow bean to be created via reflection */
    public PersonBean() {}
    
    public PersonBean(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }	
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String toString() {
        return "PersonBean[name='" + name + "',age='" + age + "']";
    }

	public boolean isM() {
		return isM;
	}

	public void setM(boolean isM) {
		this.isM = isM;
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}
}