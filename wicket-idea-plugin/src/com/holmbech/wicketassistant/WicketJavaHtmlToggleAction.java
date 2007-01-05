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

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;

/**
 * Toggles between the HTML and Java files for Wicket components.  Refer to the plugin.xml file for
 * the default keybinding.
 *
 * @author Anders Holmbech Brandt
 *         Date: 2006-10-11
 */
public class WicketJavaHtmlToggleAction extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        Object data = dataContext.getData(DataConstants.PSI_FILE);
        if (data instanceof PsiFile) {
            PsiFile psiFile = (PsiFile) data;
            VirtualFile virtualFile = psiFile.getVirtualFile();
            if (virtualFile == null) return;

            String fileName = virtualFile.getNameWithoutExtension();
            String extension = virtualFile.getExtension();
            PsiDirectory containingDirectory = psiFile.getContainingDirectory();
            if (containingDirectory == null) {
                return;
            }

            if ("html".equals(extension)) {
                String name = fileName + ".java";
                WicketHelper.openFile(containingDirectory, name);

            } else if ("java".equals(extension)) {
                String name = fileName + ".html";
                WicketHelper.openFile(containingDirectory, name);
            }
        }
    }
}
