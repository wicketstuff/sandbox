package org.wicketstuff.objectautocomplete;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.Component;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * @author roland
 * @since May 28, 2008
 */
public class ObjectUpdateBehavior<T> extends AjaxEventBehavior {

    public ObjectUpdateBehavior(String event, Component<T> objectField) {
        super(event);
    }

    protected void onEvent(AjaxRequestTarget target) {
    }

    @Override
	protected CharSequence getEventHandler()
	{
        return generateCallbackScript(new AppendingStringBuffer("wicketAjaxPost('").append(
                getCallbackUrl(false)).append(
                "', wicketSerialize(Wicket.$('" + getComponent().getMarkupId() + "'))"));
	}


}
