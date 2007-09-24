package wicket.contrib.examples.gmap.many;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GMapType;

public class ManyPanel extends Panel {

	public ManyPanel(String id, String gMapKey) {
		super(id);
		final GMap2 gMap = new GMap2("gMap", gMapKey);
		gMap.setOutputMarkupId(true);
		add(gMap);
	}
}
