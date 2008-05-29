package wicket.contrib.examples.gmap.listen;

import java.util.Locale;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.IConverter;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GLatLngBounds;
import wicket.contrib.gmap.event.LoadListener;
import wicket.contrib.gmap.event.MoveEndListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage<Void>
{

	private static final long serialVersionUID = 1L;

	private final Label<Integer> zoomLabel;

	private final MultiLineLabel<GLatLngBounds> boundsLabel;

	private MoveEndListener moveEndBehavior;

	public HomePage()
	{
		final GMap2<Object> map = new GMap2<Object>("map", GMapExampleApplication.get()
				.getGoogleMapsAPIkey());
		map.addControl(GControl.GLargeMapControl);
		add(map);
		moveEndBehavior = new MyMoveEndListener();
		map.add(moveEndBehavior);
		map.add(new LoadListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onLoad(AjaxRequestTarget target)
			{
				target.addComponent(boundsLabel);
			}
		});

		zoomLabel = new Label<Integer>("zoom", new PropertyModel<Integer>(map, "zoom"));
		zoomLabel.setOutputMarkupId(true);
		add(zoomLabel);

		boundsLabel = new MultiLineLabel<GLatLngBounds>("bounds", new PropertyModel<GLatLngBounds>(
				map, "bounds"))
		{
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public <X> IConverter<X> getConverter(Class<X> type)
			{
				if (GLatLngBounds.class.isAssignableFrom(type))
				{
					return (IConverter<X>)new IConverter<GLatLngBounds>()
					{
						private static final long serialVersionUID = 1L;

						public GLatLngBounds convertToObject(String value, Locale locale)
						{
							throw new UnsupportedOperationException();
						}

						public String convertToString(GLatLngBounds value, Locale locale)
						{
							GLatLngBounds bounds = value;

							StringBuffer buffer = new StringBuffer();
							buffer.append("NE (");
							buffer.append(bounds.getNE().getLat());
							buffer.append(",");
							buffer.append(bounds.getNE().getLng());
							buffer.append(")\nSW (");
							buffer.append(bounds.getSW().getLat());
							buffer.append(",");
							buffer.append(bounds.getSW().getLng());
							buffer.append(")");
							return buffer.toString();
						}
					};
				}
				else
				{
					return super.getConverter(type);
				}
			}

		};
		boundsLabel.setOutputMarkupId(true);
		add(boundsLabel);
		final Label<Boolean> enabledLabel = new Label<Boolean>("enabled", new Model<Boolean>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public Boolean getObject()
			{
				return map.getBehaviors().contains(moveEndBehavior);
			}
		});
		enabledLabel.add(new AjaxEventBehavior("onclick")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				if (map.getBehaviors().contains(moveEndBehavior))
				{
					map.remove(moveEndBehavior);
				}
				else
				{
					// TODO AbstractAjaxBehaviors are not reusable, so
					// we have
					// to recreate:
					// https://issues.apache.org/jira/browse/WICKET-713
					moveEndBehavior = new MyMoveEndListener();
					map.add(moveEndBehavior);
				}
				target.addComponent(map);
				target.addComponent(enabledLabel);
			}
		});
		add(enabledLabel);
	}

	private class MyMoveEndListener extends MoveEndListener
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void onMoveEnd(AjaxRequestTarget target)
		{
			target.addComponent(zoomLabel);
			target.addComponent(boundsLabel);
		}
	};
}
