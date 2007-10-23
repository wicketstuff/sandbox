package org.wicketstuff.push;

import java.io.Serializable;

import org.apache.wicket.Component;

public interface IPushInstaller extends Serializable {
	public void install(Component component, IPushTarget pushTarget);
}
