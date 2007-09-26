package wicket.contrib.mootools.plugins;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import wicket.contrib.mootools.plugins.MFXDialog;

public abstract class MFXMultiConfirmDialog extends MFXDialog {
	private static final long serialVersionUID = 1L;
	private List<IModel> targets = new ArrayList<IModel>();

	@Override
	public String getTitle() {
		return "Notification";
	}

	@Override
	public String getBody() {
		return "Confirm selection?";
	}

	public MFXMultiConfirmDialog(final String id) {
		super(id);
		setDialogType(MFXDialogTypes.CONFIRMATION);
	}

	@Override
	protected void onConfirmCallback(final AjaxRequestTarget targ) {
		onConfirmDeleteItems(targ, getTargets());
	}

	protected abstract void onConfirmDeleteItems(AjaxRequestTarget targ,
			List<IModel> targets);

	public List<IModel> getTargets() {
		return this.targets;
	}

	public void setTargets(final List<IModel> models) {
		this.targets = models;
	}
}
