/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.cluster.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds serialized attribute
 * @author Matej Knopp
 */
public class SessionAttributeHolder implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private byte[] message;
	
	public SessionAttributeHolder(Object object) {
		message = objectToByteArray(object);
	}
	
	public Object toOriginalObject() {
		return byteArrayToObject(message);
	}
	
	/**
	 * De-serializes an object from a byte array.
	 * 
	 * @param data
	 *            The serialized object
	 * @return The object
	 */
	public static Object byteArrayToObject(final byte[] data)
	{
		try
		{
			final ByteArrayInputStream in = new ByteArrayInputStream(data);
			try
			{
				return newObjectInputStream(in).readObject();
			}
			finally
			{
				in.close();
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("Could not deserialize object", e);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not deserialize object", e);
		}
	}

	/**
	 * Serializes an object into a byte array.
	 * 
	 * @param object
	 *            The object
	 * @return The serialized object
	 */
	public static byte[] objectToByteArray(final Object object)
	{
		try
		{
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			try
			{
				newObjectOutputStream(out).writeObject(object);
			}
			finally
			{
				out.close();
			}
			return out.toByteArray();
		}
		catch (Exception e)
		{
			log.error("Error serializing object " + object.getClass() + " [object=" + object + "]",
					e);
		}
		return null;
	}

	
	public static ObjectInputStream newObjectInputStream(InputStream in) throws IOException
	{
		return new ObjectInputStream(in);
	}

	
	public static ObjectOutputStream newObjectOutputStream(final OutputStream out) throws IOException
	{
		return new ObjectOutputStream(out);
	}
	
	private static final Logger log = LoggerFactory.getLogger(SessionAttributeHolder.class);
}