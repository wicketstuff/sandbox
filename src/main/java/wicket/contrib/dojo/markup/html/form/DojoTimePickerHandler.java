package wicket.contrib.dojo.markup.html.form;

import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;

/**
 * Handler for {@link DojoTimePicker}
 *
 */
public class DojoTimePickerHandler extends AbstractRequireDojoBehavior {

	public void setRequire(RequireDojoLibs libs) {
		libs.add("dojo.widget.DropdownTimePicker");
	}

	protected void respond(AjaxRequestTarget target) {
		// DO NOTHING
	}

}
