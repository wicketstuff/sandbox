package org.wicketstuff.pickwick.backend;

import java.beans.IntrospectionException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.xml.sax.SAXException;

/**
 * mapp a bean to a xml File
 * @author Vincent Demay
 *
 * @param <T>
 * TODO : remove deprecated
 * FIXME : catched exception
 * TODO : JavaDoc
 */
public class XmlBeanMapper<T extends Object> {
	
	/**
	 * Serialize a bean as xml in the stream
	 * @param bean bean to serialize
	 * @param stream stream where serialize the bean
	 */
	public void serializeInXml(T bean, FileWriter outputWriter){
		//BeanWriter beanWriter = new BeanWriter(stream);
		BeanWriter beanWriter = new BeanWriter(outputWriter);
		
		//FIXME : look for replacement
		beanWriter.getXMLIntrospector().setAttributesForPrimitives(false);
	    beanWriter.setWriteIDs(false);
	    beanWriter.enablePrettyPrint();
	    
	    String name = bean.getClass().getName().toLowerCase();
	    name = name.substring(name.lastIndexOf('.') + 1);
	    
	    try {
			beanWriter.write(name, bean);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(outputWriter.toString());
	}
	
	/**
	 * bind a xml in a bean. If the bean is not initialized it will.
	 * @param stream
	 * @param bean
	 */
	public T bindInBean(FileReader reader, T bean){
        BeanReader beanReader  = new BeanReader();
//      FIXME : look for replacement
        beanReader.getXMLIntrospector().setAttributesForPrimitives(false);
        beanReader.setMatchIDs(false);
        
        String name = bean.getClass().getName().toLowerCase();
	    name = name.substring(name.lastIndexOf('.') + 1);
        
        try {
			beanReader.registerBeanClass(name, bean.getClass());
	        bean = (T) beanReader.parse(reader);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return bean;
	}
}
