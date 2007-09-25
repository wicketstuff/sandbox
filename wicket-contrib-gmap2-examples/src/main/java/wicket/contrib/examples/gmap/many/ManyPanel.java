package wicket.contrib.examples.gmap.many;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.GMap2.PanDirection;
import wicket.contrib.gmap.api.GMapType;

public class ManyPanel extends Panel {

	final GMap2 gMap;
	
	public ManyPanel(String id, String gMapKey) {
		super(id);
		gMap = new GMap2("gMap", gMapKey);
		gMap.setOutputMarkupId(true);
		add(gMap);
		final AjaxFallbackLink normal = new AjaxFallbackLink("normal"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				gMap.setMapType(GMapType.G_NORMAL_MAP);
			}
		};
		add(normal);
		final AjaxFallbackLink satellite = new AjaxFallbackLink("satellite"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				gMap.setMapType(GMapType.G_SATELLITE_MAP);
			}
		};
		add(satellite);
		final AjaxFallbackLink hybrid = new AjaxFallbackLink("hybrid"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				gMap.setMapType(GMapType.G_HYBRID_MAP);
			}
		};
		add(hybrid);
	}

	@Override
	protected void onBeforeRender() {
//		final Label n = new Label("n", "N");
//		n.add(gMap.new PanDirection("onclick", 0, 1));
//		add(n);
//
//		final Label ne = new Label("ne", "NE");
//		ne.add(gMap.new PanDirection("onclick", -1, 1));
//		add(ne);
//
//		final Label e = new Label("e", "E");
//		e.add(gMap.new PanDirection("onclick", -1, 0));
//		add(e);
//
//		final Label se = new Label("se", "SE");
//		se.add(gMap.new PanDirection("onclick", -1, -1));
//		add(se);
//
//		final Label s = new Label("s", "S");
//		s.add(gMap.new PanDirection("onclick", 0, -1));
//		add(s);
//
//		final Label sw = new Label("sw", "SW");
//		sw.add(gMap.new PanDirection("onclick", 1, -1));
//		add(sw);
//
//		final Label w = new Label("w", "W");
//		w.add(gMap.new PanDirection("onclick", 1, 0));
//		add(w);
//
//		final Label nw = new Label("nw", "NW");
//		nw.add(gMap.new PanDirection("onclick", 1, 1));
//		add(nw);
		super.onBeforeRender();
	}
}
