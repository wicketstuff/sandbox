/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Copyright (c) 2005, Topicus B.V.
 * All rights reserved.
 */
package org.apache.wicket.security.hive.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.hive.authorization.Principal;
import org.apache.wicket.security.strategies.LoginException;


/**
 * Container class for multiple {@link LoginContext}s. Note this class is not thread
 * safe.
 * @author marrink
 */
public final class LoginContainer
{
	private List logins = new ArrayList();

	private Map subjects = new HashMap();

	private Subject subject = null;

	public void login(LoginContext context) throws LoginException
	{
		if (context == null)
			throw new LoginException("Context is required to login.");
		if (subjects.containsKey(context))
			throw new LoginException("Already logged in through this context ").setLoginContext(context);
		Subject subject = context.login();
		if (subject == null)
			throw new LoginException("Login failed ").setLoginContext(context);
		subjects.put(context, subject);
		logins.add(context);
		Collections.sort(logins);
		this.subject = new MultiSubject(subjects.values());
	}

	public boolean logoff(LoginContext context)
	{
		if (subjects.remove(context) != null)
		{
			logins.remove(context);
			if(logins.isEmpty())
				subject=null;
			else
				subject = new MultiSubject(subjects.values());
			return true;
		}
		return false;
	}

	/**
	 * Forces the container to re-evaluated the principals in subjects. Note that you
	 * would only need this if somewhere else the principals in the subjects returned in
	 * the logincontext where changed. You do not need to trigger a reload after a login
	 * or logoff since that is automaticly done.
	 * @return the subject
	 */
	public Subject reloadSubject()
	{
		return subject = new MultiSubject(subjects.values());
	}

	public boolean isModelAuthenticated(IModel model, Component component)
	{
		LoginContext ctx = null;
		for (int i = 0; i < logins.size(); i++)
		{
			ctx = (LoginContext) logins.get(i);
			if (ctx.isModelAuthenticated(model, component))
				return true;
		}
		return false;
	}

	public boolean isComponentAuthenticated(Component component)
	{
		LoginContext ctx = null;
		for (int i = 0; i < logins.size(); i++)
		{
			ctx = (LoginContext) logins.get(i);
			if (ctx.isComponentAuthenticated(component))
				return true;
		}
		return false;
	}

	public boolean isClassAuthenticated(Class clazz)
	{
		LoginContext ctx = null;
		for (int i = 0; i < logins.size(); i++)
		{
			ctx = (LoginContext) logins.get(i);
			if (ctx.isClassAuthenticated(clazz))
				return true;
		}
		return false;
	}

	/**
	 * returns an inmutable Subject which Contains all the principals of the subjects in
	 * the loginContexts. Note that the Subject is replaced by a new one if a login or
	 * logoff is performed, so do'nt keep a reference to this subject any longer then
	 * required.
	 * @return a subject
	 */
	public Subject getSubject()
	{
		return subject;
	}

	/**
	 * Returns the number of {@link LoginContext}s contained here.
	 * @return the size
	 */
	public int size()
	{
		return logins.size();
	}

	/**
	 * Readonly subject merging all the subjects from the logincontainer into one. Note
	 * this subject is not backed by the logincontainer.
	 * @author marrink
	 */
	private static class MultiSubject implements Subject
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private Set principals;

		private Set readOnlyPrincipals;

		/**
		 * @param subjects
		 */
		public MultiSubject(Collection subjects)
		{
			super();
			principals = new HashSet(100);
			// we dont keep the original collection because we might get
			// concurrentmodificationexceptions, besides this way we dont have to rebuild
			// the set every time because of possible logins/logoffs
			Iterator it = subjects.iterator();
			while (it.hasNext())
			{
				principals.addAll(((Subject) it.next()).getPrincipals());
			}
			readOnlyPrincipals = Collections.unmodifiableSet(principals);
		}

		public boolean addPrincipal(Principal principal)
		{
			return false;
		}

		public Set getPrincipals()
		{
			return readOnlyPrincipals;
		}

		public boolean isReadOnly()
		{
			return true;
		}

		public void setReadOnly()
		{
			// noop
		}

	}
}
