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


public class XmlUserManagement implements UserManagement{
	
	private static final String USER_URI = "users.xml";
	
	XmlBeanMapper<Users> mapper;
	Users users;
	
	public XmlUserManagement() {
		users = new Users();
		mapper = new XmlBeanMapper<Users>(Users.class);
		//try to read the file
		FileInputStream in = null;
		try {
			users = mapper.bindInBean(in = new FileInputStream(new File(USER_URI)));
		} catch (FileNotFoundException e) {
			//Nothing to do if file does not exists
		}finally{
			IOUtils.closeQuietly(in);
		}
	}

	public void addUser(UserBean user) {
		UserBean existing = users.get(user.getEmail());
		if (existing == null){
			existing = user;
		}else{
			existing.setName(user.getName());
			existing.setRole(user.getRole());
		}
		users.put(user.getEmail(), existing);
		saveUsers();
	}

	public List<UserBean> getAllUsers() {
		ArrayList<UserBean> toReturn = new ArrayList<UserBean>();
		Iterator<Entry<String, UserBean>> ite = users.entrySet().iterator();
		while(ite.hasNext()){
			toReturn.add(ite.next().getValue());
		}
		return toReturn;
	}

	public UserBean getUser(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeUser(String email) {
		// TODO Auto-generated method stub
		
	}
	
	private void saveUsers(){
		FileOutputStream out = null;
		try {
			mapper.serializeInXml(users, out = new FileOutputStream(new File(USER_URI)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(out);
		}
	}
	
	
}