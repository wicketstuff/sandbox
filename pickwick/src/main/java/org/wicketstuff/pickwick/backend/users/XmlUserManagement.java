package org.wicketstuff.pickwick.backend.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.wicketstuff.pickwick.backend.XmlBeanMapper;
import org.wicketstuff.pickwick.bean.Role;
import org.wicketstuff.pickwick.bean.Roles;
import org.wicketstuff.pickwick.bean.User;
import org.wicketstuff.pickwick.bean.Users;

public class XmlUserManagement implements UserManagement {
	private static final String USER_URI = "users.xml";
	private static final String ROLE_URI = "roles.xml";

	private XmlBeanMapper<Users> userMapper;
	private XmlBeanMapper<Roles> roleMapper;

	private static Users users;
	private static Roles roles;

	private Users getUserList() {
		if (users == null) {
			users = new Users();
			readUsers();
		}
		return users;
	}
	
	public Roles getAllRoles() {
		if (roles == null){
			roles = new Roles();
			readRoles();
		}
		return roles;
	}

	public XmlUserManagement() {
		userMapper = new XmlBeanMapper<Users>(Users.class);
		roleMapper = new XmlBeanMapper<Roles>(Roles.class);
	}

	/**
	 * method reading the list of all users
	 * 
	 * @param users2
	 */
	private void readUsers() {
		// try to read the file
		FileInputStream in = null;
		try {
			users = userMapper.bindInBean(in = new FileInputStream(new File(USER_URI)));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
	
	/**
	 * method reading the list of all users
	 * 
	 * @param users2
	 */
	private void readRoles() {
		// try to read the file
		FileInputStream in = null;
		try {
			roles = roleMapper.bindInBean(in = new FileInputStream(new File(ROLE_URI)));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public void addUser(User user) {
		User existing = getUserList().get(user.getName());
		if (existing == null) {
			existing = new User();
		}
		
		existing.setName(user.getName());
		existing.setRoles(user.getRoles());
		
		getUserList().put(user.getName(), existing);
		saveUsers();
	}

	public List<User> getAllUsers() {
		ArrayList<User> toReturn = new ArrayList<User>();
		Iterator<Entry<String, User>> ite = getUserList().entrySet().iterator();
		while (ite.hasNext()) {
			toReturn.add(ite.next().getValue());
		}
		return toReturn;
	}

	public User getUser(String name) {
		return getUserList().get(name);
	}

	public void removeUser(String name) {
		getUserList().remove(name);
	}

	/**
	 * Save user on the file
	 * 
	 */
	private void saveUsers() {
		FileOutputStream out = null;
		try {
			userMapper.serializeInXml(getUserList(), out = new FileOutputStream(new File(USER_URI)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	
	/**
	 * Save role in the file
	 * 
	 */
	private void saveRoles() {
		FileOutputStream out = null;
		try {
			roleMapper.serializeInXml(getAllRoles(), out = new FileOutputStream(new File(ROLE_URI)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	

	public boolean checkUser(String userName, String password) {
		User user = getUserList().get(userName);
		if (user != null){
			if (user.getPassword() == null){
				return false;
			}
			if (user.getPassword().equals(password)){
				return true;
			}
		}
		return false;
	}

	public void addRole(Role role) {
		roles.add(role);
		saveRoles();
	}


}