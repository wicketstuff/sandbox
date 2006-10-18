package wicket.contrib.markup.html.yui.sort;

import java.util.HashMap;
import java.util.Map;

import wicket.behavior.StringHeaderContributor;
import wicket.extensions.util.resource.PackagedTextTemplate;
import wicket.markup.html.WebMarkupContainer;

public class SortGroup extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;

	private String javaScriptId;

	private String mode;

	public SortGroup(String id, SortSettings settings) {
		super(id);
		this.mode = settings.getMode();
	}

	protected String getJavaScriptComponentInitializationScript() {
		PackagedTextTemplate template = new PackagedTextTemplate(
				SortGroup.class, "init.js");
		Map<String, Object> variables = new HashMap<String, Object>(2);
		variables.put("javaScriptId", javaScriptId);
		variables.put("mode", "YAHOO.util.DDM." + mode);
		template.interpolate(variables);
		return template.getString();
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		javaScriptId = getMarkupId();
		
		String js= "\n<script type=\"text/javascript\">" + getJavaScriptComponentInitializationScript() + "\n</script>\n";
		add(new StringHeaderContributor(js));
	}
}
