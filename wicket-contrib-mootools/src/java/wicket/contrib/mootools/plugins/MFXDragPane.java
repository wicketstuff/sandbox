package wicket.contrib.mootools.plugins;

import org.apache.wicket.IClusterable;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.resources.CompressedResourceReference;
import org.apache.wicket.model.PropertyModel;


import wicket.contrib.mootools.IncludeMooToolsStateless;

public abstract class MFXDragPane extends Panel {
	private String title;
	private String width;
	private MFXDragPaneCallbacks mfxDropCallback;
	
	private static final long serialVersionUID = 1L;
	private final static String AJAXPANE = "ajaxPane";
	private CompressedResourceReference DRAGPANE_JS = 
		new CompressedResourceReference(MFXDragPane.class,"MFXDragPane.js");
	private CompressedResourceReference DRAGPANE_CSS = 
		new CompressedResourceReference(MFXDragPane.class,"MFXDragPane.css");
	
	public MFXDragPane(String id) {
		super(id);
		
		setOutputMarkupId(true);
		
		setTitle("MFXDragPane");
		setWidth("250px");
		
		add(new IncludeMooToolsStateless());
		add(HeaderContributor.forCss(DRAGPANE_CSS));
		add(HeaderContributor.forJavaScript(DRAGPANE_JS));
		
		
		add(new AjaxPane(AJAXPANE));
	}
	
	public MFXDragPane setMFXDropCallBack(MFXDragPaneCallbacks callback) {
		this.mfxDropCallback = callback;
		return this;
	}
	
	public static interface MFXDragPaneCallbacks extends IClusterable {
		public void onDropCallback(AjaxRequestTarget target,int column);
		public void onCloseCallback(AjaxRequestTarget target,int column);
		public void onMinimizeCallback(AjaxRequestTarget target,int column);
		public void onMaximizedCallback(AjaxRequestTarget target,int column);
	}
	
	protected class AjaxSetBehavior extends AbstractDefaultAjaxBehavior {
		private static final long serialVersionUID = 1L;

		public String getCallbackScript() {
			//var wcall=wicketAjaxGet('&wicket:ignoreIfNotActive=true', function() { }.bind(this), function() { }.bind(this));
			//return "var wcall=wicketAjaxGet('"+getCallbackUrl()+"";
			return getCallbackScript(true).toString();
		}
		
		@Override
		protected void respond(AjaxRequestTarget arg0) {
			RequestCycle requestCycle = RequestCycle.get();
			
			if(mfxDropCallback != null) {
				String callback = requestCycle.getRequest().getParameter("callback");
				int column  = Integer.valueOf(requestCycle.getRequest().getParameter("column"));
				
				if(callback.equals("close"))
					mfxDropCallback.onCloseCallback(arg0, column);
				
				if(callback.equals("drop"))
					mfxDropCallback.onDropCallback(arg0, column);
				
				if(callback.equals("minimize"))
					mfxDropCallback.onMinimizeCallback(arg0, column);
				
				if(callback.equals("maximized"))
					mfxDropCallback.onMaximizedCallback(arg0, column);
			}
		}
	}
	
	protected class AjaxPane extends WebMarkupContainer {
		private static final long serialVersionUID = 1L;
		public AjaxPane(String id) {
			super(id);
			add(new AjaxSetBehavior());
			add(new Label("title",new PropertyModel(MFXDragPane.this,"title")));
			add(getDragPanePanel("content"));
		}
		@Override
		protected void onComponentTag(ComponentTag tag) {
			super.onComponentTag(tag);
			tag.put("id", getAjaxPaneMarkupId());
			AjaxSetBehavior behavior = (AjaxSetBehavior)getBehaviors(AjaxSetBehavior.class).get(0);
			//tag.put("callbackScript", behavior.getCallbackScript());
			tag.put("callbackUrl", behavior.getCallbackUrl(true));
		}
	}
	
	
	protected String getAjaxPaneMarkupId() {
		return getMarkupId()+"-ajaxpane";
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		tag.put("style", "width: "+getWidth() +" !important;");
	}
	
	protected abstract Panel getDragPanePanel(String id);
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getWidth() {
		return width;
	}
}
