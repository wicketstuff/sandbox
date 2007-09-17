/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.quickstart;

import java.io.Serializable;
import java.util.Date;

/**
 * Contact details. This is the business object that we persist to the DB.
 * 
 * @author igor
 */
public class Contact implements Serializable
{
	private long id;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;

	private int age;
	private Date dob;


	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public Date getDob()
	{
		return dob;
	}

	public void setDob(Date dob)
	{
		this.dob = dob;
	}

	public long getId()
	{
		return id;
	}

	/**
	 * This is required for iBatis, but not for Hibernate
	 * 
	 * @param id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getFullName()
	{
		return firstname + " " + lastname;
	}

	@Override
	public String toString()
	{
		return ("[Contact id=" + id + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", phone=" + phone + ", email=" + email);
	}

}
