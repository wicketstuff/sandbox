package org.wicketstuff.jmx.util;

import org.apache.wicket.Application;
import org.apache.wicket.IClusterable;

/**
 * This interface is used to prevent certain domains from being rendered.
 * 
 * @author Gerolf Seitz
 * 
 */
public interface IDomainFilter extends IClusterable
{

	/**
	 * IDomainFilter implementation that accepts all domains.
	 */
	public static final IDomainFilter ALL = new IDomainFilter()
	{
		private static final long serialVersionUID = 1L;

		public boolean accept(String domain)
		{
			return true;
		}
	};

	/**
	 * IDomainFilter implementation that only accepts domains that contain the
	 * name of the application.
	 */
	public static final IDomainFilter CONTAINING_APPLICATION = new IDomainFilter()
	{
		private static final long serialVersionUID = 1L;

		public boolean accept(String domain)
		{
			return domain.contains(Application.get().getName());
		}

	};

	/**
	 * Used to determine whether a domain should be included in the tree or not.
	 * 
	 * @param domain
	 *            the domain to process
	 * @return <code>true</code> if the domain is to be shown, false otherwise
	 */
	boolean accept(String domain);
}
