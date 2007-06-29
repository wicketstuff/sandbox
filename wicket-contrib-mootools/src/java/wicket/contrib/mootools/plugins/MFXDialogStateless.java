package wicket.contrib.mootools.plugins;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.mootools.IncludeMooToolsStateless;
import wicket.contrib.mootools.MFXMooBindable;

public class MFXDialogStateless extends MFXDialogBase implements MFXMooBindable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String contentId;
	private WebMarkupContainer dialog;
	private WebMarkupContainer contentPane;
	private MFXDialogStatelessTypes dialogType;
	private String closeButtonText;
	private WebMarkupContainer staticLessLink;
	
	private static enum MFXDialogStatelessTypes { MESSAGE_WITH_CLOSE , MESSAGE_WITHOUT_CLOSE };
	
	public MFXDialogStateless(final String id) {
		this(id,MFXDialogStatelessTypes.MESSAGE_WITH_CLOSE);
	}
	
	public MFXDialogStateless(final String id,MFXDialogStatelessTypes type) {
		super(id);
		add(new IncludeMooToolsStateless());
		
		this.id = id;
		this.contentId = id+"0";
		this.dialogType = type;
		this.closeButtonText = "Close";
		
		dialog= new WebMarkupContainer("dialog") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("style", "border: 1px solid "+getColor()+" !important;");
				tag.put("id",id);
			}
		};
		add(dialog);
		
		dialog.add(new Label("title",new PropertyModel(this,"title")) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("style", "background-color: "+getColor()+";");
			}
		});
		
		contentPane=new WebMarkupContainer("content") { 
			private static final long serialVersionUID = 1L;
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("id", contentId);
			}
		};
		dialog.add(contentPane);
		
		
		dialog.add(staticLessLink = new WebMarkupContainer("statelessLink") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				tag.put("href", "javascript:"+closeWindowJavaScript());
			}
		});
		staticLessLink.add(new Label("closeText",new PropertyModel(this,"closeButtonText")));
		contentPane.add(new Label("body",new PropertyModel(this,"body")));
	}
	
	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		
		if(getDialogType() == MFXDialogStatelessTypes.MESSAGE_WITHOUT_CLOSE)
			staticLessLink.setVisible(false);
	}
	
	private String closeWindowJavaScript() {
		return genericCloseWindowJavaScript(id);
	}
	
	public String mooFunction() {
		return openWindowJavaSript();
	}
	
	private String openWindowJavaSript() {
		return genericOpenJavaScript(id,contentId);
	}

	public void setDialogType(MFXDialogStatelessTypes dialogType) {
		this.dialogType = dialogType;
	}

	public MFXDialogStatelessTypes getDialogType() {
		return dialogType;
	}

	public void setCloseButtonText(String closeButtonText) {
		this.closeButtonText = closeButtonText;
	}

	public String getCloseButtonText() {
		return closeButtonText;
	}

}
