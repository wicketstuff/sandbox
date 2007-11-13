package wicket.contrib.mootools.plugins;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.mootools.IncludeMooTools;

public class MFXDialog extends MFXDialogBase {
	private static final long serialVersionUID = 1L;
	private String confirmButtonText;
	private String abortButtonText;
	private Boolean shown;
	private MFXDialogTypes dialogType;
	private AjaxLink confirmLink;
	private AjaxLink abortLink;
	private WebMarkupContainer dialog;
	private WebMarkupContainer contentPane;

	public static enum MFXDialogTypes {
		CONFIRMATION, MESSAGE
	};

	public MFXDialog(final String id) {
		this(id, MFXDialogTypes.MESSAGE);
	}

	public MFXDialog(final String id, final MFXDialogTypes type) {
		super(id);

		add(new IncludeMooTools());

		// defaults
		this.dialogType = type;
		this.shown = false;
		this.confirmButtonText = "Confirm";
		this.abortButtonText = "Cancel";
		this.setOutputMarkupId(true);

		dialog = new WebMarkupContainer("dialog");
		dialog.setOutputMarkupId(true);
		add(dialog);

		dialog.add(new Label("title", new PropertyModel(this, "title")));

		dialog.add(contentPane = new WebMarkupContainer("content"));

		contentPane.add(new Label("body", new PropertyModel(this, "body")).setEscapeModelStrings(false));

		dialog.add(confirmLink = new AjaxLink("confirm") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(final AjaxRequestTarget arg0) {
				shown = false;
				arg0.appendJavascript(closeWindowJavaScript());
				onConfirmCallback(arg0);
				onCloseCallBack(arg0);
			}
		});

		dialog.add(abortLink = new AjaxLink("abort") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(final AjaxRequestTarget arg0) {
				shown = false;
				arg0.appendJavascript(closeWindowJavaScript());
				onAbortCallback(arg0);
				onCloseCallBack(arg0);
			}
		});

		abortLink.add(new Label("abortText", new PropertyModel(this, "abortButtonText")));
		confirmLink.add(new Label("confirmText", new PropertyModel(this, "confirmButtonText")));
	}

	protected void onConfirmCallback(final AjaxRequestTarget targ) {
	}

	protected void onAbortCallback(final AjaxRequestTarget targ) {
	}

	protected void onCloseCallBack(final AjaxRequestTarget targ) {
	}

	protected void onOpenCallBack(final AjaxRequestTarget targ) {
	}

	@Override
	protected void onComponentTag(final ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put("style", "display: none;");
	}

	public void show(final AjaxRequestTarget target) {
		if (shown == false) {
			target.addComponent(this);
			target.appendJavascript(openWindowJavaSript());
			onOpenCallBack(target);
			shown = true;
		}
	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();

		abortLink.setVisible(false);
		confirmLink.setVisible(false);

		if (getDialogType() == MFXDialogTypes.CONFIRMATION) {
			abortLink.setVisible(true);
			confirmLink.setVisible(true);
		}

	}

	private String closeWindowJavaScript() {
		return genericCloseWindowJavaScript(getMarkupId());
	}

	private String openWindowJavaSript() {
		return genericOpenJavaScript(getMarkupId(), // for the display: none
				dialog.getMarkupId(), // for the dialog animation
				contentPane.getMarkupId() // for the content
		);
	}

	public void setDialogType(final MFXDialogTypes dialogType) {
		this.dialogType = dialogType;
	}

	public MFXDialogTypes getDialogType() {
		return dialogType;
	}

	public void setConfirmButtonText(final String confirmButtonText) {
		this.confirmButtonText = confirmButtonText;
	}

	public String getConfirmButtonText() {
		return confirmButtonText;
	}

	public void setAbortButtonText(final String abortButtonText) {
		this.abortButtonText = abortButtonText;
	}

	public String getAbortButtonText() {
		return abortButtonText;
	}
}
