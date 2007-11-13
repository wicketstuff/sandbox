package wicket.contrib.mootools.plugins;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

public abstract class MFXConfirmDialog extends MFXDialog {
	private IModel targetModel;

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
		onConfirmDelete(targ, targetModel);
	}

	protected abstract void onConfirmDelete(AjaxRequestTarget targ, IModel targetModel);

	public void setTarget(final IModel targetModel) {
		this.targetModel = targetModel;
	}

}
