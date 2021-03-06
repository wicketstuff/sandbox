package org.wicketstuff.pickwick.backend.users;

import java.util.List;

import org.wicketstuff.pickwick.bean.Role;
import org.wicketstuff.pickwick.bean.Roles;
import org.wicketstuff.pickwick.bean.User;

import com.google.inject.ImplementedBy;

/**
 * Interface in charge of user management: add delete modify user
 * 
 * @author Vincent Demay
 */
@ImplementedBy(XmlUserManagement.class)
public interface UserManagement {
	/**
	 * Called when an user should be added to the system
	 * @param user user to add
	 */
	public void addUser(User user);
	
	/**
	 * remove a user by its mane
	 * @param email user name
	 */
	public void removeUser(String name);
	
	/**
	 * get a user by his name
	 * @param email user name
	 * @return user with the given name
	 */
	public User getUser(String name);
	
	/**
	 * get the list of available users
	 * @return list of available users
	 */
	public List<User> getAllUsers();

	/**
	 * Check if the user can be logged in
	 * @param userName user name
	 * @param password user password
	 * @return true if the user is authenticated, otherwise return false
	 */
	public boolean checkUser(String userName, String password); 
	
	/**
	 * Return the role list
	 * @return the role list
	 */
	public Roles getAllRoles();
	
	/**
	 * Add a new Role
	 *
	 */
	public void addRole(Role role);
	
}
