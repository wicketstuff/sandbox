package wicket.contrib.markup.html.velocity;

import java.util.HashMap;

import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.model.Model;
import wicket.util.resource.UrlResourceStream;

/**
 * Test page for <code>VelocityPanel</code>
 * 
 * @see wicket.contrib.markup.html.velocity.VelocityPanel
 */
public class VelocityWithMarkupParsingPage extends WebPage
{
	/**
	 * Adds a VelocityPanel to the page with markup parsing
	 */
	public VelocityWithMarkupParsingPage()
	{
		HashMap values = new HashMap();
		values.put("labelId", "message");
		VelocityPanel velocityPanel = new VelocityPanel("velocityPanel",
				new UrlResourceStream(this.getClass().getResource("testWithMarkup.html")), new Model(values));
		velocityPanel.setParseGeneratedMarkup(true);
		velocityPanel.add(new Label("message", VelocityPage.TEST_STRING));
		add(velocityPanel);
	}
}
