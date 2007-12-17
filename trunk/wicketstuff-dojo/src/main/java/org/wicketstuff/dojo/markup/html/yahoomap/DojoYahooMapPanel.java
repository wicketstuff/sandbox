package org.wicketstuff.dojo.markup.html.yahoomap;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;
import org.wicketstuff.dojo.IDojoWidget;

public class DojoYahooMapPanel extends Panel implements IDojoWidget {

	public static final String MAP_DATA = "mapData";
	public static final String DESCRIPTION = "description";
	
	public DojoYahooMapPanel(String id) {
		super(id);

		add(new DojoYahooMapPanelHandler());
		add(new ListView("mapDataListView", getDataMarkerList(DESCRIPTION)) {
			@Override
			protected void populateItem(ListItem item) {
				DojoMapData dojoMapData = (DojoMapData) item.getModelObject();
				item.add(new LabelForDouble("lat", new Model(dojoMapData.getLatitude())));
				item.add(new LabelForDouble("long", new Model(dojoMapData.getLongitude())));
				item.add(new Label("icon", dojoMapData.getIconUrl()));
				item.add(dojoMapData.getDescription());
			}
		});
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 * FIXME yahoomap hardcoded
	 */
	public String getDojoType()
	{
		return "yahoomap";
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		// TODO Auto-generated method stub
		super.onComponentTag(tag);
		tag.put("datasrc",MAP_DATA);
	}
	
	protected List<DojoMapData> getDataMarkerList(String descriptionId) {
		return Collections.EMPTY_LIST;
	}
	
	private class LabelForDouble extends Label {
		
		public LabelForDouble(final String id) {
			super(id);
		}

		public LabelForDouble(final String id, String label) {
			this(id, new Model(label));
		}

		public LabelForDouble(final String id, IModel model) {
			super(id, model);
		}	
		
		@Override
		public IConverter getConverter(Class type) {
			return getApplication().getConverterLocator().getConverter(String.class);
		}
	}

}
