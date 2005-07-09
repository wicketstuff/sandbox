/*
 * Created on 22.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package wicket.addons.hibernate;

import java.util.List;

/**
 * @author Juergen Donnerstag
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IUserDao
{
	public abstract List loadUserByNickname(final String nickname);

	public abstract List getUsers();

	public abstract Integer getNumberOfRegisteredUsers();

	public abstract User login(final String nickname, final String email, final String password);
}