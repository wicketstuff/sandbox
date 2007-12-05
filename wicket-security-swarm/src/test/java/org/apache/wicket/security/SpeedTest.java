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
package org.apache.wicket.security;

import junit.framework.TestCase;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.security.hive.BasicHive;
import org.apache.wicket.security.hive.Hive;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.authorization.Principal;
import org.apache.wicket.security.hive.authorization.SimplePrincipal;
import org.apache.wicket.security.hive.authorization.permissions.ComponentPermission;
import org.apache.wicket.security.hive.config.HiveFactory;
import org.apache.wicket.security.pages.MockLoginPage;
import org.apache.wicket.security.pages.SpeedPage;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author marrink
 */
public class SpeedTest extends TestCase
{
	private static final Logger log = LoggerFactory.getLogger(SpeedTest.class);

	/**
	 * Nr. of rows and columns in our repeater.
	 */
	public static final int ROWS = 50;
	public static final int COLS = 50;

	/**
	 * The swarm application used for the test.
	 */
	protected WebApplication application;
	/**
	 * Handle to the mock environment.
	 */
	protected WicketTester mock;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		mock = new WicketTester(application = new SwarmWebApplication()
		{

			protected Object getHiveKey()
			{
				// if we were using servlet-api 2.5 we could get the contextpath
				// from the servletcontext
				return "test";
			}

			protected void setUpHive()
			{
				HiveFactory factory = new DummyFactory();
				HiveMind.registerHive(getHiveKey(), factory);
			}

			public Class getHomePage()
			{
				return SpeedPage.class;
			}

			public Class getLoginPage()
			{
				return MockLoginPage.class;
			}
		}, "src/test/java/" + getClass().getPackage().getName().replace('.', '/'));
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		mock.setupRequestAndResponse();
		mock.getWicketSession().invalidate();
		mock.processRequestCycle();
		mock.destroy();
		mock = null;
		application = null;
		HiveMind.unregisterHive("test");
	}

	/**
	 * Test performance diff between secure page and "unsecure" page with lots
	 * of components.
	 */
	public void testPerformance()
	{
		mock.startPage(SpeedPage.class);
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", "speed");
		form.submit();
		mock.assertRenderedPage(SpeedPage.class);
		mock.assertVisible("secure");
		// weird no html????
		// TagTester tag = mock.getTagById("secure");
		// assertNotNull(tag);
		// assertEquals("not secure", tag.getValue());

		int warmup = 100;
		// warmup
		for (int i = 0; i < warmup; i++)
		{
			mock.startPage(mock.getLastRenderedPage());
			mock.assertRenderedPage(SpeedPage.class);
		}

		int count = 100;
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			mock.startPage(mock.getLastRenderedPage());
		long end = System.currentTimeMillis();
		log.info("enabling security now");
		// enable security checks
		((SpeedPage)mock.getLastRenderedPage()).setSecured(true);
		// warmup
		for (int i = 0; i < warmup; i++)
		{
			mock.startPage(mock.getLastRenderedPage());
			mock.assertRenderedPage(SpeedPage.class);
		}

		long start2 = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			mock.startPage(mock.getLastRenderedPage());
		long end2 = System.currentTimeMillis();

		log.info("Testing " + (ROWS * COLS) + " components");
		log.info("unsecured page took " + (end - start) + " ms total, " + ((end - start) / count)
				+ " ms on average over " + count + " requests");
		log.info("secured page took " + (end2 - start2) + " ms total, " + ((end2 - start2) / count)
				+ " ms on average over " + count + " requests");
		long diff = (((end2 - start2) / count) - ((end - start) / count));
		log.info("difference per request = " + diff
				+ " ms, meaning each component security check took "
				+ ((double)diff / (ROWS * COLS)) + " ms");

		// 2500 components, diff per request = 27 ms, time per component =
		// 0.0108 ms
		// no caching, no permission / principal inheritance, all components
		// allowed

		// 2500 components, diff per request = 917 ms, time per component =
		// 0.3668 ms
		// no caching, no permission / principal inheritance, 1250 components
		// allowed, 1250 components denied

		// caching should dramatically improve situations where no permission is
		// found

	}

	private static final class DummyFactory implements HiveFactory
	{


		/**
		 * Construct.
		 */
		public DummyFactory()
		{
			super();
		}

		/**
		 * 
		 * @see org.apache.wicket.security.hive.config.HiveFactory#createHive()
		 */
		public Hive createHive()
		{
			BasicHive hive = new BasicHive();
			Principal principal = new SimplePrincipal("speed");
			hive.addPermission(principal, new ComponentPermission(
					"org.apache.wicket.security.pages.SpeedPage", "access, render"));
			for (int i = 0; i < ROWS; i++)
			{
				for (int j = 0; j < COLS; j++)
				{
					// not granting a permission for each component will add
					// linear time to check, the more permissions the more time
					// will be added
					hive.addPermission(principal, new ComponentPermission(
							"org.apache.wicket.security.pages.SpeedPage:rows:" + i + ":cols:" + j
									+ ":label", "access, render"));
				}
			}
			return hive;
		}

	}
}
