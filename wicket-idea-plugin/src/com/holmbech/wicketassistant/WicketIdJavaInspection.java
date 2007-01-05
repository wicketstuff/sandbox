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
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.*;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anders Holmbech Brandt
 *         Date: 2006-09-10
 */
public class WicketIdJavaInspection extends LocalInspectionTool {

    private static final Logger LOG = Logger.getInstance(WicketIdJavaInspection.class.getName());

    @NotNull
    public String getGroupDisplayName() {
        return "Wicket Assistent";
    }

    @NotNull
    public String getDisplayName() {
        return "Wicket id java inspection";
    }

    @NotNull
    public String getShortName() {
        return "WicketIdJavaInspection";
    }

    @NotNull
    public HighlightDisplayLevel getDefaultLevel() {
        return HighlightDisplayLevel.WARNING;
    }

    public boolean isEnabledByDefault() {
        return true;
    }

    public ProblemDescriptor[] checkClass(@NotNull PsiClass aClass, @NotNull InspectionManager manager, boolean isOnTheFly) {
        PsiClassInitializer[] initializers = aClass.getInitializers();
        WicketIdVisitor visitor = new WicketIdVisitor(manager);
        for (int i = 0; i < initializers.length; i++) {
            PsiClassInitializer initializer = initializers[i];
            initializer.accept(visitor);
        }
        return visitor.problems.toArray(new ProblemDescriptor[visitor.problems.size()]);
    }

    public ProblemDescriptor[] checkMethod(@NotNull PsiMethod method, @NotNull InspectionManager manager, boolean isOnTheFly) {
        WicketIdVisitor visitor = new WicketIdVisitor(manager);
        method.accept(visitor);
        return visitor.problems.toArray(new ProblemDescriptor[visitor.problems.size()]);
    }

    public ProblemDescriptor[] checkField(@NotNull PsiField field, @NotNull InspectionManager manager, boolean isOnTheFly) {
        WicketIdVisitor visitor = new WicketIdVisitor(manager);
        field.accept(visitor);
        return visitor.problems.toArray(new ProblemDescriptor[visitor.problems.size()]);
    }

    private static class WicketIdVisitor extends PsiRecursiveElementVisitor {
        public List<ProblemDescriptor> problems = new ArrayList<ProblemDescriptor>();
        private InspectionManager manager;

        public WicketIdVisitor(InspectionManager manager) {
            this.manager = manager;
        }

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
                if (expressionList == null) {
                    return;
                }
                
                LOG.debug("expression argumentlist: " + expression.getArgumentList());
                final PsiExpression[] psiExpressions = expressionList.getExpressions();

                if (psiExpressions.length <= 0) {
                    return;
                }
                
                PsiExpression idElement = psiExpressions[0];
                int length = idElement.getText().length();
                if (!(idElement.getText().charAt(0) == '\"')) {
                    return; // todo check if its a string, if its a string variable check that one instead.
                }
                String wicketId = idElement.getText().substring(1, length - 1);
                PsiFile containingFile = idElement.getContainingFile();
                String fileName = containingFile.getName();
                if (fileName == null) {
                    return;
                }
                fileName = fileName.substring(0, fileName.length() - 5);
                PsiDirectory containingDirectory = containingFile.getContainingDirectory();
                if (containingDirectory == null) {
                    return;
                }
                PsiFile htmlFile = containingDirectory.findFile(fileName + ".html");
                if (htmlFile != null && !wicketIdExistsInHtmlfile(wicketId, ((XmlFile) htmlFile).getDocument())) {
                    LocalQuickFix fix = null;
                    problems.add(manager.createProblemDescriptor(idElement, "Wicket id missing in html file?",
                        fix, ProblemHighlightType.GENERIC_ERROR_OR_WARNING));
                }
            }
        }

        private boolean wicketIdExistsInHtmlfile(final String wicketId, XmlDocument document) {
            final boolean[] result = new boolean[]{false};
            document.accept(new PsiRecursiveElementVisitor() {
                public void visitReferenceExpression(PsiReferenceExpression expression) {
                    super.visitReferenceExpression(expression);
                }

                public void visitXmlAttribute(XmlAttribute attribute) {
                    super.visitXmlAttribute(attribute);
                    if ("wicket:id".equals(attribute.getName()) && wicketId.equals(attribute.getValue())) {
                        result[0] = true;
                    }
                }
            });
            return result[0];
        }

        private boolean instanceOfWicketComponent(PsiClass aClass) {
            while (aClass != null && !"Object".equals(aClass.getQualifiedName())) {
                if ("wicket.Component".equals(aClass.getQualifiedName())) {
                    return true;
                }
                aClass = aClass.getSuperClass();
            }
            return false;
        }
    }
}
