package contrib.wicket.cms.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import contrib.wicket.cms.model.Creatable;
import contrib.wicket.cms.model.Updatable;

public class AuditInterceptor extends EmptyInterceptor {

	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		// do nothing
	}

	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {

		if (entity instanceof Updatable) {
			for (int i = 0; i < propertyNames.length; i++) {
				if ("updatedDate".equals(propertyNames[i])) {
					currentState[i] = new Date();
					return true;
				}
			}
		}
		return false;
	}

	public boolean onLoad(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		return false;
	}

	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {

		boolean modified = false;

		if (entity instanceof Creatable || entity instanceof Updatable) {
			Date date = new Date();
			for (int i = 0; i < propertyNames.length; i++) {
				if (("createdDate".equals(propertyNames[i]) && entity instanceof Creatable)
						|| ("updatedDate".equals(propertyNames[i]) && entity instanceof Updatable)) {
					state[i] = date;
					modified = true;
				}
			}
		}

		return modified;
	}

}
