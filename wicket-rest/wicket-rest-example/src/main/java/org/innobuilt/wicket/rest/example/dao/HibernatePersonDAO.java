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
package org.innobuilt.wicket.rest.example.dao;

import java.util.List;

import org.innobuilt.wicket.rest.example.model.Person;
import org.springframework.util.Assert;

public class HibernatePersonDAO extends HibernateDao implements PersonDAO {

    public Person getPerson(Long personId) {
        return (Person) getSession().get(Person.class, personId);
    }

    public Person findPerson(String username) {
        Assert.hasText(username);
        String query = "from Person p where p.username = :username";
        return (Person) getSession().createQuery(query).setString("username", username).uniqueResult();
    }

    public void createPerson(Person person) {
        getSession().save( person );
    }

    public List<Person> getAllPersons() {
        return getSession().createQuery("from Person order by username").list();
    }

    public void deletePerson(Long personId) {
        Person person = getPerson(personId);
        if( person != null ) {
            getSession().delete(person);
        }
    }

    public void updatePerson(Person person) {
        getSession().update(person);
    }

	public Person findPersonByEmail(String email) {
        Assert.hasText(email);
        String query = "from Person p where p.email = :email";
        return (Person) getSession().createQuery(query).setString("email", email).uniqueResult();
    }

}

