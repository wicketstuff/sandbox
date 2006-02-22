/**
 * 
 */
package wicket.contrib.dojo;

import java.io.IOException;
import java.io.OutputStream;

import wicket.IRequestTarget;
import wicket.RequestCycle;
import wicket.Session;
import wicket.util.io.Streams;
import wicket.util.resource.IResourceStream;

/**
 * @author jcompagner
 *
 */
public class DojoRequestTarget implements IRequestTarget
{

	private final IResourceStream resourceStream;

	/**
	 * @param resourceStream
	 */
	public DojoRequestTarget(IResourceStream resourceStream)
	{
		this.resourceStream = resourceStream;
	}

	/**
	 * @see wicket.IRequestTarget#respond(wicket.RequestCycle)
	 */
	public void respond(RequestCycle requestCycle)
	{
		final OutputStream out = requestCycle.getResponse().getOutputStream();
		try
		{
			Streams.copy(resourceStream.getInputStream(), out);
		}
		catch(Exception exception)
		{
			
		}
		finally
		{
			try
			{
				resourceStream.close();
				out.flush();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}		
	}

	/**
	 * @see wicket.IRequestTarget#cleanUp(wicket.RequestCycle)
	 */
	public void cleanUp(RequestCycle requestCycle)
	{
		try
		{
			resourceStream.close();
		}
		catch (IOException ex)
		{
			// ignore
		}
	}

	/**
	 * @see wicket.IRequestTarget#getLock(wicket.RequestCycle)
	 */
	public Object getLock(RequestCycle requestCycle)
	{
		return Session.get();
	}

}
