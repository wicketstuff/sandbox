package wicket.contrib.examples.gmap.many;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GMapType;

public abstract class ManyPanel extends Panel {

	final GMap2 gMap;

	private Label n, ne, e, se, s, sw, w, nw;

	public ManyPanel(String id, String gMapKey) {
		super(id);
		gMap = new GMap2("gMap", gMapKey);
		gMap.setOutputMarkupId(true);
		add(gMap);
		final AjaxFallbackLink normal = new AjaxFallbackLink("normal") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				gMap.setMapType(GMapType.G_NORMAL_MAP);
			}
		};
		add(normal);
		final AjaxFallbackLink satellite = new AjaxFallbackLink("satellite") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				gMap.setMapType(GMapType.G_SATELLITE_MAP);
			}
		};
		add(satellite);
		final AjaxFallbackLink hybrid = new AjaxFallbackLink("hybrid") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				gMap.setMapType(GMapType.G_HYBRID_MAP);
			}
		};
		add(hybrid);
		n = new Label("n", "N");
		add(n);
		ne = new Label("ne", "NE");
		add(ne);
		e = new Label("e", "E");
		add(e);
		se = new Label("se", "SE");
		add(se);
		s = new Label("s", "S");
		add(s);
		sw = new Label("sw", "SW");
		add(sw);
		w = new Label("w", "W");
		add(w);
		nw = new Label("nw", "NW");
		add(nw);

		n.add(gMap.new PanDirection("onclick", 0, 1));
		ne.add(gMap.new PanDirection("onclick", -1, 1));
		e.add(gMap.new PanDirection("onclick", -1, 0));
		se.add(gMap.new PanDirection("onclick", -1, -1));
		s.add(gMap.new PanDirection("onclick", 0, -1));
		sw.add(gMap.new PanDirection("onclick", 1, -1));
		w.add(gMap.new PanDirection("onclick", 1, 0));
		nw.add(gMap.new PanDirection("onclick", 1, 1));
		
		AjaxFallbackLink close = new AjaxFallbackLink("close") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				closing(target);
			}
		};
		add(close);		
	}
	
	protected abstract void closing(AjaxRequestTarget target);
}
