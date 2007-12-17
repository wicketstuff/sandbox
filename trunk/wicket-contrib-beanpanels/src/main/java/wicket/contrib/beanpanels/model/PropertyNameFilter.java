package wicket.contrib.beanpanels.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PropertyNameFilter implements IPropertyFilter {

		int c = 0;
		private List names;
		
		PropertyNameFilter( String ... names ) { 
			this.names = names != null ? Arrays.asList(names) : new ArrayList();
		}
		
		PropertyNameFilter( List names ) { 
			this.names = names;
		}
		
		public int accept(Field field) {
			return names.indexOf(field.getName());
		};
		
}
