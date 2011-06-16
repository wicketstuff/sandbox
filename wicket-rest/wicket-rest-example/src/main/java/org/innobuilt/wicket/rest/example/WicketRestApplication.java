package org.innobuilt.wicket.rest.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.innobuilt.wicket.rest.example.pages.HomePage;
import org.innobuilt.wicket.rest.example.pages.PersonJsonRestService;
import org.innobuilt.wicket.rest.example.pages.PersonRPCServicePage;
import org.innobuilt.wicket.rest.example.pages.PersonXmlRestService;
import org.innobuilt.wicket.rest.example.service.PersonService;
import org.innobuilt.wicket.rest.jsonrpc.JsonRPCServicePage;

public class WicketRestApplication extends WebApplication {

	private PersonService personService;

	public void init() {
		super.init();

		//TODO make these auto mount with annotation
		mount(new QueryStringUrlCodingStrategy("/person-api/json",PersonJsonRestService.class));
		mount(new QueryStringUrlCodingStrategy("/person-api/xml",PersonXmlRestService.class));
		mount(new QueryStringUrlCodingStrategy("/person-api/rpc",PersonRPCServicePage.class));
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
	
	public static final WicketRestApplication get() {
		return (WicketRestApplication)WebApplication.get();
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

}
