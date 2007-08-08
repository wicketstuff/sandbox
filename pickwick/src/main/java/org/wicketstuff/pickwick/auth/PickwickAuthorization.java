package org.wicketstuff.pickwick.auth;

import java.io.File;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.Settings;
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
		String role = session.getUser().getRole();
		File f = new File(imagePath);
		if (f.exists()){
			while (!f.isDirectory()) {
				f = new File(f.getParent());
				Sequence s = ImageUtils.readSequence(f);
				if (s != null){
					if (!isAuthorized(role, s.getRole())){
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
	public static boolean isAuthorized(String userRole, String sequenceRole){
		return userRole == null || sequenceRole == null || sequenceRole.equals(userRole);
	}

}
