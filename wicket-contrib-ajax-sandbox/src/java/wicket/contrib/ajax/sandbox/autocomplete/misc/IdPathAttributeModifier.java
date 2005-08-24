package wicket.contrib.ajax.sandbox.autocomplete.misc;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.model.Model;

/**
 * binds an attribute modifier to a component that replaced the ID attribute with the component's path
 * @author CameronBraid
 */
public class IdPathAttributeModifier {
		
	public static void bind(final Component component) {
		component.add(new AttributeModifier("id", true, new Model("")) {
			protected String newValue(String currentValue, String replacementValue) {
				// to this at render time, incase the component has not been added to its parent when this attribute modifier is created 
				return component.getPath();
			};
		});
	}
	
}
