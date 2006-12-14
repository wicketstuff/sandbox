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
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaToken;

/**
 * @author Anders Holmbech Brandt
 *         Date: 2006-10-12
 */
public class WicketIdGotoAction extends AnAction {

    private AnAction defaultGotoAction = null;

    public WicketIdGotoAction() {
    }

    public WicketIdGotoAction(AnAction defaultGotoAction) {
        this.defaultGotoAction = defaultGotoAction;
    }

    public void actionPerformed(AnActionEvent event) {
        DataContext dataContext = event.getDataContext();
        if (!gotoWicketId(dataContext)) {
            defaultGotoAction.actionPerformed(event);
        }
    }

    private boolean gotoWicketId(DataContext dataContext) {
        Editor editor = getEditor(dataContext);
        int offset = editor.getCaretModel().getOffset();
        PsiElement stringElement = getPsiFile(dataContext).findElementAt(offset);
        if (stringElement == null)
            return false;

        VirtualFile virtualFile = getPsiFile(dataContext).getVirtualFile();
        if (virtualFile == null)
            return false;
        if ("html".equals(virtualFile.getExtension())) {
            stringElement = stringElement.getParent(); // gets the string including the ""
            PsiElement parent = stringElement.getParent(); // gets what should be wicket:id...
            if (parent.getText().startsWith("wicket:id")) {
                String fileName = virtualFile.getNameWithoutExtension() + ".java";
                jumpToWicketId(dataContext, fileName, stringElement);
            }
        } else if ("java".equals(virtualFile.getExtension())) {
            if ((stringElement instanceof PsiJavaToken )) {
                PsiJavaToken token = (PsiJavaToken) stringElement;
                System.out.println("token = " + token);
                String fileName = virtualFile.getNameWithoutExtension() + ".html";
                jumpToWicketId(dataContext, fileName, stringElement);
            }
        }

        return true;
    }

    private void jumpToWicketId(DataContext dataContext, String fileName, PsiElement stringElement) {
        PsiDirectory containingDirectory = getPsiFile(dataContext).getContainingDirectory();
        Editor javaEditor = WicketHelper.openFile(containingDirectory, fileName);
        String text = javaEditor.getDocument().getText();
        int wicketIndex = text.indexOf(stringElement.getText());
        if (wicketIndex > -1) {
            CaretModel caretModel = javaEditor.getCaretModel();
            caretModel.moveToOffset(wicketIndex);
        }
    }

    private PsiFile getPsiFile(DataContext dataContext) {
        return (PsiFile) dataContext.getData(DataConstants.PSI_FILE);
    }

    private Project getProject(DataContext dataContext) {
        return (Project) dataContext.getData(DataConstants.PROJECT);
    }

    private Editor getEditor(DataContext dataContext) {
        return (Editor) dataContext.getData(DataConstants.EDITOR);
    }
}
