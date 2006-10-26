package org.stickywicket.renderer;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import wicket.model.AbstractModel;
import wicket.model.IModel;

public class ContentModel extends AbstractModel<String>
{
	private final IModel<Node> nodeModel;

	public ContentModel(IModel<Node> nodeModel)
	{
		if (nodeModel == null)
		{
			throw new IllegalArgumentException("Argument [[nodeModel]] cannot be null");
		}
		this.nodeModel = nodeModel;
	}

	public String getObject()
	{
		try
		{
			return nodeModel.getObject().getProperty("content").getValue().getString();
		}
		catch (ValueFormatException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalStateException e)
		{
			throw new RuntimeException(e);
		}
		catch (PathNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		catch (RepositoryException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setObject(String object)
	{
		try
		{
			nodeModel.getObject().getProperty("content").setValue(object);
		}
		catch (ValueFormatException e)
		{
			throw new RuntimeException(e);
		}
		catch (VersionException e)
		{
			throw new RuntimeException(e);
		}
		catch (LockException e)
		{
			throw new RuntimeException(e);
		}
		catch (ConstraintViolationException e)
		{
			throw new RuntimeException(e);
		}
		catch (PathNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		catch (RepositoryException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void detach()
	{
		nodeModel.detach();
	}
}
