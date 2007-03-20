package wicket.contrib.util.resource;

import wicket.markup.html.IHeaderResponse;
import wicket.model.IModel;
import wicket.util.lang.Packages;

/**
 * A derivation of VelocityContributor that uses
 * {@link wicket.markup.html.IHeaderResponse#renderJavascript(CharSequence, String)}
 */
public class VelocityJavascriptContributor extends VelocityContributor
{

	private String id;

	/**
	 * Ctor
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
	public VelocityJavascriptContributor(Class clazz, String templatePath, IModel model,
			String id)
	{
		super(Packages.absolutePath(clazz, templatePath), model);
		this.id = id;
	}

	/**
	 * Ctor
	 * 
	 * Use this constructor when Velocity is configured with the
	 * FileResourceLoader. templatePath with then be relative to the loader path
	 * configured in velocity.properties
	 * 
	 * @param templatePath
	 * @param model
	 * @param id
	 */
	public VelocityJavascriptContributor(String templatePath, IModel model, String id)
	{
		super(templatePath, model);
		this.id = id;
	}

	/**
	 * @see wicket.markup.html.IHeaderContributor#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		String s = evaluate();
		if (null != s)
		{
			response.renderJavascript(s, id);
		}
	}
}
