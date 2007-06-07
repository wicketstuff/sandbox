package org.wicketstuff.pickwick.backend;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import junit.framework.TestCase;

public class XmlBeanMapperTest extends TestCase {

    public void testGenerate() throws Exception {
    	File file = new File("/tmp/person.xml");
        XmlBeanMapper<PersonBean> mapper = new XmlBeanMapper<PersonBean>();
        FileWriter fw = new FileWriter(file);
        
        PersonBean bean = new PersonBean("Doume", 25);
        mapper.serializeInXml(bean, fw);
        fw.flush();
        fw.close();
        
        assertTrue(new File("/tmp/person.xml").exists());
        
        PersonBean person = new PersonBean();
        person = mapper.bindInBean(new FileReader(file), person);
        
        assertEquals(bean.getName(), person.getName());
        assertEquals(bean.getAge(), person.getAge());
    }
}
