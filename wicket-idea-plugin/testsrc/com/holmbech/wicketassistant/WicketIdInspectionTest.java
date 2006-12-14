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

import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.ide.startup.impl.StartupManagerImpl;
import com.intellij.lang.properties.PropertiesReferenceManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.InspectionTestCase;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.TestLogger;

import java.io.IOException;

/**
 * @author Anders Holmbech Brandt
 *         Date: 2006-09-19
 */
public class WicketIdInspectionTest extends InspectionTestCase {

    public WicketIdInspectionTest() {
    }

    protected void setUpProject() throws IOException {
        final String root = "testResources/projects/wicketProject";
        myProject = createProjectFrom(root);
        setUpJdk();
        myModule = ModuleManager.getInstance(myProject).getModules()[0];
    }

    static Project createProjectFrom(final String root) {
        VirtualFile tempProjectRootDir = prepareProjectDirectory(root);
        Project project = loadProjectComponents(tempProjectRootDir);

        ModuleManager.getInstance(project);

        PropertiesReferenceManager.getInstance(project).projectOpened();
        ((StartupManagerImpl) StartupManager.getInstance(project)).runStartupActivities();
        ((StartupManagerImpl) StartupManager.getInstance(project)).runPostStartupActivities();
        return project;
    }

    private static Project loadProjectComponents(VirtualFile tempProjectRootDir) {
        Project project = null;
        try {
            VirtualFile[] children = tempProjectRootDir.getChildren();
            for (VirtualFile virtualFile : children) {
                if (FileTypeManager.getInstance().getFileTypeByFile(virtualFile) == StdFileTypes.IDEA_PROJECT) {
                    project = ProjectManagerEx.getInstanceEx().loadProject(virtualFile.getPath());
                    break;
                }
            }
        }
        catch (Exception e) {
            TestLogger.getInstance("").error(e);
        }
        return project;
    }

    private static VirtualFile prepareProjectDirectory(final String root) {
        return ApplicationManager.getApplication().runWriteAction(new Computable<VirtualFile>() {
            public VirtualFile compute() {
                try {
                    return PsiTestUtil.createTestProjectStructure(null, FileUtil.toSystemIndependentName(root), myFilesToDelete, false);
                }
                catch (Exception e) {
                    TestLogger.getInstance("").error(e);
                    return null;
                }
            }
        });
    }

    public void testWicketProject() throws Exception {
        Module module = ModuleManager.getInstance(myProject).findModuleByName("test wicket project");
        PsiManager psiManager = PsiManager.getInstance(myProject);
        PsiClass aClass = psiManager.findClass("dk.test.TestPage", GlobalSearchScope.moduleWithDependenciesScope(module));
        ProblemDescriptor[] problemDescriptors = new WicketIdJavaInspection().checkClass(aClass, getManager(), true);
        assertEquals(0, problemDescriptors.length);
    }

    public void testWicketHelper() throws Exception {
        Module module = ModuleManager.getInstance(myProject).findModuleByName("test wicket project");
        PsiManager psiManager = PsiManager.getInstance(myProject);
        PsiClass aClass = psiManager.findClass("dk.test.TestPage", GlobalSearchScope.moduleWithDependenciesScope(module));
        String[] strings = WicketHelper.getWicketIdsFromJavaFile(aClass);

        assertEquals(5, strings.length);
        assertEquals("label_1", strings[0]);
        assertEquals("label_2", strings[1]);
        assertEquals("testid", strings[2]);
        assertEquals("someid", strings[3]);
        assertEquals("list", strings[4]);
    }
}
