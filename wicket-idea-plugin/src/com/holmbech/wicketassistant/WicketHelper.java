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

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassInitializer;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLocalVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anders Holmbech Brandt
 *         Date: 2006-10-12
 */
public class WicketHelper {
    /**
     * Opens the file if found in the given directory
     * @param containingDirectory the directory to find the file in
     * @param fileName the filename to find in the directory
     */
    public static Editor openFile(PsiDirectory containingDirectory, String fileName) {
        PsiFile file = containingDirectory.findFile(fileName);
        if (file == null) return null;
        Project project = file.getProject();
        FileEditorManager instance = FileEditorManager.getInstance(project);
        VirtualFile virtualFile = file.getVirtualFile();
        return instance.openTextEditor(new OpenFileDescriptor(project, virtualFile), true);
    }

    /**
     * @param psiClass the class to findwicket ids in
     * @return all the wicket ids found the in the given class
     */
    @SuppressWarnings("unchecked")
    public static String[] getWicketIdsFromJavaFile(PsiClass psiClass) {
        WicketIdVisitor visitor = new WicketIdVisitor();
        PsiField[] psiFields = psiClass.getFields();
        for (int i = 0; i < psiFields.length; i++) {
            PsiField psiField = psiFields[i];
            psiField.accept(visitor);
        }
        PsiClassInitializer[] psiClassInitializers = psiClass.getInitializers();
        for (int i = 0; i < psiClassInitializers.length; i++) {
            PsiClassInitializer psiClassInitializer = psiClassInitializers[i];
            psiClassInitializer.accept(visitor);
        }
        PsiMethod[] psiMethods = psiClass.getMethods();
        for (int i = 0; i < psiMethods.length; i++) {
            PsiMethod psiMethod = psiMethods[i];
            psiMethod.accept(visitor);
        }

        List wicketIds = visitor.getWicketIds();
        return (String[]) wicketIds.toArray(new String[wicketIds.size()]);
    }

    /**
     *
     * @param psiFile
     * @return all wicket ids found in the given html file
     */
    public static String[] getWicketIdsFromHtmlFile(PsiFile psiFile) {
        return new String[0];
    }


    /**
     * This is the class that does all the work.
     */
    private static class WicketIdVisitor extends PsiRecursiveElementVisitor {
        List<String> wicketIds = new ArrayList<String>();

        public void visitReferenceExpression(PsiReferenceExpression expression) {
            super.visitReferenceExpression(expression);
        }

        public void visitNewExpression(PsiNewExpression expression) {
            super.visitNewExpression(expression);
            if (expression.resolveConstructor() != null) {
                PsiMethod constructor = expression.resolveConstructor();
                if (constructor == null || !constructor.getContainingFile().isPhysical()) {
                    return;
                }
                wicketIdChecker(constructor, expression);
            }
        }

        private void wicketIdChecker(PsiMethod constructor, PsiNewExpression expression) {
            if (instanceOfWicketComponent(constructor.getContainingClass())) {
                PsiExpressionList expressionList = expression.getArgumentList();
                if (expressionList == null || expressionList.getExpressions().length == 0) {
                    return;
                }
                PsiExpression psiExpression = expressionList.getExpressions()[0];
                String wicketId;
                int length = psiExpression.getText().length();
                // if the element it not string (ie. starts with a ")
                if (!(psiExpression.getText().charAt(0) == '\"')) {
                    if (psiExpression.getType() != null && "String".equals(psiExpression.getType().getPresentableText())) {
                        PsiReference reference = psiExpression.getReference();
                        if (reference == null) return;
                        PsiElement psiElement = reference.resolve();
                        if (psiElement == null) return;
                        PsiLocalVariable localVariable = (PsiLocalVariable) psiElement;
                        PsiExpression initializer = localVariable.getInitializer();
                        if (initializer == null) return;
                        wicketId = initializer.getText().substring(1, length - 1);
                    } else {
                        return;
                    }
                } else {
                    wicketId = psiExpression.getText().substring(1, length - 1);
                }
                wicketIds.add(wicketId);
            }
        }

        /**
         * Looks through the object hierachy to see it the object or one of its fathers is extending
         * a wicket.Component object. If so we have a winner :-)
         * @return true if class is a descendant of wicket.Component
         */
        private boolean instanceOfWicketComponent(PsiClass aClass) {
            while (aClass != null && !"Object".equals(aClass.getQualifiedName())) {
                if ("wicket.Component".equals(aClass.getQualifiedName())) {
                    return true;
                }
                aClass = aClass.getSuperClass();
            }
            return false;
        }

        /**
         * This method is used to return all the wicket ids found
         * @return
         */
        public List getWicketIds() {
            return wicketIds;
        }
    }

}
