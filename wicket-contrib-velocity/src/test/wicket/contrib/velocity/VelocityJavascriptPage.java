package wicket.contrib.velocity;

import java.util.Map;

import wicket.contrib.util.resource.VelocityJavascriptContributor;
import wicket.markup.html.WebPage;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.util.collections.MiniMap;
import wicket.util.lang.Packages;
import wicket.util.string.JavascriptUtils;

/**
 * Test page.
 */
public class VelocityJavascriptPage extends WebPage
{
	static final String MSG1 = "Stoopid test 1";

	/**
	 * Construct.
	 */
	public VelocityJavascriptPage()
	{
		String templateName = Packages.absolutePath(this.getClass(), "testTemplate.vm");

		String id = "000001";
		String javascript = "msg1: Stoopid test 1\nmsg2: Stooopid test 2";
		JavascriptUtils.writeJavascript(getResponse(), javascript, id);

		IModel model = new Model()
		{
			public Object getObject()
			{
				Map map = new MiniMap(2);
				map.put("msg1", MSG1);
				map.put("msg2", "Stooopid test 2");
				return map;
			}

		};

		add(new VelocityJavascriptContributor(templateName, model, id));
	}
}
