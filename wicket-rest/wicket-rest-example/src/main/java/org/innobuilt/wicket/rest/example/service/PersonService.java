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

import org.innobuilt.wicket.rest.example.model.Person;

/**
 * A service interface for accessing and modifying person data in the system.
 */
public interface PersonService {

    void createPerson(String personname, String email, String name);

    void createPerson(Person person);
    
    List<Person> getAllPersons();

    Person getPerson(Long personId);

    Person getPerson(String username);

    void deletePerson(Long personId);

    void updatePerson(Person person);

	public Person getPersonByEmail(String email);
}
