package com.holmbech.wicketassistant;

import com.intellij.codeInsight.completion.CompletionUtil;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * User: Nick Heudecker
 * Date: Jan 2, 2007
 * Time: 10:36:24 AM
 */
public class WicketIdCompletionComponent implements ProjectComponent {

    private Project project;

    public WicketIdCompletionComponent(Project project) {
        this.project = project;
    }

    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "WicketIdCompletionComponent";
    }

    public void projectOpened() {
        final FileType htmlFileType = FileTypeManager.getInstance().getFileTypeByExtension("html");
        System.out.println("FileType: " + htmlFileType.getName());
        CompletionUtil.registerCompletionData(htmlFileType, new WicketJavaIdCompletionData());
        final FileType javaFileType = FileTypeManager.getInstance().getFileTypeByExtension("java");
        System.out.println("FileType: " + javaFileType.getName());
        CompletionUtil.registerCompletionData(javaFileType, new WicketHtmlIdCompletionData());
    }

    public void projectClosed() {
        // called when project is being closed
    }
}
