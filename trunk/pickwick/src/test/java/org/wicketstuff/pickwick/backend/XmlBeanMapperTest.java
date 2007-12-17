package org.wicketstuff.pickwick.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;

public class XmlBeanMapperTest extends TestCase {

    public void testGenerate() throws Exception {
    	File file = File.createTempFile(XmlBeanMapperTest.class.getName(), null);
        XmlBeanMapper<PersonBean> mapper = new XmlBeanMapper<PersonBean>(PersonBean.class);
        FileOutputStream fo = new FileOutputStream(file);
        
        PersonBean bean = new PersonBean("Doume", 25);
        mapper.serializeInXml(bean, fo);
        
        assertTrue(file.exists());
        
        PersonBean person = new PersonBean();
        person = mapper.bindInBean(new FileInputStream(file));
        
        assertEquals(bean.getName(), person.getName());
        assertEquals(bean.getAge(), person.getAge());
    }
}
