package wicket.contrib.markup.html.velocity;

import java.util.HashMap;

import wicket.markup.html.WebPage;
import wicket.model.Model;
import wicket.util.resource.UrlResourceStream;

/**
 * Test page for <code>VelocityPanel</code>
 * 
 * @see wicket.contrib.markup.html.velocity.VelocityPanel
 */
public class VelocityPage extends WebPage
{
	protected static final String TEST_STRING = "Hello, World!";

	/**
	 * Adds a VelocityPanel to the page
	 */
	public VelocityPage()
	{
		HashMap values = new HashMap();
		values.put("message", TEST_STRING);
		add(new VelocityPanel("velocityPanel", new UrlResourceStream(this.getClass().getResource("test.html")), new Model(values)));
	}
}
