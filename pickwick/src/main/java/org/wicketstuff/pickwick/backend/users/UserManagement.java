package org.wicketstuff.pickwick.backend.users;

import java.util.List;

/**
 * Interface in charge of user management: add delete modify user
 * 
 * @author Vincent Demay
 *
 */
public interface UserManagement {
	/**
	 * Called when an user should be added to the system
	 * @param user user to add
	 */
	public void addUser(UserBean user);
	
	/**
	 * remove a user by its email
	 * @param email user email
	 */
	public void removeUser(String email);
	
	/**
	 * get a user by his email
	 * @param email user email
	 * @return user with the given email
	 */
	public UserBean getUser(String email);
	
	/**
	 * get the list of available users
	 * @return list of available users
	 */
	public List<UserBean> getAllUsers(); 
	
}
