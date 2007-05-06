package com.holmbech.wicketassistant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.intellij.codeInsight.completion.CompletionContext;
import com.intellij.codeInsight.completion.CompletionVariant;
import com.intellij.codeInsight.completion.DefaultInsertHandler;
import com.intellij.codeInsight.completion.HtmlCompletionData;
import com.intellij.codeInsight.completion.KeywordChooser;
import com.intellij.codeInsight.lookup.LookupItem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.psi.filters.TextFilter;
import com.intellij.psi.filters.TrueFilter;
import com.intellij.psi.filters.position.LeftNeighbour;
import com.intellij.psi.impl.source.tree.LeafPsiElement;

/**
 * User: Nick Heudecker Date: Jan 2, 2007 Time: 11:50:41 AM
 */
public class WicketJavaIdCompletionData extends HtmlCompletionData {
    public WicketJavaIdCompletionData() {
        registerAttributeValueCompletionVariant();
    }

    private void registerAttributeValueCompletionVariant() {
        try {
            LeftNeighbour left = new LeftNeighbour(new TextFilter("\""));
            CompletionVariant completionVariant = new CompletionVariant(left);
            completionVariant.includeScopeClass(LeafPsiElement.class, true);
            completionVariant.addCompletionFilterOnElement(TrueFilter.INSTANCE);
            completionVariant.addCompletion(new SimpleKeywordChooser(), 0);
            completionVariant.setInsertHandler(new DefaultInsertHandler());
            registerVariant(completionVariant);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completeReference(PsiReference psiReference, Set<LookupItem> set,
        CompletionContext completionContext, PsiElement psiElement) {
        super.completeReference(psiReference, set, completionContext, psiElement);
    }

    private class SimpleKeywordChooser implements KeywordChooser {
        private final String[] EMPTY_KEYWORDS = new String[0];

        public String[] getKeywords(CompletionContext completionContext, PsiElement psiElement) {
            PsiFile containingFile = psiElement.getContainingFile();
            if(containingFile == null) {
                return EMPTY_KEYWORDS;
            }
            VirtualFile virtualFile = containingFile.getOriginalFile().getVirtualFile();
            if(virtualFile != null && virtualFile.getExtension() != null) {
                String fileExtension = virtualFile.getExtension();
                String fileName = virtualFile.getNameWithoutExtension();
                if("html".equalsIgnoreCase(fileExtension)) {
                    final PsiFile origFile = containingFile.getOriginalFile();
                    if(origFile == null) {
                        return EMPTY_KEYWORDS;
                    }
                    final PsiDirectory javaDirectory = origFile.getContainingDirectory();
                    if(javaDirectory == null) {
                        return EMPTY_KEYWORDS;
                    }
                    PsiFile javaFile = javaDirectory.findFile(fileName + ".java");
                    WicketIdElementJavaVisitor visitor = new WicketIdElementJavaVisitor();
                    javaFile.accept(visitor);
                    return visitor.getReferencesArray();
                } else if("java".equalsIgnoreCase(fileExtension)) {
                    System.out.println("Can't complete for java file just yet.");
                }
            }
            return EMPTY_KEYWORDS;
        }
    }

    private class WicketIdElementJavaVisitor extends PsiRecursiveElementVisitor {
        private List<String> references = new ArrayList<String>();

        public List<String> getReferences() {
            return references;
        }

        public void setReferences(final List<String> references) {
            this.references = references;
        }

        public String[] getReferencesArray() {
            return references.toArray(new String[references.size()]);
        }

        public void visitNewExpression(PsiNewExpression expression) {
            super.visitNewExpression(expression);
            if(expression.resolveConstructor() != null) {
                PsiMethod constructor = expression.resolveConstructor();
                if(constructor == null || !constructor.getContainingFile().isPhysical()) {
                    return;
                }
                wicketIdChecker(constructor, expression);
            }
        }

        private void wicketIdChecker(PsiMethod constructor, PsiNewExpression expression) {
            if(instanceOfWicketComponent(constructor.getContainingClass())) {
                PsiExpressionList expressionList = expression.getArgumentList();
                if(expressionList == null || expressionList.getExpressions().length == 0) {
                    return;
                }
                PsiExpression psiExpression = expressionList.getExpressions()[0];
                String qualifyingExpression;
                int length = psiExpression.getText().length();
                // if the element it not string (ie. starts with a ")
                if(!(psiExpression.getText().charAt(0) == '\"')) {
                    if(psiExpression.getType() != null && "String"
                        .equals(psiExpression.getType().getPresentableText())) {
                        PsiReference reference = psiExpression.getReference();
                        if(reference == null) {
                            return;
                        }
                        PsiElement psiElement = reference.resolve();
                        if(psiElement == null) {
                            return;
                        }
                        PsiLocalVariable localVariable = (PsiLocalVariable)psiElement;
                        PsiExpression initializer = localVariable.getInitializer();
                        if(initializer == null) {
                            return;
                        }
                        qualifyingExpression = initializer.getText();
                    } else {
                        return;
                    }
                } else {
                    qualifyingExpression = psiExpression.getText();
                }

                // remove the quotes from the expression.  this can probably be improved.
                if(qualifyingExpression.startsWith("\"")) {
                    qualifyingExpression = qualifyingExpression.substring(1);
                }
                if(qualifyingExpression.endsWith("\"")) {
                    qualifyingExpression = qualifyingExpression.substring(0, qualifyingExpression.length() - 1);
                }
                references.add(qualifyingExpression);
            }
        }

        private boolean instanceOfWicketComponent(PsiClass aClass) {
            while(aClass != null && !"Object".equals(aClass.getQualifiedName())) {
                if("wicket.Component".equals(aClass.getQualifiedName())) {
                    return true;
                }
                aClass = aClass.getSuperClass();
            }
            return false;
        }


    }
}
