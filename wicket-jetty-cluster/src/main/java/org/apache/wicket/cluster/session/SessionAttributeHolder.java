package org.apache.wicket.cluster.session;

import java.io.Serializable;

import org.apache.wicket.util.lang.Objects;

/**
 * Holds serialized attribute
 * @author Matej Knopp
 */
public class SessionAttributeHolder implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private byte[] message;
	
	public SessionAttributeHolder(Object object) {
		message = Objects.objectToByteArray(object);
	}
	
	public Object toOriginalObject() {
		return Objects.byteArrayToObject(message);
	}
}