package org.innobuilt.wicket.rest.example.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.innobuilt.wicket.rest.example.WicketRestApplication;
import org.innobuilt.wicket.rest.example.model.Person;
import org.innobuilt.wicket.rest.example.service.PersonService;

public class PersonRestServiceHelper {
	public static final String ID_KEY="id";
	public static final String EMAIL_KEY="email";
	public static final String USERNAME_KEY = "username";

	private static PersonService getPersonService() {
		return WicketRestApplication.get().getPersonService();
	}

	public static void doGet(WebPage page, PageParameters params) {
		Person person = null;
		
		//return all if no params
		if (params.isEmpty()) {
			List<Person> persons = getPersonService().getAllPersons();
			//Convert List to ArrayList since wicket likes only serializable objects
			page.setDefaultModel(new Model(new ArrayList(persons)));
			return;
		} 
		Long personId = params.getAsLong(ID_KEY);
		String email = params.getString(EMAIL_KEY);
		String username = params.getString(USERNAME_KEY);
		
		if (personId != null) {
			person = getPersonService().getPerson(personId);
		}
		
		if (person == null && username != null) {
			person = getPersonService().getPerson(username);
		}
		
		if (person == null && email != null) {
			person = getPersonService().getPersonByEmail(email);
		}
		
		if (person != null) {
			page.setDefaultModel(new Model(person));
		}
	}

	public static void doPost(WebPage page, PageParameters params) {
		getPersonService().updatePerson((Person)page.getDefaultModelObject());
	}

	public static void doPut(WebPage page, PageParameters params) {
		Person person = (Person)page.getDefaultModelObject();
		//Ensure that the id is null
		person.setId(null);
		getPersonService().createPerson(person);
	}

	public static void doDelete(WebPage page, PageParameters params) {
		getPersonService().deletePerson(params.getAsLong("id"));
	}
	
	
}
