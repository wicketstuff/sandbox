package wicket.contrib.util.resource;

import java.util.Map;

import wicket.markup.html.IHeaderResponse;
import wicket.model.IModel;
import wicket.util.lang.Packages;

/**
 * A derivation of VelocityContributor that uses
 * {@link wicket.markup.html.IHeaderResponse#renderJavascript(CharSequence, String)}
 * 
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 * 
 */
public class VelocityJavascriptContributor<K, V> extends VelocityContributor<K, V>
{

	private String id;

	/**
	 * Construct.
	 * 
	 * Use this constructor if you have configured Velocity to use a
	 * ClasspathResourceLoader. The templatePath will then be relative to the
	 * package for clazz
	 * 
	 * @param clazz
	 * @param templatePath
	 * @param model
	 * @param id
	 */
	public VelocityJavascriptContributor(Class clazz, String templatePath,
			IModel<Map<K, V>> model, String id)
	{
		super(Packages.absolutePath(clazz, templatePath), model);
		this.id = id;
	}

	/**
	 * Construct.
	 * 
	 * Use this constructor when Velocity is configured with the
	 * FileResourceLoader. templatePath with then be relative to the loader path
	 * configured in velocity.properties
	 * 
	 * @param templatePath
	 * @param model
	 * @param id
	 */
	public VelocityJavascriptContributor(String templatePath, IModel<Map<K, V>> model,
			String id)
	{
		super(templatePath, model);
		this.id = id;
	}

	/**
	 * @see wicket.contrib.util.resource.VelocityContributor#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		String s = evaluate();
		if (null != s)
		{
			response.renderJavascript(s, id);
		}
	}
}
