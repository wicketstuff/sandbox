/*
 * $Id$
 * $Revision$
 * $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.phonebook.web.page;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.easymock.MockControl;

import wicket.Page;
import wicket.contrib.injection.web.InjectorHolder;
import wicket.contrib.phonebook.Contact;
import wicket.contrib.phonebook.ContactDao;
import wicket.contrib.spring.injection.annot.AnnotSpringInjector;
import wicket.contrib.spring.test.ApplicationContextMock;
import wicket.contrib.spring.test.SpringContextLocatorMock;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.util.tester.DummyHomePage;
import wicket.util.tester.WicketTester;

public class DeleteContactPageTest extends TestCase {
	public void test() throws ServletException {
		// 1. setup dependencies and mock objects
		Contact contact=new Contact();
		
		MockControl daoCtrl=MockControl.createControl(ContactDao.class);
		ContactDao dao=(ContactDao) daoCtrl.getMock();
		
		daoCtrl.expectAndReturn(dao.load(10), contact);
		dao.delete(10);
		
		daoCtrl.replay();
		
		// 2. setup mock injection environment
		ApplicationContextMock appctx=new ApplicationContextMock();
		appctx.putBean("contactDao", dao);
		
		SpringContextLocatorMock ctxLocator=new SpringContextLocatorMock(appctx);
		
		AnnotSpringInjector injector=new AnnotSpringInjector(ctxLocator);
		
		// 3. set our mock injection environment as active
		InjectorHolder.setInjector(injector);

		// 4. run the test
		WicketTester app=new WicketTester();
		
		app.startPage(new DeleteContactPage(new DummyHomePage(), 10));
		app.assertRenderedPage(DeleteContactPage.class);
		app.assertComponent("confirmForm", Form.class);
		app.assertComponent("confirmForm:confirm", Button.class);
		app.setParameterForNextRequest("confirmForm:confirm", "pressed");
		app.submitForm("confirmForm");
		app.assertRenderedPage(DummyHomePage.class);
		
		daoCtrl.verify();
	}
}
