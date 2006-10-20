package wicket.contrib.util.resource;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import wicket.WicketRuntimeException;
import wicket.behavior.AbstractBehavior;
import wicket.markup.html.IHeaderContributor;
import wicket.markup.html.IHeaderResponse;
import wicket.model.IDetachable;
import wicket.model.IModel;
import wicket.util.string.Strings;

/**
 * An IHeaderContributor implementation that renders a velocity template and
 * writes it to the response. The template is loaded via Velocity's resource
 * loading mechanism, as defined in your velocity.properties. If you do not have
 * a velocity.properties for your app, it will default to a directory
 * "templates" in the root of your app.
 * 
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 * 
 */
public class VelocityContributor<K, V> extends AbstractBehavior implements IHeaderContributor
{

	private static final long serialVersionUID = 1L;

	/** Whether to escape HTML characters. The default value is false. */
	private boolean escapeHtml = false;

	private IModel<Map<K, V>> model;

	private String templateName;

	private String encoding = "ISO-8859-1";

	public String getEncoding()
	{
		return encoding;
	}

	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

	/**
	 * Ctor for VelocityContributor
	 * 
	 * The templateName needs to have the full path relative to where the
	 * resource loader starts looking. For example, if there is a template next
	 * to this class in the package called foo.vm, and you have configured the
	 * ClassPathResourceLoader, template name will then be
	 * "wicket/contrib/util/resource/foo.vm". Wicket provides a nice utility
	 * {@link wicket.util.lang.Packages} for this.
	 * 
	 * 
	 * @param templateName
	 * @param model
	 */
	public VelocityContributor(String templateName, final IModel<Map<K, V>> model)
	{
		this.templateName = templateName;
		this.model = model;
	}

	protected String evaluate()
	{

		if (!Velocity.resourceExists(templateName))
		{
			return null;
		}
		// create a Velocity context object using the model if set
		final VelocityContext ctx = new VelocityContext(model.getObject());

		// create a writer for capturing the Velocity output
		StringWriter writer = new StringWriter();

		try
		{
			// execute the velocity script and capture the output in writer
			if (!Velocity.mergeTemplate(templateName, encoding, ctx, writer))
			{
				return null;
			}

			// replace the tag's body the Velocity output
			String result = writer.toString();

			if (escapeHtml)
			{
				// encode the result in order to get valid html output that
				// does not break the rest of the page
				result = Strings.escapeMarkup(result).toString();
			}
			return result;
		}
		catch (Exception e)
		{
			throw new WicketRuntimeException(e);
		}
	}

	public void renderHead(IHeaderResponse response)
	{
		String s = evaluate();
		if (null != s)
		{
			response.getResponse().println(s);
		}
	}

	public void detachModel()
	{
		if (model instanceof IDetachable)
		{
			((IDetachable) model).detach();

		}
	}

}
