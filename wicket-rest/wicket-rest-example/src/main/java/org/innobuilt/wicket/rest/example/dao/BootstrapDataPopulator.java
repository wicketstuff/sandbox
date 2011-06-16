package org.innobuilt.wicket.rest.example.dao;

import org.innobuilt.wicket.rest.example.service.PersonService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Loads sample data for the sample app since it's an in-memory database.
 */
public class BootstrapDataPopulator implements InitializingBean {

    private PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public void afterPropertiesSet() throws Exception {
        //because we're using an in-memory hsqldb for the sample app, a new one will be created each time the
        //app starts, so insert the sample admin user at startup:

		personService.createPerson("jpiasci", "jesse@innobuilt.org", "Jesse Piascik");
		personService.createPerson("jgarcia", "jerry.garcia@dead.net", "Jerry Garcia");
		personService.createPerson("jcoltran", "john.coltrane@sax.com", "John Coltrane");
		personService.createPerson("lpaul", "les.paul@guitar.com", "Les Paul");
    }
}
