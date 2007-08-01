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
import org.wicketstuff.pickwick.bean.User;
import org.wicketstuff.pickwick.bean.Users;

import sun.security.action.GetLongAction;


public class XmlUserManagement implements UserManagement{
	
	private static final String USER_URI = "users.xml";
	
	private XmlBeanMapper<Users> mapper;
	private static Users users;
	
	private Users getUserList(){
		if (users == null){
			users = new Users();
			readUsers();
		}
		return users;
	}
	
	public XmlUserManagement() {
		mapper = new XmlBeanMapper<Users>(Users.class);
	}
	
	/**
	 * method reading the list of all users
	 * @param users2
	 */
	private void readUsers() {
		//try to read the file
		FileInputStream in = null;
		try {
			users = mapper.bindInBean(in = new FileInputStream(new File(USER_URI)));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}finally{
			IOUtils.closeQuietly(in);
		}
	}

	public void addUser(User user) {
		User existing = getUserList().get(user.getName());
		if (existing == null){
			existing = user;
		}else{
			existing.setName(user.getName());
			existing.setRole(user.getRole());
		}
		getUserList().put(user.getName(), existing);
		saveUsers();
	}

	public List<User> getAllUsers() {
		ArrayList<User> toReturn = new ArrayList<User>();
		Iterator<Entry<String, User>> ite = getUserList().entrySet().iterator();
		while(ite.hasNext()){
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
	private void saveUsers(){
		FileOutputStream out = null;
		try {
			mapper.serializeInXml(getUserList(), out = new FileOutputStream(new File(USER_URI)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(out);
		}
	}

	public boolean checkUser(String userName, String password) {
		return getUserList().get(userName) != null;
	}
	
	
}