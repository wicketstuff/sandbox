/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.addons.dao;

import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;


/**
 * @author Juergen Donnerstag
 */
public final class UserDaoImpl extends HibernateDaoSupport
{
    public final List loadUserByNickname(final String nickname) 
    {
        return getHibernateTemplate().find(
            "from User user where user.nickname=?", nickname,
            Hibernate.STRING);
    }

    public final List getUsers() 
    {
        return getHibernateTemplate().find(
            "from User u where (u.deleted is null) and (u.deactivated is null)");
    }
    
    public final Integer getNumberOfRegisteredUsers()
    {
        return (Integer) getHibernateTemplate().find(
                "select count(*) from User u where (u.deactivated is null) AND (u.lastModified is not null)").get(0);
    }
    
    public final User login(final String nickname, final String email, final String password)
    {
        final List users;
        
        if ((nickname != null) && (nickname.trim().length() > 0) 
                && (email != null) && (email.trim().length() > 0))
        {
            users = getHibernateTemplate().find(
                	"from User u where (u.nickname=?) AND ((u.email=?)) AND (u.deactivated is null) AND (u.lastModified is not null)",
                	new Object[] { nickname, email }, 
                	new Type[] { Hibernate.STRING, Hibernate.STRING } );
        }
        else if ((nickname != null) && (nickname.trim().length() > 0))
        {
            users = getHibernateTemplate().find(
                	"from User u where (u.nickname=?) AND (u.deactivated is null) AND (u.lastModified is not null)",
                	nickname, Hibernate.STRING);
        }
        else if ((email != null) && (email.trim().length() > 0))
        {
            users = getHibernateTemplate().find(
                	"from User u where (u.email=?) AND (u.deactivated is null) AND (u.lastModified is not null)",
                	email, Hibernate.STRING);
        }        
        else
        {
            return null;
        }

        if ((users == null) || (users.size() == 0))
        {
            return null;
        }
        else if (users.size() > 1)
        {
            throw new RuntimeException("Found login name more than once in the database: nickname=" + nickname);
        }
        
        final User user = (User)users.get(0);
        if (user != null)
        {
            if ((password == null) && (user.getPassword() == null))
            {
                // prevent accidental ...
                user.setPassword(null);
                return user;
            }

            if ((password != null) && (user.getPassword() != null) 
                    && password.equals(user.getPassword()))
            {
                // prevent accidental ...
                user.setPassword(null);
                return user;
            }
        }
            
        return null;
    }
}
