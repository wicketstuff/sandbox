package org.innobuilt.wicket.rest.example.pages;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.model.Model;
import org.innobuilt.wicket.rest.JsonWebServicePage;
import org.innobuilt.wicket.rest.example.WicketRestApplication;
import org.innobuilt.wicket.rest.example.model.Person;
import org.innobuilt.wicket.rest.example.service.PersonService;

public class PersonJsonRestService extends JsonWebServicePage {
	
	public PersonJsonRestService(PageParameters params) {
		super(params, Person.class);
		//I would prefer to only expose the fields I have annotated
		getBuilder().excludeFieldsWithoutExposeAnnotation();

	}

	@Override
	public void doGet(PageParameters params) {
		PersonRestServiceHelper.doGet(this, params);
	}

	@Override
	public void doPost(PageParameters params) {
		PersonRestServiceHelper.doPost(this, params);
	}

	@Override
	public void doPut(PageParameters params) {
		PersonRestServiceHelper.doPut(this, params);
	}

	@Override
	public void doDelete(PageParameters params) {
		PersonRestServiceHelper.doDelete(this, params);
	}
	
	
}
