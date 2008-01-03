package wicket.contrib.mootools.plugins;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

public abstract class MFXMultiConfirmDialog extends MFXDialog {
	private static final long serialVersionUID = 1L;
	private List<IModel> targets = new ArrayList<IModel>();

	public MFXMultiConfirmDialog(final String id) {
		super(id);
		setDialogType(MFXDialogTypes.CONFIRMATION);
		setBody("Confirm selection?");
		setTitle("Notification");
	}

	@Override
	protected void onConfirmCallback(final AjaxRequestTarget targ) {
		onConfirm(targ, getTargets());
	}

	protected abstract void onConfirm(AjaxRequestTarget targ, List<IModel> targets);

	public List<IModel> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<IModel> models) {
		this.targets = models;
	}
}
