package wicket.contrib.mootools.plugins;

import org.apache.wicket.IClusterable;
import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.mootools.IncludeMooTools;
import wicket.contrib.mootools.effects.MFXStyle;
import wicket.contrib.mootools.effects.MFXTransition;
import wicket.contrib.mootools.validators.MFXValidationHandler;

public class MFXDialog extends Panel {
	private static final long serialVersionUID = 1L;
	private PageCreator pageCreator;
	private int width;
	private int height;
	private String unit;
	private String title;
	private String confirmButtonText;
	private String abortButtonText;
	private int offsetTop;
	private Boolean shown;
	private String body;
	private int dialogType;
	private WebMarkupContainer empty;
	private Label titleLbl;
	private TextArea input;
	private String inputText;
	private Form form;
	private AjaxLink closeLink;
	private AjaxLink confirmLink;
	private AjaxSubmitLink submitLink;
	private AjaxLink abortLink;
	private WebMarkupContainer dialog;
	private String color;
	
	private ResourceReference PLAINCSS = new CompressedResourceReference(MFXDialog.class,"MFXDialog.css");
	
	
	public MFXDialog(String id) {
		super(id);
		
		add(new IncludeMooTools());
		
		//defaults
		this.width=300;
		this.height=0;
		this.dialogType=MFXDialogTypes.MESSAGE;
		this.offsetTop=300;
		this.unit="px";
		this.shown=false;
		this.title="Modal Window";
		this.confirmButtonText = "Ok";
		this.abortButtonText = "Cancel";
		this.color="#a3a3a3";
		this.setOutputMarkupId(true);
		
		
		dialog = new WebMarkupContainer("dialog") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("style", "border: 1px solid "+color+" !important;");
			}
		};
		dialog.setOutputMarkupId(true);
		add(dialog);
		
		add(HeaderContributor.forCss(PLAINCSS));
		
		dialog.add(titleLbl = new Label("title",title) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("style", "background-color: "+color+";");
			}
		});
		
		dialog.add(empty = new WebMarkupContainer("content"));
		
		empty.add(new Label("body",new PropertyModel(this,"body")));
		
		form = new Form("inputForm");
		input =  new TextArea("input",new PropertyModel(this,"inputText"));
		input.setRequired(true);
		input.add(new MFXValidationHandler("#ffffff","#fedede",true));
		form.add(input);
		empty.add(form);
		
		dialog.add(confirmLink = new AjaxLink("confirm") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget arg0) {
				shown = false;
				arg0.appendJavascript(closeWindowJavaScript());
				onAbortCallback(arg0);
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
	
	public interface PageCreator extends IClusterable {
		public Page createPage();
	}

	public void setPageCreator(PageCreator pageCreator) {
		this.pageCreator = pageCreator;
	}

	public PageCreator getPageCreator() {
		return pageCreator;
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
	
	private Page createPage() {
		
		if(pageCreator == null)
			return null;
		
		try {
			Page page = pageCreator.createPage();
			return page;
		} catch (Exception e) {
			return null;
		}
	}
	
	private String closeWindowJavaScript() {
		StringBuffer str = new StringBuffer();
		
		str.append("var elm = $('"+getMarkupId()+"');");
		str.append("elm.setStyle('display','none');");
		return str.toString();
	}
	
	private String openWindowJavaSript() {
		
		StringBuffer str = new StringBuffer();
		
		
		Page page = createPage();
		String url = null;
		if(page != null)
			url = RequestCycle.get().urlFor(page).toString();
		
		
		str.append("var elm = $('"+getMarkupId()+"');");
		str.append("var win = $('"+dialog.getMarkupId()+"');");
		
		str.append("elm.setStyle('display','block');");
		
		MFXStyle style = new MFXStyle("margin-top",0,100);
		
		style.setDuration(1000);
		style.setTransition(MFXTransition.backInOut);
		style.setTarget(dialog.getMarkupId());
		
		str.append("var effect = "+style.toString());
		
		if(getWidth() != 0)
			str.append("win.setStyle('width','"+getWidth()+""+getUnit()+"');");
		if(getHeight() != 0)
			str.append("win.setStyle('height','"+getHeight()+""+getUnit()+"');");
		
		str.append("var winw = window.getWidth();");
		str.append("var winh = window.getHeight();");
		
		str.append("win.setStyle('left',(winw-"+getWidth()+")/2);");
		str.append("win.setStyle('top',(winh-"+getHeight()+")/2-"+getOffsetTop()+");");
		
		if(url != null)
			str.append("new Ajax('"+url+"', { method: 'get', update: '"+empty.getMarkupId()+"', onComplete: function() {  effect.start("+style.getStartValue()+","+style.getEndValue()+"); } }).request();");
		else
			str.append("effect.start("+style.getStartValue()+","+style.getEndValue()+");");
		
		
		return str.toString();
	}

	// getters and setters below
	
	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setTitle(String title) {
		this.title = title;
		titleLbl.setModelObject(this.title);
	}

	public String getTitle() {
		return title;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}

	public void setOffsetTop(int offsetTop) {
		this.offsetTop = offsetTop;
	}

	public int getOffsetTop() {
		return offsetTop;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setDialogType(int dialogType) {
		this.dialogType = dialogType;
	}

	public int getDialogType() {
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


	public void setColor(String color) {
		this.color = color;
	}


	public String getColor() {
		return color;
	}
	
}
