package org.stickywicket.renderer;

public enum NodeType {
	CONTAINER, CONTENT, CONTENT_REFERENCE;

	@Override
	public String toString()
	{
		return NodeType.class.getName() + "." + name();
	}

	public static NodeType fromString(String string)
	{
		for (NodeType type : values())
		{
			if (type.toString().equals(string))
			{
				return type;
			}
		}
		throw new IllegalStateException("Could not resolve NodeType from string [["
				+ string + "]]");
	}
}
