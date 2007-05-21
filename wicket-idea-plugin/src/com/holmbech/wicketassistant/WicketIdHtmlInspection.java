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

import com.intellij.codeHighlighting.HighlightDisplayLevel;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.xml.XmlAttribute;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anders Holmbech Brandt
 *         Date: 2006-09-10
 */
public class WicketIdHtmlInspection extends LocalInspectionTool {

    public WicketIdHtmlInspection() {
        super();
    }

    @NotNull
    public String getGroupDisplayName() {
        return "Wicket Assistant";
    }

    @NotNull
    public String getDisplayName() {
        return "Wicket ID HTML Inspection";
    }

    @NotNull
    public String getShortName() {
        return "WicketIdHtmlInspection";
    }

    @NotNull
    public HighlightDisplayLevel getDefaultLevel() {
        return HighlightDisplayLevel.WARNING;
    }

    public boolean isEnabledByDefault() {
        return true;
    }

    @SuppressWarnings("unchecked")
    public ProblemDescriptor[] checkFile(@NotNull PsiFile file,
        @NotNull InspectionManager manager, boolean isOnTheFly) {
        
        WicketIdHtmlInspection.WicketIdVisitor idVisitor = new WicketIdHtmlInspection.WicketIdVisitor(manager);
        file.accept(idVisitor);
        return idVisitor.problems.toArray(new ProblemDescriptor[idVisitor.problems.size()]);
    }

    private class WicketIdVisitor extends PsiRecursiveElementVisitor {
        public List<ProblemDescriptor> problems = new ArrayList<ProblemDescriptor>();
        private InspectionManager manager;

        public WicketIdVisitor(InspectionManager manager) {
            this.manager = manager;
        }

        public void visitReferenceExpression(PsiReferenceExpression expression) {
            super.visitReferenceExpression(expression);
        }

        /**
         * The html inspector method that finds wicket:ids in a html file
         */
        @SuppressWarnings("unchecked")
        public void visitXmlAttribute(XmlAttribute attribute) {
            super.visitXmlAttribute(attribute);
            String name = attribute.getContainingFile().getName();
            if (name == null || !name.endsWith("html")) {
                return;
            }
            if ("wicket:id".equals(attribute.getName())) {
                String fileName = attribute.getContainingFile().getName();
                if (fileName == null) {
                    return;
                }
                PsiFile containingFile = attribute.getContainingFile();
                PsiDirectory containingDirectory = containingFile.getContainingDirectory();
                if (containingDirectory == null) {
                    return;
                }
                fileName = fileName.substring(0, fileName.length() - 5);
                PsiFile javaFile = containingDirectory.findFile(fileName + ".java");
                if (javaFile != null && !wicketIdExistsInJavafile(attribute.getValue(), javaFile)) {
                    LocalQuickFix fix = null;
                    problems.add(manager.createProblemDescriptor(attribute, "Wicket id missing in java file?",
                        fix, ProblemHighlightType.GENERIC_ERROR_OR_WARNING));
                }
            }
        }

        private boolean wicketIdExistsInJavafile(final String htmlWicketId, PsiFile javaFile) {
            if (htmlWicketId == null || htmlWicketId.length() == 0) {
                return false;
            }

            HtmlElementVisitor visitor = new HtmlElementVisitor(htmlWicketId, new boolean[]{false});
            javaFile.accept(visitor);
            return visitor.getResult()[0];
        }
    }

    private class HtmlElementVisitor extends PsiRecursiveElementVisitor {

        private String htmlWicketId;
        private boolean[] result;

        public HtmlElementVisitor(String htmlWicketId, boolean[] result) {
            this.htmlWicketId = htmlWicketId;
            this.result = result;
        }

        public void visitReferenceExpression(PsiReferenceExpression expression) {
            super.visitReferenceExpression(expression);
        }

        public void visitNewExpression(PsiNewExpression expression) {
            super.visitNewExpression(expression);
            PsiMethod constructor = expression.resolveConstructor();
            if (constructor == null || !constructor.getContainingFile().isPhysical()) {
                return;
            }

            if (WicketHelper.instanceOfWicketComponent(constructor.getContainingClass())) {
                PsiExpressionList expressionList = expression.getArgumentList();
                if (expressionList == null) {
                    return;
                }

                final PsiExpression[] psiExpressions = expressionList.getExpressions();
                if (psiExpressions.length > 0) {
                    PsiExpression idElement = psiExpressions[0];
                    int length = idElement.getText().length();
                    if (length > 0) {
                        String theWicketId = idElement.getText().substring(1, length - 1);
                        if (theWicketId != null && htmlWicketId.equals(theWicketId)) {
                            result[0] = true;
                        }
                    }
                }
            }
        }

        public boolean[] getResult() {
            return result;
        }

    }
}
