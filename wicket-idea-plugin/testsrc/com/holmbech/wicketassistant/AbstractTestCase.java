package com.holmbech.wicketassistant;

import com.intellij.testFramework.InspectionTestCase;
import com.intellij.testFramework.TestLogger;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.lang.properties.PropertiesReferenceManager;
import com.intellij.ide.startup.impl.StartupManagerImpl;

import java.io.IOException;

/**
 * @author Anders
 *         Date: 2006-12-14
 */
public abstract class AbstractTestCase extends InspectionTestCase {
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
}
