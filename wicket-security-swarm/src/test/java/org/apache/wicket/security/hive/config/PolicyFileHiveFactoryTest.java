/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.hive.config;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.wicket.security.hive.Hive;
import org.apache.wicket.security.hive.authorization.EverybodyPrincipal;
import org.apache.wicket.security.hive.authorization.TestPermission;
import org.apache.wicket.security.hive.authorization.TestPrincipal;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;


import junit.framework.TestCase;

/**
 * @author marrink
 *
 */
public class PolicyFileHiveFactoryTest extends TestCase
{

	/**
	 * @param name
	 */
	public PolicyFileHiveFactoryTest(String name)
	{
		super(name);
	}

	/**
	 * Test method for {@link org.apache.wicket.security.hive.config.PolicyFileHiveFactory#addPolicyFile(java.net.URL)}.
	 */
	public void testAddPolicyFile()
	{
		PolicyFileHiveFactory factory= new PolicyFileHiveFactory();
		factory.addPolicyFile(getClass().getResource("test-policy.hive"));
		try
		{
			factory.addPolicyFile(new URL("http","localhost","foobar"));
		}
		catch (MalformedURLException e)
		{
			fail(e.getMessage());
		}
		factory.addPolicyFile(null);
	}

	/**
	 * Test method for {@link org.apache.wicket.security.hive.config.PolicyFileHiveFactory#createHive()}.
	 */
	public void testCreateHive()
	{
		PolicyFileHiveFactory factory= new PolicyFileHiveFactory();
		factory.addPolicyFile(getClass().getResource("test-policy.hive"));
		//based on policy content we can expect the following principals/permissions
		Hive hive=factory.createHive();
		assertTrue(hive.containsPrincipal(new EverybodyPrincipal()));
		assertTrue(hive.containsPrincipal(new TestPrincipal("test1")));
		assertTrue(hive.containsPrincipal(new TestPrincipal("test2")));
		assertTrue(hive.containsPrincipal(new TestPrincipal("test6")));
		assertTrue(hive.containsPermission(new TestPermission("A","inherit, read")));
		assertTrue(hive.containsPermission(new TestPermission("A","execute")));
		assertTrue(hive.containsPermission(new TestPermission("1.A","inherit, read")));
		assertTrue(hive.containsPermission(new TestPermission("1.A","execute")));
		assertFalse(hive.containsPermission(new TestPermission("2.A","inherit, read")));
		assertFalse(hive.containsPermission(new TestPermission("2.A","execute")));
		assertTrue(hive.containsPermission(new TestPermission("2.B","inherit, read, write")));
		assertTrue(hive.containsPermission(new TestPermission("2.B","execute")));
		assertTrue(hive.containsPermission(new TestPermission("2.C","read, execute")));
		assertTrue(hive.containsPermission(new TestPermission("2.C.1","write")));
		assertTrue(hive.containsPermission(new TestPermission("7.A","inherit, read")));
		assertTrue(hive.containsPermission(new TestPermission("7.A","execute")));
		assertTrue(hive.containsPermission(new TestPermission("7.B","inherit, read, write")));
		assertTrue(hive.containsPermission(new TestPermission("7.B","execute")));
		assertTrue(hive.containsPermission(new TestPermission("7.C","read, execute")));
		assertTrue(hive.containsPermission(new TestPermission("7.C.1","write")));
		assertFalse(hive.containsPermission(new TestPermission("6.A","inherit, read")));
		assertFalse(hive.containsPermission(new TestPermission("6.A","execute")));
		assertFalse(hive.containsPermission(new TestPermission("6.B","inherit, read, write")));
		assertFalse(hive.containsPermission(new TestPermission("6.B","execute")));
		assertFalse(hive.containsPermission(new TestPermission("6.C","read, execute")));
		assertFalse(hive.containsPermission(new TestPermission("6.C.1","write")));
	}

}
