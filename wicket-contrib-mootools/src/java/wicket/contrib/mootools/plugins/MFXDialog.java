package wicket.contrib.mootools.plugins;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.mootools.IncludeMooTools;
import wicket.contrib.mootools.validators.MFXValidationHandler;

public class MFXDialog extends MFXDialogBase {
	private static final long serialVersionUID = 1L;
	private String confirmButtonText;
	private String abortButtonText;
	private Boolean shown;
	private MFXDialogTypes dialogType;
	private TextArea input;
	private String inputText;
	private Form form;
	private AjaxLink closeLink;
	private AjaxLink confirmLink;
	private AjaxSubmitLink submitLink;
	private AjaxLink abortLink;
	private WebMarkupContainer dialog;
	private WebMarkupContainer contentPane;
	
	public static enum MFXDialogTypes { CONFIRMATION, MESSAGE, MESSAGE_WITHOUT_CLOSE, QUESTION_WITH_INPUT};
	
	public MFXDialog(String id) {
		super(id);
		
		add(new IncludeMooTools());
		
		//defaults
		this.dialogType=MFXDialogTypes.MESSAGE;
		this.shown=false;
		this.confirmButtonText = "Ok";
		this.abortButtonText = "Cancel";
		this.setOutputMarkupId(true);
		
		
		dialog=new WebMarkupContainer("dialog") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("style", "border: 1px solid "+getColor()+" !important;");
			}
		};
		dialog.setOutputMarkupId(true);
		add(dialog);
		
		dialog.add(new Label("title",new PropertyModel(this,"title")) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("style", "background-color: "+getColor()+";");
			}
		});
		
		dialog.add(contentPane = new WebMarkupContainer("content"));
		
		contentPane.add(new Label("body",new PropertyModel(this,"body")));
		
		form = new Form("inputForm");
		input =  new TextArea("input",new PropertyModel(this,"inputText"));
		input.setRequired(true);
		input.add(new MFXValidationHandler("#ffffff","#fedede",true));
		form.add(input);
		contentPane.add(form);
		
		dialog.add(confirmLink = new AjaxLink("confirm") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget arg0) {
				shown = false;
				arg0.appendJavascript(closeWindowJavaScript());
				onConfirmCallback(arg0);
				onCloseCallBack(arg0);
			}
		});
		
		dialog.add(abortLink = new AjaxLink("abort") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget arg0) {
				shown = false;
				arg0.appendJavascript(closeWindowJavaScript());
				onAbortCallback(arg0);
				onCloseCallBack(arg0);
			}
		});
		
		dialog.add(closeLink = new AjaxLink("close") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget arg0) {
				shown = false;
				arg0.appendJavascript(closeWindowJavaScript());
				onAbortCallback(arg0);
				onCloseCallBack(arg0);
			}
		});
		
		dialog.add(submitLink = new AjaxSubmitLink("submitLink",form) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form arg1) {
				shown = false;
				arg0.appendJavascript(closeWindowJavaScript());
				onConfirmCallback(arg0);
				onCloseCallBack(arg0);
			}
		});
		
		abortLink.add(new Label("abortText",new PropertyModel(this,"abortButtonText")));
		confirmLink.add(new Label("confirmText",new PropertyModel(this,"confirmButtonText")));
		closeLink.add(new Label("closeText",new PropertyModel(this,"confirmButtonText")));
		submitLink.add(new Label("submitText",new PropertyModel(this,"confirmButtonText")));
		
	}


	protected void onConfirmCallback(AjaxRequestTarget targ) {
	}
	
	protected void onAbortCallback(AjaxRequestTarget targ) {
	}
	
	protected void onCloseCallBack(AjaxRequestTarget targ) {
	}
	
	protected void onOpenCallBack(AjaxRequestTarget targ) {
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put("style","display: none;");
	}
	
	
	public void show(AjaxRequestTarget target) {
		if(shown == false) {
			target.addComponent(this);
			target.appendJavascript(openWindowJavaSript());
			onOpenCallBack(target);
			shown = true;
		}
	}
	
	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
	
		closeLink.setVisible(false);
		abortLink.setVisible(false);
		confirmLink.setVisible(false);
		submitLink.setVisible(false);
		form.setVisible(false);
		
		if(getDialogType() == MFXDialogTypes.MESSAGE)
			closeLink.setVisible(true);
		

		if(getDialogType() == MFXDialogTypes.CONFIRMATION) {
			abortLink.setVisible(true);
			confirmLink.setVisible(true);
		}
		
		if(getDialogType() == MFXDialogTypes.QUESTION_WITH_INPUT) {
			input.setVisible(true);
			form.setVisible(true);
			abortLink.setVisible(true);
			confirmLink.setVisible(false);
			submitLink.setVisible(true);
		}
	}
	
	
	private String closeWindowJavaScript() {
		return genericCloseWindowJavaScript(getMarkupId());
	}
	
	private String openWindowJavaSript() {
		return genericOpenJavaScript(
				getMarkupId(), // for the display: none
				dialog.getMarkupId(),  // for the dialog animation
				contentPane.getMarkupId() // for the content
		);
	}

	public void setDialogType(MFXDialogTypes dialogType) {
		this.dialogType = dialogType;
	}

	public MFXDialogTypes getDialogType() {
		return dialogType;
	}

	public void setConfirmButtonText(String confirmButtonText) {
		this.confirmButtonText = confirmButtonText;
	}

	public String getConfirmButtonText() {
		return confirmButtonText;
	}

	public void setAbortButtonText(String abortButtonText) {
		this.abortButtonText = abortButtonText;
	}

	public String getAbortButtonText() {
		return abortButtonText;
	}

	protected void setInputText(String inputText) {
		this.inputText = inputText;
	}

	protected String getInputText() {
		return inputText;
	}
	
}
