/**
 * 
 */
package wicket.contrib.dojo;

import java.io.IOException;
import java.io.OutputStream;

import wicket.IRequestTarget;
import wicket.RequestCycle;
import wicket.Session;
import wicket.ajax.AjaxRequestTarget;
import wicket.util.io.Streams;
import wicket.util.resource.IResourceStream;

/**
 * @author jcompagner
 * @deprecated will be remove in 2.0 use {@link AjaxRequestTarget}
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
		catch (Exception exception)
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
	 * @see wicket.IRequestTarget#getLock(wicket.RequestCycle)
	 */
	public Object getLock(RequestCycle requestCycle)
	{
		return Session.get();
	}

	/**
	 * @see wicket.IRequestTarget#detach(wicket.RequestCycle)
	 */
	public void detach(RequestCycle requestCycle)
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

}
