/**
 * 
 */
package contrib.wicket.cms.validator;

import java.util.Map;

import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.validation.StringValidator;

public class EqualsValidator extends StringValidator {

	FormComponent dependentFormComponent;

	public EqualsValidator(FormComponent dependentFormComponent) {
		this.dependentFormComponent = dependentFormComponent;
	}

	@Override
	public void onValidate(FormComponent formComponent, String value) {
		if (value != null && !value.equals(dependentFormComponent.getValue())) {
			Map messageModel = messageModel(formComponent);
			messageModel.put("dependentLabel", dependentFormComponent
					.getLabel().toString());
			error(formComponent, resourceKey(formComponent), messageModel);
		}
	}
}