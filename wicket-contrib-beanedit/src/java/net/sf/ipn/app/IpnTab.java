/*
 * Created on Apr 20, 2005
 */
package net.sf.ipn.app;

import java.io.Serializable;

import wicket.Page;

/**
 * @author Jonathan Carlson Contains information about a tab in the main border. Lazily
 *         creates a page when the tab is clicked.
 */
public class IpnTab implements Serializable
{
	private IpnSession session;
	private Page page;
	private Class pageClass;
	private String label;

	/**
	 * 
	 */
	public IpnTab(IpnSession session, Class pageClass, String label)
	{
		super();
		this.session = session;
		this.pageClass = pageClass;
		this.label = label;
	}

	public String getLabel()
	{
		return this.label;
	}

	public Page getPage()
	{
		if (this.page == null)
			this.page = this.session.getPageFactory().newPage(this.pageClass);
		return this.page;
	}

	public Class getPageClass()
	{
		return this.pageClass;
	}

}
