/*
 * $Id$ $Revision$
 * $Date$
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.authorization.strategies.role;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import wicket.util.string.StringList;

/**
 * Utility class for working with roles.
 *
 * @author Eelco Hillenius
 * @author Jonathan Locke
 * @author Gili Tzabari
 */
public final class Roles
  extends HashSet<Class>
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  
  /**
   * Construct.
   */
  public Roles()
  {}
  
  /**
   * Construct.
   *
   * @param role
   *            Role
   */
  public Roles(Class role)
  {
    add(role);
  }
  
  /**
   * Construct.
   *
   * @param roles
   *            Roles
   */
  public Roles(final Class[] roles)
  {
    for (Class role: roles)
      add(role);
  }
  
  /**
   * Construct.
   *
   * @param roles
   *            Roles
   */
  public Roles(final Collection<Class> roles)
  {
    super(roles);
  }
  
  /**
   * Whether this role contains the provided role.
   *
   * @param role
   *            the role to check
   * @return true if it contains the role, false otherwise
   */
  public boolean contains(Object o)
  {
    // Ensure that all roles extend EVERYONE
    if (!(o instanceof Class))
      return false;
    Class c = (Class) o;
    if (!Role.EVERYONE.class.isAssignableFrom(c))
      return false;
    else
    {
      for (Class<?> role: this)
      {
        if (role.isAssignableFrom(c))
          return true;
      }
    }
    return false;
  }
  
  /**
   * Whether this role contains all of the provided roles.
   *
   * @param roles
   *            the roles to check
   * @return true if it contains all of the roles, false otherwise
   */
  public boolean containsAll(Collection<?> roles)
  {
    return super.containsAll(roles);
  }
  
  /**
   * Whether this role contains any of the provided roles.
   *
   * @param roles
   *            the roles to check
   * @return true if it contains any of the roles, false otherwise
   */
  public boolean containsAny(Collection<?> roles)
  {
    // Ensure that all roles extend EVERYONE
    for (Object o: roles)
    {
      if (!(o instanceof Class))
        continue;
      Class wantedRole = (Class) o;
      if (!Role.EVERYONE.class.isAssignableFrom(wantedRole))
        continue;
      else
      {
        for (Class<?> ownedRole: this)
        {
          if (ownedRole.isAssignableFrom(wantedRole))
            return true;
        }
      }
    }
    return false;
  }
  
  /**
   * Returns a set of all roles implied by the roles object by inheritance,
   * including the roles that were explicitly specified.
   */
  public Set<Class> getImplicitRoles()
  {
    Stack<Class> inputRoles = new Stack<Class>();
    inputRoles.addAll(this);
    Set<Class> outputRoles = new HashSet<Class>();
    while (!inputRoles.isEmpty())
    {
      Class role = inputRoles.pop();
      outputRoles.add(role);
      Class superClass = role.getSuperclass();
      if (superClass!=null && Role.EVERYONE.class.isAssignableFrom(superClass))
        inputRoles.add(superClass);
      Class[] interfaces = role.getInterfaces();
      for (Class Interface: interfaces)
      {
        if (Role.EVERYONE.class.isAssignableFrom(Interface))
          inputRoles.add(Interface);
      }
    }
    return outputRoles;
  }
  
  /**
   * @see java.lang.Object#toString()
   */
  @Override
    public String toString()
  {
    return StringList.valueOf(this).join();
  }
}
