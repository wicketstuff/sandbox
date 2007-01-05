/*
 * Copyright 2006 Holmbech Development
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.holmbech.wicketassistant;

import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * This class is the integration point between IDEA and the rest of the plugin.
 * 
 * @author Anders Holmbech Brandt
 *         Date: 2006-09-04
 */
public class WicketAssistentApplicationComponent implements ApplicationComponent, InspectionToolProvider {

    /** Stores a reference to the AnAction we're replacing. */
    private AnAction defaultGotoAction;

    public WicketAssistentApplicationComponent() {
    }

    public void initComponent() {
        defaultGotoAction = ActionManager.getInstance().getAction("GotoDeclaration");
        replaceAction("GotoDeclaration", new WicketIdGotoAction(defaultGotoAction));
    }

    public void disposeComponent() {
        replaceAction("GotoDeclaration", defaultGotoAction);
    }

    @NotNull
    public String getComponentName() {
        return "WicketAssistentApplicationComponent";
    }

    public Class[] getInspectionClasses() {
        return new Class[]{WicketIdJavaInspection.class, WicketIdHtmlInspection.class};
    }

    private static void replaceAction(String interceptedAction, AnAction action) {
        final ActionManager actionManager = ActionManager.getInstance();
        actionManager.unregisterAction(interceptedAction);
        actionManager.registerAction(interceptedAction, action);
    }
}
