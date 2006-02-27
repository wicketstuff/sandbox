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
package wicket.contrib.authorization.strategies.role.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import wicket.contrib.authorization.strategies.role.Role;
import wicket.util.string.StringList;

/**
 * Simple user object.
 *
 * @author Eelco Hillenius
 * @author Gili Tzabari
 */
public class User
  implements Serializable
{
  private final String uid;
  /**
   * The user role.
   */
  private Class role;
  
  /**
   * Construct.
   *
   * @param uid
   *            the unique user id
   * @param role
   *            the user role
   */
  public User(String uid, Class role)
  {
    if (uid == null)
      throw new IllegalArgumentException("uid must be not null");
    this.uid = uid;
    this.role = role;
  }
  
  public Class getRole()
  {
    return role;
  }
  
  /**
   * Whether this user has the given role.
   *
   * @param role
   * @return whether this user has the given role
   */
  public boolean hasRole(Class role)
  {
    return getClass().isAssignableFrom(role);
  }
  
  /**
   * Gets the uid.
   *
   * @return the uid
   */
  public String getUid()
  {
    return uid;
  }
  
  /**
   * @see java.lang.Object#toString()
   */
  @Override
    public String toString()
  {
    List<String> roleNames = new ArrayList<String>();
    Stack<Class> inputRoles = new Stack<Class>();
    inputRoles.add(getRole());
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
    for (Class role: outputRoles)
    {
      String name = role.getName();
      // Role could be a nested or a normal class
      int lastDollar = name.lastIndexOf('$');
      int lastPeriod = name.lastIndexOf('.');
      int lastIndex = Math.max(lastDollar, lastPeriod);
      if (lastIndex==-1)
        throw new IllegalStateException();
      roleNames.add(name.substring(lastIndex+1));
    }
    String rolesString = StringList.valueOf(roleNames).join();
    return uid + " " + rolesString;
  }
}