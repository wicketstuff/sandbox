package org.wicketstuff.pickwick.backend.panel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.backend.ImageMetadataReader;
import org.wicketstuff.pickwick.backend.Settings;

import com.google.inject.Inject;

public class MetadataPropertiesPanel extends Panel{
	
	Map<String, String> properties;
	
	@Inject
	private Settings settings;
	
	public MetadataPropertiesPanel(String id, String uri) {
		super(id);
		
		ImageMetadataReader reader = new ImageMetadataReader(settings.getImageDirectoryRoot() + "/" + uri);
		properties = reader.getMetadata();
		
		RefreshingView list     = new RefreshingView("list"){

			@Override
			protected void populateItem(Item item) {
				item.add(new Label("type", ((Property)item.getModelObject()).getKey()));
				item.add(new Label("value", ((Property)item.getModelObject()).getValue()));
			}

			@Override
			protected Iterator getItemModels() {
				List<Model> toReturn = new ArrayList<Model>();
				Iterator<Entry<String, String>> it = properties.entrySet().iterator();
				int i = 0;
				while(it.hasNext()){
					Entry<String, String> entry = it.next();
					toReturn.add(new Model(new Property(entry.getKey(), entry.getValue())));
				}
				return toReturn.iterator();
			}
			
		};
		add(list);
	}
	
	public class Property implements Serializable{
		String key;
		String value;
		public Property(String key, String value) {
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value){
			this.value= value;
		}
	}
}
