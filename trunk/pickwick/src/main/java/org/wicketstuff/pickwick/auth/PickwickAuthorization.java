package org.wicketstuff.pickwick.auth;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.Role;
import org.wicketstuff.pickwick.bean.Sequence;

import com.google.inject.Inject;

/**
 * Utilities class to deal with authorization
 * @author Vincent Demay
 * 
 * TODO : see if we can put it in ImagesUtils???
 *
 */
public class PickwickAuthorization {

	/**
	 * Check if the user in the session can access to this uri
	 * Redirect in {@link PickwickLoginPage} if failed
	 * @param uri uri to check
	 * @param session current {@link PickwickSession}
	 */
	public static void check(String imagePath, PickwickSession session){
		List<Role> roles = session.getUser().getRoles();
		File f = new File(imagePath);
		if (f.exists()){
			while (!f.isDirectory()) {
				f = new File(f.getParent());
				Sequence s = ImageUtils.readSequence(f);
				if (s != null){
					if (!isAuthorized(roles, s.getRoles())){
						throw new RestartResponseAtInterceptPageException(PickwickLoginPage.class);
					}else{
						return;
					}
				}else{
					return;
				}
			}
		}
		
	}
	
	/**
	 * Is the user role match to the sequence role
	 * @param userRole user role	
	 * @param sequenceRole sequence role
	 * @return true if roles match
	 */
	public static boolean isAuthorized(List<Role> userRoles, List<Role> sequenceRoles){
		if(sequenceRoles == null || sequenceRoles.isEmpty()){
			return true;
		}
		
		if (userRoles == null || userRoles.isEmpty()){
			if(sequenceRoles == null  || sequenceRoles.isEmpty()){
				return true;
			}else{
				return false;
			}
		}else{
			return sequenceRoles.isEmpty() || intersect(userRoles, sequenceRoles);
		}
	}
	
	private static boolean intersect(List<Role> list1, List<Role> list2){
		for (Role role1 : list1){
			for(Role role2 : list2){
				if(role1.getLabel().equals(role2.getLabel())){
					return true;
				}
			}
		}
		return false;
	}

}
