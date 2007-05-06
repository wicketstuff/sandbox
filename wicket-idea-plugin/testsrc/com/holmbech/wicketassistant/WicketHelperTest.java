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
/**
 * @author Anders Holmbech Brandt
 * Date: 2006-10-22
 */

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;

public class WicketHelperTest extends AbstractTestCase {

    public void testGetWicketIdsFromJavaFile() throws Exception {
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

    public void testGetWicketIdsFromHtmlFile() throws Exception {
        /*
        Module module = ModuleManager.getInstance(myProject).findModuleByName("test wicket project");
        PsiManager psiManager = PsiManager.getInstance(myProject);
        PsiClass aClass = psiManager.;
        PsiClass aClass = psiManager.findFile("dk.test.TestPage", GlobalSearchScope.moduleWithDependenciesScope(module));
        String[] strings = WicketHelper.getWicketIdsFromJavaFile(aClass);

        assertEquals(5, strings.length);
        assertEquals("label_1", strings[0]);
        assertEquals("label_2", strings[1]);
        assertEquals("testid", strings[2]);
        assertEquals("someid", strings[3]);
        assertEquals("list", strings[4]);
        */
    }

}