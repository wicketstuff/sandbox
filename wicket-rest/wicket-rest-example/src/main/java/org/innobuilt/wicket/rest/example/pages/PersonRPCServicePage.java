package org.innobuilt.wicket.rest.example.pages;

import org.apache.wicket.PageParameters;
import org.innobuilt.wicket.rest.example.model.Person;
import org.innobuilt.wicket.rest.jsonrpc.Expose;
import org.innobuilt.wicket.rest.jsonrpc.JsonRPCServicePage;

public class PersonRPCServicePage extends JsonRPCServicePage{

	public PersonRPCServicePage(PageParameters params) {
		super(params);
	}
	
	@Expose
	public Person echo(Person p) {
		return p;
	}
	
	public void someOtherMethod(Person p, String in) {
	
	}

}
