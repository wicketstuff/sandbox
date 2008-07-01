/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.objectautocomplete;

import org.apache.wicket.*;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.settings.IDebugSettings;

import java.util.Iterator;

/**
 * Behaviour for object auto completion using a slightly modified variant of
 * {@see org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteBehavior}
 *
 * An (hidden) element is required to store the object id which has been selected.
 *
 * The type parameter is the type of the object to be rendered (not it's id)
 *
 * @author roland
 * @since May 18, 2008
 */
public class ObjectAutoCompleteBehavior<T> extends AutoCompleteBehavior<T> {

    private static final ResourceReference OBJECTAUTOCOMPLETE_JS = new JavascriptResourceReference(
            ObjectAutoCompleteBehavior.class, "wicketstuff-objectautocomplete.js");
    // Our version of 'wicket-autocomplete.js', with the patch from WICKET-1651
    private static final ResourceReference AUTOCOMPLETE_OBJECTIFIED_JS = new JavascriptResourceReference(
            ObjectAutoCompleteBehavior.class, "wicket-autocomplete-objectified.js");

    // Reference to upstream JS, use this if the required patch has been applied. For now, unused.
    private static final ResourceReference AUTOCOMPLETE_JS = new JavascriptResourceReference(
		AutoCompleteBehavior.class, "wicket-autocomplete.js");

    // Element holding the object id as value
    private Component objectElement;

    private ObjectAutoCompleteCancelListener cancelListener;
    private AutoCompletionChoicesProvider<T> choicesProvider;

    <I> ObjectAutoCompleteBehavior(Component pObjectElement,ObjectAutoCompleteBuilder<T,I> pBuilder) {
        super(pBuilder.objectAutoCompleteRenderer,
                new AutoCompleteSettings()
                        .setMaxHeightInPx(pBuilder.maxHeightInPx)
                        .setPreselect(pBuilder.preselect)
                        .setShowListOnEmptyInput(pBuilder.showListOnEmptyInput));
        objectElement = pObjectElement;
        cancelListener = pBuilder.cancelListener;
        choicesProvider = pBuilder.choicesProvider;
    }

    /**
     * Temporarily solution until patch from WICKET-1651 is applied. Note, that we avoid a call to super
     * to avoid the initialization in the direct parent class, but we have to copy over all other code from the parent,
     *
     * @param response response to write to
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        abstractDefaultAjaxBehaviour_renderHead(response);
        initHead(response);
    }

    // Copied over from AbstractDefaultAjaxBehaviour.renderHead() until patch
    // in WICKET-1651 gets applied
    private void abstractDefaultAjaxBehaviour_renderHead(IHeaderResponse response) {
		final IDebugSettings debugSettings = Application.get().getDebugSettings();

		response.renderJavascriptReference(WicketEventReference.INSTANCE);
		response.renderJavascriptReference(WicketAjaxReference.INSTANCE);

		if (debugSettings.isAjaxDebugModeEnabled())
		{
            response.renderJavascriptReference(new JavascriptResourceReference(
                    AbstractDefaultAjaxBehavior.class, "wicket-ajax-debug.js"));
			response.renderJavascript("wicketAjaxDebugEnable=true;", "wicket-ajax-debug-enable");
		}

		RequestContext context = RequestContext.get();
		if (context.isPortletRequest())
		{
			response.renderJavascript("Wicket.portlet=true", "wicket-ajax-portlet-flag");
		}
    }

    /**
     * Initialize response with our own java script
     *
     * @param response response to write to
     */
    protected void initHead(IHeaderResponse response)
	{
		response.renderJavascriptReference(AUTOCOMPLETE_OBJECTIFIED_JS);
		response.renderJavascriptReference(OBJECTAUTOCOMPLETE_JS);
		final String id = getComponent().getMarkupId();
        String initJS = String.format("new Wicketstuff.ObjectAutoComplete('%s','%s','%s',%s);", id,objectElement.getMarkupId(),
            getCallbackUrl(), constructSettingsJS());
		response.renderOnDomReadyJavascript(initJS);
	}

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        if (cancelListener != null) {
            final String keypress = "if (event) { var kc=wicketKeyCode(event); if (kc==27) {" +
                    generateCallbackScript("wicketAjaxGet('" + getCallbackUrl() + "&cancel=true&force=true'") +
                    "; return false;} else if (kc==13) return false; else return true;}";
            tag.put("onkeypress", keypress);

            final String onblur =
                    generateCallbackScript("wicketAjaxGet('" + getCallbackUrl() + "&cancel=true'") + "; return false;";
            tag.put("onblur",onblur);
        }
    }

    @Override
    protected void respond(AjaxRequestTarget target) {
        RequestCycle requestCycle = RequestCycle.get();
        boolean cancel = Boolean.valueOf(requestCycle.getRequest().getParameter("cancel")).booleanValue();
        boolean force = Boolean.valueOf(requestCycle.getRequest().getParameter("force")).booleanValue();
        if (cancelListener != null && cancel) {
            cancelListener.searchCanceled(target,force);
        } else {
            super.respond(target);
        }
    }

    @Override
    protected Iterator<T> getChoices(String input) {
        return choicesProvider.getChoices(input);
    }
}
