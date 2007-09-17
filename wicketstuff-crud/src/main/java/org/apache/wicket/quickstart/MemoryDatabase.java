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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.util.string.Strings;

public class MemoryDatabase implements ContactDao
{
	private static int ID = 1000;

	private static final List<Contact> LIST = new ArrayList<Contact>(100);

	static
	{
		DataGenerator gen = new DataGenerator();
		gen.setCount(100);
		gen.setContactDao(new MemoryDatabase());
		try
		{
			gen.generate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public int count(Contact filter)
	{
		int count = 0;
		for (Contact c : LIST)
		{
			if (matches(filter, c))
			{
				count++;
			}
		}
		return count;

	}

	public void delete(long id)
	{
		Iterator<Contact> it = LIST.iterator();
		while (it.hasNext())
		{
			Contact c = it.next();
			if (c.getId() == id)
			{
				it.remove();
				break;
			}
		}
	}

	public Iterator<Contact> find(QueryParam qp, Contact filter)
	{
		List<Contact> filtered = new ArrayList<Contact>();
		for (Contact c : LIST)
		{
			if (matches(filter, c))
			{
				filtered.add(c);
			}
		}

		if (qp.getSort() != null)
		{
			if (qp.getSort().equalsIgnoreCase("firstName"))
			{
				Collections.sort(filtered, new Comparator<Contact>()
				{

					public int compare(Contact o1, Contact o2)
					{
						return comp(o1.getFirstname(), o2.getFirstname());
					}

				});
			}
			else if (qp.getSort().equalsIgnoreCase("lastName"))
			{
				Collections.sort(filtered, new Comparator<Contact>()
				{

					public int compare(Contact o1, Contact o2)
					{
						return comp(o1.getLastname(), o2.getLastname());
					}

				});
			}
			else if (qp.getSort().equalsIgnoreCase("phone"))
			{
				Collections.sort(filtered, new Comparator<Contact>()
				{

					public int compare(Contact o1, Contact o2)
					{
						return comp(o1.getPhone(), o2.getPhone());
					}

				});
			}
			else if (qp.getSort().equalsIgnoreCase("email"))
			{
				Collections.sort(filtered, new Comparator<Contact>()
				{

					public int compare(Contact o1, Contact o2)
					{
						return comp(o1.getEmail(), o2.getEmail());
					}

				});
			}
		}
		if (qp.getFirst() >= 0)
		{
			return filtered.subList(qp.getFirst(), qp.getFirst() + qp.getCount()).iterator();
		}
		else
		{
			return filtered.iterator();
		}

	}

	private static int comp(String o1, String o2)
	{
		if (o1 == o2)
		{
			return 0;
		}
		if (o1 == null)
		{
			return -1;
		}
		else if (o2 == null)
		{
			return 1;
		}
		else
		{
			return o1.compareTo(o2);
		}
	}

	private boolean matches(Contact criteria, Contact c)
	{
		if (!Strings.isEmpty(criteria.getFirstname()))
		{
			if (!c.getFirstname().toUpperCase().contains(criteria.getFirstname().toUpperCase()))
			{
				return false;
			}
		}
		if (!Strings.isEmpty(criteria.getLastname()))
		{
			if (!c.getLastname().toUpperCase().contains(criteria.getLastname().toUpperCase()))
			{
				return false;
			}
		}
		if (!Strings.isEmpty(criteria.getPhone()))
		{
			if (!c.getPhone().toUpperCase().contains(criteria.getPhone().toUpperCase()))
			{
				return false;
			}
		}
		if (!Strings.isEmpty(criteria.getEmail()))
		{
			if (!c.getEmail().toUpperCase().contains(criteria.getEmail().toUpperCase()))
			{
				return false;
			}
		}
		return true;

	}

	public List<String> getUniqueLastNames()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Contact load(long id)
	{
		for (Contact c : LIST)
		{
			if (c.getId() == id)
			{
				return c;
			}
		}
		return null;
	}

	public Contact save(Contact contact)
	{
		if (contact.getId() != 0)
		{
			delete(contact.getId());
		}
		else
		{
			contact.setId(ID++);
		}
		LIST.add(contact);
		return contact;

	}
}
