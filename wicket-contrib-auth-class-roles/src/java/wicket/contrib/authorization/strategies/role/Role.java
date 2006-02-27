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

/**
 * Represents a user role. Developers should implement Role.EVERYONE.
 *
 * @author Gili Tzabari
 */
public interface Role extends Serializable
{
  /**
   * Role granted to all users.
   */
  public static interface EVERYONE extends Role
  {};
  /**
   * Role for denying access to all. No user should ever be granted this role,
   * including the administrator.
   */
  public static final class NO_ONE implements Role
  {};
}