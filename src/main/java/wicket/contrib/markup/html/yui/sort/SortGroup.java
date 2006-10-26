package wicket.contrib.markup.html.yui.sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.behavior.StringHeaderContributor;
import wicket.contrib.YuiImage;
import wicket.extensions.util.resource.PackagedTextTemplate;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.form.FormComponent;
import wicket.model.AbstractReadOnlyModel;

public class SortGroup extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;

	private String javaScriptId;

	private String mode;
	
	private List<YuiImage> sortList;
	
	private String id;
	
	private String valueId;

	public SortGroup(String id, SortSettings settings, final FormComponent element) {
		super(id);
		this.mode = settings.getMode();
		this.sortList = settings.getSortList();
		this.id= id;
		this.valueId = element.getId()+"_"+id;
		
		if (element != null) {
			element.add(new AttributeModifier("id", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						public Object getObject(Component component) {
							return element.getId()+"_"+javaScriptId;
						}
					}));
		}
		add(element);
	}

	protected String getJavaScriptComponentInitializationScript() {
		String sortValues = "";
		String sortIds = "";
		
		for(int i=0; i<sortList.size(); i++){
			YuiImage yuiImage = (YuiImage)sortList.get(i);
			if(sortValues.equals("") || sortValues == ""){
				sortValues = "'"+ yuiImage.getDesc() +"'";
				sortIds = "'dd"+i+"_"+id+"'";
			}
			else{
				sortValues = sortValues +", '"+ yuiImage.getDesc()+"'";
				sortIds = sortIds + ", 'dd"+i+"_"+id+"'";
			}
		}
		PackagedTextTemplate template = new PackagedTextTemplate(
				SortGroup.class, "init.js");
		Map<String, Object> variables = new HashMap<String, Object>(4);
		variables.put("javaScriptId", javaScriptId);
		variables.put("mode", "YAHOO.util.DDM." + mode);
		variables.put("sortValues", sortValues);
		variables.put("sortIds", sortIds);
		variables.put("valueId", "'" + valueId + "'");
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
