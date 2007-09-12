package org.wicketstuff.yui.markup.html.menu2.action;

import org.apache.wicket.model.IModel;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;

public abstract class AbstractYuiMenuAction implements IYuiMenuAction, java.io.Serializable {

	abstract public IModel getName();

	abstract public void onClick();

}
