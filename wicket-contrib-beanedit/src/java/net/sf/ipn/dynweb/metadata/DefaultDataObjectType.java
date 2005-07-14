/*
 * Created on Dec 23, 2004
 */
package net.sf.ipn.dynweb.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Jonathan Carlson TODO Provides...
 */
public class DefaultDataObjectType implements DataObjectType, Serializable
{

	private String name;
	private List attributes = new ArrayList();
	private List relationships = new ArrayList();

	/**
	 * @see net.sf.ipn.dynweb.metadata.DataObjectType#getAttributes()
	 */
	public Attribute[] getAttributes()
	{
		return (Attribute[])this.attributes.toArray(new Attribute[] {});
	}

	public void addAttribute(Attribute attr)
	{
		this.attributes.add(attr);
	}

	/**
	 * @see net.sf.ipn.dynweb.metadata.DataObjectType#getRelationships()
	 */
	public Relationship[] getRelationships()
	{
		return (Relationship[])this.relationships.toArray(new Relationship[] {});
	}

	public void addRelationship(Relationship r)
	{
		this.relationships.add(r);
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @see net.sf.ipn.dynweb.metadata.Type#visit(net.sf.ipn.uimeta.TypeVisitor)
	 */
	public void visit(TypeVisitor visitor)
	{
		visitor.handleDataObjectType(this);
	}

}
