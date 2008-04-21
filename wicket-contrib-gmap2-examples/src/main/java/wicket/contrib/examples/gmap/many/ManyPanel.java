package wicket.contrib.examples.gmap.many;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GMapType;

public abstract class ManyPanel extends Panel<Object>
{

	final GMap2<Object> gMap;

	private WebMarkupContainer<Object> n, ne, e, se, s, sw, w, nw;

	public ManyPanel(String id, String gMapKey)
	{
		super(id);
		gMap = new GMap2<Object>("gMap", gMapKey);
		gMap.setZoom(7);
		gMap.setOutputMarkupId(true);
		add(gMap);
		final AjaxFallbackLink<Object> normal = new AjaxFallbackLink<Object>("normal")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				gMap.setMapType(GMapType.G_NORMAL_MAP);
			}
		};
		add(normal);
		final AjaxFallbackLink<Object> satellite = new AjaxFallbackLink<Object>("satellite")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				gMap.setMapType(GMapType.G_SATELLITE_MAP);
			}
		};
		add(satellite);
		final AjaxFallbackLink<Object> hybrid = new AjaxFallbackLink<Object>("hybrid")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				gMap.setMapType(GMapType.G_HYBRID_MAP);
			}
		};
		add(hybrid);
		n = new WebMarkupContainer<Object>("n");
		add(n);
		ne = new WebMarkupContainer<Object>("ne");
		add(ne);
		e = new WebMarkupContainer<Object>("e");
		add(e);
		se = new WebMarkupContainer<Object>("se");
		add(se);
		s = new WebMarkupContainer<Object>("s");
		add(s);
		sw = new WebMarkupContainer<Object>("sw");
		add(sw);
		w = new WebMarkupContainer<Object>("w");
		add(w);
		nw = new WebMarkupContainer<Object>("nw");
		add(nw);

		n.add(gMap.new PanDirectionBehavior("onclick", 0, 1));
		ne.add(gMap.new PanDirectionBehavior("onclick", -1, 1));
		e.add(gMap.new PanDirectionBehavior("onclick", -1, 0));
		se.add(gMap.new PanDirectionBehavior("onclick", -1, -1));
		s.add(gMap.new PanDirectionBehavior("onclick", 0, -1));
		sw.add(gMap.new PanDirectionBehavior("onclick", 1, -1));
		w.add(gMap.new PanDirectionBehavior("onclick", 1, 0));
		nw.add(gMap.new PanDirectionBehavior("onclick", 1, 1));

		AjaxFallbackLink<Object> close = new AjaxFallbackLink<Object>("close")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				closing(target);
			}
		};
		add(close);
	}

	protected abstract void closing(AjaxRequestTarget target);
}
