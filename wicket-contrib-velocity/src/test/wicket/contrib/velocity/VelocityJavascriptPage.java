package wicket.contrib.velocity;

import java.util.Map;

import wicket.contrib.util.resource.VelocityJavascriptContributor;
import wicket.markup.html.WebPage;
import wicket.markup.html.internal.HeaderContainer;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.util.collections.MiniMap;
import wicket.util.lang.Packages;
import wicket.util.string.JavascriptUtils;

public class VelocityJavascriptPage extends WebPage
{
	public static final String MSG1 = "Stoopid test 1";
	public VelocityJavascriptPage()
	{
		String templateName = Packages.absolutePath(this.getClass(), "testTemplate.vm");
		
		String id = "000001";
		String javascript = "msg1: Stoopid test 1\nmsg2: Stooopid test 2";
		JavascriptUtils.writeJavascript(getResponse(), javascript, id);
		
		IModel<Map<String, Object>> model = new Model<Map<String, Object>>() {

			public Map<String, Object> getObject()
			{
				Map<String, Object> map = new MiniMap<String,Object>(2);
				map.put("msg1", "Stoopid test 1");
				map.put("msg2", "Stooopid test 2");
				return map;
			}
			
		};

		add(new VelocityJavascriptContributor<String, Object>(templateName, model, id));
	}
}
