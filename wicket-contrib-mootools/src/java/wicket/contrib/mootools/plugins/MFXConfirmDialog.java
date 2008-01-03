package wicket.contrib.mootools.plugins;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

public abstract class MFXConfirmDialog extends MFXDialog {
	public MFXConfirmDialog(final String id) {
		this(id, MFXDialogTypes.CONFIRMATION);
		setTitle("Notifcation");
		setBody("Confirm selection?");
	}

	protected MFXConfirmDialog(final String id, final MFXDialogTypes type) {
		super(id, type);
	}

	@Override
	protected void onConfirmCallback(final AjaxRequestTarget targ) {
		onConfirm(targ, getModel());
	}

	protected abstract void onConfirm(AjaxRequestTarget targ, IModel targetModel);

}
