/*
 * Created on Dec 21, 2004
 */
package net.sf.ipn.dynweb.metadata;

/**
 * @author Jonathan Carlson Provides error and informational message information
 */
public class Message
{

	private String key;

	public Message(String key)
	{
		this.key = key;
	}

	public String getKey()
	{
		return this.key;
	}

}
