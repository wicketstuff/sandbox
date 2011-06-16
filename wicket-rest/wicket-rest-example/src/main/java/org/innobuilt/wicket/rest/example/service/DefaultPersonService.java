/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.innobuilt.wicket.rest.example.service;

import java.util.List;

import org.innobuilt.wicket.rest.example.dao.PersonDAO;
import org.innobuilt.wicket.rest.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of the {@link PersonService} interface.  This service implements
 * operations related to Person data.
 */
@Transactional
public class DefaultPersonService implements PersonService {

    PersonDAO personDAO;

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public void createPerson(String username, String email, String name) {
        Person person = new Person();
        person.setUsername(username);
        person.setEmail(email);
        person.setName(name);
        personDAO.createPerson( person );
    }
    
    public void createPerson(Person person) {
    	personDAO.createPerson(person);
    }

    public List<Person> getAllPersons() {
        return personDAO.getAllPersons();
    }

    public Person getPerson(Long personId) {
        return personDAO.getPerson(personId);
    }

    public void deletePerson(Long personId) {
        personDAO.deletePerson( personId );
    }

    public void updatePerson(Person person) {
        personDAO.updatePerson( person );
    }

	public Person getPerson(String username) {
		return personDAO.findPerson(username);
	}

	public Person getPersonByEmail(String email) {
		return personDAO.findPersonByEmail(email);
	}

}
