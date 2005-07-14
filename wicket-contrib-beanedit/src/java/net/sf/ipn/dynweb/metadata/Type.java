package net.sf.ipn.dynweb.metadata;

/**
 * @author Jonathan Carlson Provides information on a type of object.
 */
public interface Type
{

	public String getName();

	public String getTitle();

	public void visit(TypeVisitor visitor);

}
