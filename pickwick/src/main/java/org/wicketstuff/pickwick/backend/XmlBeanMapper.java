package org.wicketstuff.pickwick.backend;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.wicketstuff.pickwick.bean.Sequence;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.io.xml.AbstractXmlDriver;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Maps a {@link Sequence} bean to an XML file
 * 
 * @author Vincent Demay
 * 
 * @param <T>
 * 
 * TODO : remove deprecated
 * 
 * TODO : JavaDoc
 */
public class XmlBeanMapper<T extends Object>{

	XStream xstream;
	
	public XmlBeanMapper(Class<T> clazz) {
		super();
		//StaxDriver driver = new StaxDriver();
		//driver.setQnameMap(new QNameMap());
		this.xstream = new XStream(/**driver*/);
		Annotations.configureAliases(xstream, clazz);
	}

	/**
	 * Serialize a bean as xml in the stream
	 * 
	 * @param bean
	 *            bean to serialize
	 * @param stream
	 *            stream where serialize the bean
	 */
	public void serializeInXml(T bean, OutputStream stream) {
		xstream.toXML(bean, stream);
		try {
			stream.flush();
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * bind a xml in a bean. If the bean is not initialized it will.
	 * 
	 * @param stream
	 */
	@SuppressWarnings("unchecked")
	public T bindInBean(InputStream stream) {
		T toReturn = (T)xstream.fromXML(stream);
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return toReturn;
	}
}
