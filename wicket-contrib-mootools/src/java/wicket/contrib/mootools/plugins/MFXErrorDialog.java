package wicket.contrib.mootools.plugins;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;

public class MFXErrorDialog extends MFXDialog {
	private static final long serialVersionUID = 1L;
	private StringBuffer error = new StringBuffer();
	private FeedbackMessagesModel feedbackModel;

	public MFXErrorDialog(final String id, final CSSCOLOR color) {
		super(id, color, MFXDialogTypes.MESSAGE);
		setTitle("Notice");
		setBody("");
		setFeedbackComponent(this);
	}

	public MFXErrorDialog(final String id) {
		this(id, CSSCOLOR.BLUE);
	}

	@Override
	public void show(final AjaxRequestTarget target) {
		for (FeedbackMessage msg : getFeedbackMessages()) {
			appendError(msg.getMessage().toString());
		}
		super.show(target);
	}

	public void appendError(final Exception e) {
		appendError(e.getMessage());
	}

	@Override
	protected void onCloseCallBack(final AjaxRequestTarget targ) {
		super.onCloseCallBack(targ);
		clearErrors();
	}

	public void appendError(final String error) {
		this.error.append("<li>" + error + "</li>");
		setBody("<ul>" + this.error.toString() + "</ul>");
	}

	protected List<FeedbackMessage> getFeedbackMessages() {
		return (List) feedbackModel.getObject();
	}

	protected void setFeedbackComponent(final Component c) {
		feedbackModel = new FeedbackMessagesModel(c);
	}

	public Boolean hasErrors() {
		if (this.error.length() > 0 || getFeedbackMessages().size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void clearErrors() {
		this.error = new StringBuffer();
		setBody("");
	}
}
