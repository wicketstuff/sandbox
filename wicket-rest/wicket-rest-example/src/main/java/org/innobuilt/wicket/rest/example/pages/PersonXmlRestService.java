package org.innobuilt.wicket.rest.example.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.model.Model;
import org.innobuilt.wicket.rest.XmlWebServicePage;
import org.innobuilt.wicket.rest.example.WicketRestApplication;
import org.innobuilt.wicket.rest.example.model.Person;
import org.innobuilt.wicket.rest.example.service.PersonService;

public class PersonXmlRestService extends XmlWebServicePage {

	public PersonXmlRestService(PageParameters params) {
		super(params);
		//set up an xstream alias for the Person class so we don't see the package name
		getXStream().alias("person", Person.class);
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
