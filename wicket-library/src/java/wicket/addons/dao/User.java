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

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Juergen Donnerstag
 */
public class User implements Serializable, ILastModified, IDeleted, IIdentifiable
{
    private int id;

    private String nickname;

    private String firstname;

    private String lastname;

    private String email;

    private String locale;

    private String password;

    private Timestamp lastModified;

    private Timestamp deleted;

    private Date lastLogin;

    private Date deactivated;

    public User()
    {
        ; // empty
    }

    /**
     * @return Returns the email.
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email
     *            The email to set.
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return Returns the firstname.
     */
    public String getFirstname()
    {
        return firstname;
    }
    
    public String getNameLastNameFirst()
    {
        return lastname + ", " + firstname;
    }
    
    public String getNameFirstNameFirst()
    {
        return firstname + lastname;
    }

    /**
     * @param firstname
     *            The firstname to set.
     */
    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    /**
     * @return Returns the id.
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return Returns the lastname.
     */
    public String getLastname()
    {
        return lastname;
    }

    /**
     * @param lastname
     *            The lastname to set.
     */
    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    /**
     * @return Returns the locale.
     */
    public String getLocale()
    {
        return locale;
    }

    /**
     * @param locale
     *            The locale to set.
     */
    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    /**
     * @return Returns the nickname.
     */
    public String getNickname()
    {
        return nickname;
    }

    /**
     * @param nickname
     *            The nickname to set.
     */
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password
     *            The password to set.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return Returns the lastModified.
     */
    public Timestamp getLastModified()
    {
        return lastModified;
    }

    /**
     * @param lastModified
     *            The lastModified to set.
     */
    public void setLastModified(Timestamp lastModified)
    {
        this.lastModified = lastModified;
    }

    /**
     * @return Returns the lastLogin.
     */
    public Date getLastLogin()
    {
        return lastLogin;
    }

    /**
     * @param lastLogin
     *            The lastLogin to set.
     */
    public void setLastLogin(Date lastLogin)
    {
        this.lastLogin = lastLogin;
    }

    /**
     * @return Returns the deactivated.
     */
    public Date getDeactivated()
    {
        return deactivated;
    }

    /**
     * @param deactivated
     *            The deactivated to set.
     */
    public void setDeactivated(Date deactivated)
    {
        this.deactivated = deactivated;
    }

    /*
     * (non-Javadoc)
     * 
     * @see wicket.addons.dao.Deleted#getDeleted()
     */
    public Timestamp getDeleted()
    {
        return deleted;
    }

    /*
     * (non-Javadoc)
     * 
     * @see wicket.addons.dao.Deleted#setDeleted(java.sql.Timestamp)
     */
    public void setDeleted(Timestamp deleted)
    {
        this.deleted = deleted;
    }
}
