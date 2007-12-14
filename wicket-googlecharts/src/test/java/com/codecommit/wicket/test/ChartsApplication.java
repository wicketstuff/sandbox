/*
 * Created on Dec 11, 2007
 */
package com.codecommit.wicket.test;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author Daniel Spiewak
 */
public class ChartsApplication extends WebApplication {
	@Override
	public Class<? extends WebPage> getHomePage() {
		return Home.class;
	}
}
