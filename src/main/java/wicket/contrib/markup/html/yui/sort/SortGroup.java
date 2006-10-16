package wicket.contrib.markup.html.yui.sort;

import java.util.Map;

import wicket.Component;
import wicket.extensions.util.resource.PackagedTextTemplate;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.model.AbstractReadOnlyModel;
import wicket.util.collections.MiniMap;

public class SortGroup extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;
	private String javaScriptId;
	private String mode;
	
	public SortGroup(String id, SortSettings settings){
		super(id);
		this.mode= settings.getMode();
		
		Label initialization = new Label("init", new AbstractReadOnlyModel(){
			private static final long serialVersionUID=1L;
			public Object getObject(Component component){
				return getJavaScriptComponentInitializationScript();
			}
		});
		initialization.setEscapeModelStrings(false);
		add(initialization);
	}

	protected String getJavaScriptComponentInitializationScript(){
		PackagedTextTemplate template = new PackagedTextTemplate(SortGroup.class, "init.js");
		Map variables = new MiniMap(2);
		variables.put("javaScriptId", javaScriptId);
		variables.put("mode", "YAHOO.util.DDM."+mode);
		template.interpolate(variables);
		return template.getString();
	}

	protected void onAttach(){
		super.onAttach();
		javaScriptId= getMarkupId();
	}
}

