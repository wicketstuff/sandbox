package contrib.wicket.cms.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import wicket.AttributeModifier;
import wicket.MarkupContainer;
import wicket.WicketRuntimeException;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.FormComponent;
import wicket.model.Model;
import wicket.model.StringResourceModel;

public class WicketUtil {

	private static transient Log log = LogFactory.getLog(WicketUtil.class);

	public static String DEFAULT_LABEL_INFO_CLASS = "labelInfo";

	public static String DEFAULT_LABEL_ERROR_CLASS = "labelError";

	public static String DEFAULT_FORM_COMPONENT_ERROR_CLASS = "formComponentError";

	public static String DEFAULT_FORM_COMPONENT_INFO_CLASS = "formComponentInfo";

	public static AttributeModifier addErrorClassAttributeModifier(
			final FormComponent conditionalComponent) {
		return addErrorClassAttributeModifier(conditionalComponent,
				DEFAULT_LABEL_INFO_CLASS, DEFAULT_LABEL_ERROR_CLASS);
	}

	public static AttributeModifier addErrorClassAttributeModifier(
			final FormComponent conditionalComponent,
			final String defaultClass, final String errorClass) {
		return new AttributeModifier("class", true, new Model()) {
			protected String newValue(String currentValue,
					String replacementValue) {
				if (conditionalComponent.isValid()) {
					return defaultClass;
				}
				return errorClass;
			}
		};
	}

	public static Label addErrorableLabel(MarkupContainer container,
			String name, FormComponent errorableFormComponent) {
		return addErrorableLabel(container, name, name, errorableFormComponent);
	}

	public static Label addErrorableLabel(MarkupContainer container,
			String errorableLabelName, String contentKey,
			FormComponent errorableFormComponent) {
		StringResourceModel labelModel = new StringResourceModel(contentKey,
				container, null);
		errorableFormComponent.setLabel(labelModel);
		Label label = new Label(container, errorableLabelName, labelModel);
		label.add(addErrorClassAttributeModifier(errorableFormComponent));
		errorableFormComponent.add(addErrorClassAttributeModifier(
				errorableFormComponent, DEFAULT_FORM_COMPONENT_INFO_CLASS,
				DEFAULT_FORM_COMPONENT_ERROR_CLASS));

		return label;
	}

	// public static void setResponseToAccountPageWithMessage(TemplatePage page,
	// String message) {
	// Page memberPage = page.newPage(page.getSecuritySession()
	// .getMemberPerspectiveAccountPage());
	// memberPage.get("infoMessages").info(message);
	//
	// page.setResponsePage(memberPage);
	// }

	public static void copyProperties(Object source, Object target)
			throws WicketRuntimeException {
		try {
			BeanUtils.copyProperties(source, target);
		} catch (Exception e) {
			log.error("copyProperties", e);
			throw new WicketRuntimeException(e);
		}
	}
}
