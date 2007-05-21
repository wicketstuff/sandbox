/*
 * $Id: JaasPageAuthorizationStrategy.java,v 1.1 2006/06/28 10:00:16 Marrink Exp $ $Revision: 1.1 $ $Date: 2005/12/02
 * 08:38:47 $ ==================================================================== Copyright (c) 2005, Topicus B.V. All
 * rights reserved.
 */

package org.apache.wicket.security.strategies;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.security.actions.Access;
import org.apache.wicket.security.checks.ClassSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.components.ISecurePage;


/**
 * Authorization strategy to enforce security at the construction of components rather
 * then at render time. Note that it always requires a valid login, failing this condition
 * will cause a redirect to the login page as defined in the application rather then
 * returning false which will cause wicket to go to the accessdenied page. This class
 * operates by checking if the supplied class or any of its superclasses have static
 * fields of type ISecurityCheck. It then calls each of them with an Access action, if any
 * one of them fails the class is not allowed to instantiate. Note that this
 * implementation only allows you to go as deep as ISecureComponent because there is no
 * point in checking every Component if the strategy does not provide someway of allowing
 * all components on the loginpage to be constructed. In fact even IsecureComponent is
 * questionable because you would most likely have to grant access rights to the class of
 * the secure component thereby making it completely irrelevant on what page the component
 * is located. Therefore the most sensible and default is to check instances of
 * ISecurePage (or subclasses).
 * @author marrink
 */
public abstract class ClassAuthorizationStrategy extends WaspAuthorizationStrategy
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(ClassAuthorizationStrategy.class);

	/**
	 * Default is to only check ISecureComponents
	 */
	private Class secureClass = ISecurePage.class;

	public ClassAuthorizationStrategy()
	{
		super();
	}

	/**
	 * Creates a strategy that checks all implementations of the supplied class.
	 * @param secureClass an {@link ISecureComponent} (sub)class.
	 */
	public ClassAuthorizationStrategy(Class secureClass)
	{
		super();
		if (secureClass != null)
		{
			if (ISecureComponent.class.isAssignableFrom(secureClass))
				this.secureClass = secureClass;
			else
				throw new IllegalArgumentException(
						"securePageClass must be an ISecureComponent class.");
		}
	}

	/**
	 * Checks if a class is allowed to be constructed. Only classes asignable to the
	 * specified Class are checked. Note that all the found {@link ISecurityCheck}s must
	 * return true for the authorization to succeed. If the class does not have any static
	 * {@link ISecurityCheck}s a {@link ClassSecurityCheck} is used to simulate a static
	 * securitycheck This way you only need to assign static checks if you want something
	 * special.
	 * @see wicket.authorization.IAuthorizationStrategy#isInstantiationAuthorized(java.lang.Class)
	 */
	public boolean isInstantiationAuthorized(Class c)
	{
		if (c != null && secureClass.isAssignableFrom(c))
		{
			ISecurityCheck[] checks = getClassChecks(c);
			for (int i = 0; i < checks.length; i++)
			{
				if (!checks[i].isActionAuthorized(getActionFactory().getAction(Access.class)))
					return false;
			}
			if (checks.length == 0)
				return new ClassSecurityCheck(c).isActionAuthorized(getActionFactory().getAction(
						Access.class));
			return true;
		}
		return true;
	}

	/**
	 * Returns the static {@link ISecurityCheck}s of a class.
	 * @param clazz
	 * @return an array containing all the {@link ISecurityCheck} of this class and all
	 *         its superclasses, or an array of length 0 if none is found.
	 */
	protected final ISecurityCheck[] getClassChecks(Class clazz)
	{
		List list = getClassChecks(clazz, new ArrayList());
		if (list == null)
			return new ISecurityCheck[0];
		return (ISecurityCheck[]) list.toArray(new ISecurityCheck[list.size()]);
	}

	/**
	 * Appends all the {@link ISecurityCheck}s of a class and its superclasses to the
	 * list.
	 * @param clazz
	 * @param list
	 * @return the list
	 */
	protected List getClassChecks(Class clazz, List list)
	{
		while (clazz != null)
		{
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
			{
				if (Modifier.isStatic(fields[i].getModifiers())
						&& ISecurityCheck.class.isAssignableFrom(fields[i].getType()))
				{
					try
					{
						fields[i].setAccessible(true);
						Object check = fields[i].get(null);
						if (check != null)
							list.add(check);
					}
					catch (SecurityException e)
					{
						log.error(getExceptionMessage(fields[i]), e);
					}
					catch (IllegalArgumentException e)
					{
						log.error(getExceptionMessage(fields[i]), e);
					}
					catch (IllegalAccessException e)
					{
						log.error(getExceptionMessage(fields[i]), e);
					}
				}
			}
			Class[] interfaces = clazz.getInterfaces();
			for (int i = 0; i < interfaces.length; i++)
				getClassChecks(interfaces[i], list);
			clazz = clazz.getSuperclass();
		}
		return list;
	}

	/**
	 * Produces a generic exception message including information about the field that
	 * caused the exception.
	 * @param field
	 * @return generic exception message
	 */
	protected String getExceptionMessage(Field field)
	{
		if (field == null)
			return "unable to process unknown field";
		return "Unable to process " + field.getDeclaringClass().getName() + "#" + field.getName();
	}
}
