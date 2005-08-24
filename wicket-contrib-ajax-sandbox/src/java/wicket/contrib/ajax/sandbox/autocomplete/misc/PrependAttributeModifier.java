package wicket.contrib.ajax.sandbox.autocomplete.misc;

import wicket.AttributeModifier;
import wicket.model.IModel;

public class PrependAttributeModifier extends AttributeModifier {
	
	public PrependAttributeModifier(String attribute, boolean addAttributeIfNotPresent, IModel replaceModel) {
		super(attribute, addAttributeIfNotPresent, replaceModel);
	}

	public PrependAttributeModifier(String attribute, IModel replaceModel) {
		super(attribute, replaceModel);
	}

	public PrependAttributeModifier(String attribute, String pattern, boolean addAttributeIfNotPresent, IModel replaceModel) {
		super(attribute, pattern, addAttributeIfNotPresent, replaceModel);
	}

	public PrependAttributeModifier(String attribute, String pattern, IModel replaceModel) {
		super(attribute, pattern, replaceModel);
	}

	protected String newValue(String currentValue, String replacementValue) {
		// force the border to be 0px so that table border looks like the textfield border
		return replacementValue + (currentValue==null?"":currentValue);
	}

}
