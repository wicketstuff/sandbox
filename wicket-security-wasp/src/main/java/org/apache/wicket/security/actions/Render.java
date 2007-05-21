package org.apache.wicket.security.actions;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.model.IModel;

/**
 * The right to render / read (from) the specified {@link Component} / {@link IModel}. Components might use this to
 * turn visible on or off. Read implies {@link Access}
 * 
 * @author marrink
 * @see Action#RENDER
 * @see Component#RENDER
 */
public interface Render extends WaspAction
{

}
