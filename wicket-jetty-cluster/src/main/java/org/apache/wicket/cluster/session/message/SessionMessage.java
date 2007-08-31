package org.apache.wicket.cluster.session.message;

import java.io.Serializable;

import org.apache.wicket.cluster.SessionProvider;

public interface SessionMessage extends Serializable {

	public String getContextPath();
	
	public void execute(SessionProvider sessionProvider);

}
