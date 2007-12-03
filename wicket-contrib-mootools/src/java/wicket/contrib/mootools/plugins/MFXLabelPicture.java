package wicket.contrib.mootools.plugins;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.mootools.IncludeMooTools;
import wicket.contrib.mootools.MFXJavascriptUtils;
import wicket.contrib.mootools.events.MFXWindowLoad;

public abstract class MFXLabelPicture extends Panel {
	private static final long serialVersionUID = 1L;
	private List<MFXLabel> labels = new ArrayList<MFXLabel>();
	private Component comp;
	private MFXWindowLoad winLoad;
	private AjaxCallBackBehavior callbackBehavior;
	private AjaxImageLabelCallback ajaxImageCallback;

	public static interface AjaxImageLabelCallback extends Serializable {
		public void onSavePosition(MFXLabel label, AjaxRequestTarget targ);
	}

	public class AjaxCallBackBehavior extends AbstractDefaultAjaxBehavior {
		private static final long serialVersionUID = 1L;

		@Override
		public CharSequence getCallbackScript() {
			return getCallbackScript(true).toString();
		}

		@Override
		protected void respond(final AjaxRequestTarget arg0) {
			RequestCycle requestCycle = RequestCycle.get();
			String callback = requestCycle.getRequest().getParameter("callback");
			int x = Integer.valueOf(requestCycle.getRequest().getParameter("x"));
			int y = Integer.valueOf(requestCycle.getRequest().getParameter("y"));

			MFXLabel lbl = new MFXLabel(callback, x, y);
			if (MFXLabelPicture.this.getAjaxImageCallback() != null) {
				MFXLabelPicture.this.getAjaxImageCallback().onSavePosition(lbl, arg0);
			}
		}
	}

	public static class MFXAddButton extends WebMarkupContainer {
		private static final long serialVersionUID = 1L;
		private MFXLabelPicture picture;

		public MFXAddButton(final String id, final MFXLabelPicture picture) {
			super(id);
			this.picture = picture;
		}

		@Override
		protected void onComponentTag(final ComponentTag tag) {
			super.onComponentTag(tag);
			String id = "boxFactory" + picture.getPictureId();
			tag.put("onclick", id + ".showTagBox();");
		}
	}

	public static class MFXLabel implements Serializable {
		private static final long serialVersionUID = 1L;
		private String label;
		private int x;
		private int y;

		public MFXLabel(final String label, final int x, final int y) {
			super();
			this.label = label;
			this.x = x;
			this.y = y;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(final String label) {
			this.label = label;
		}

		public int getX() {
			return x;
		}

		public void setX(final int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(final int y) {
			this.y = y;
		}
	}

	@Override
	public void renderHead(final HtmlHeaderContainer container) {
		container.getResponse().write("<script>var boxFactory" + comp.getMarkupId() + " = null;</script>");
		super.renderHead(container);
	}

	public String getPictureId() {
		return comp.getMarkupId();
	}

	public MFXLabelPicture(final String id, final List<MFXLabel> labels) {
		super(id);
		this.labels = labels;
		add(new IncludeMooTools());
		add(HeaderContributor.forJavaScript(MFXJavascriptUtils.getMooAddons()));
		comp = getPicture("picture");
		comp.setOutputMarkupId(true);
		add(comp);
		add(winLoad = new MFXWindowLoad());
		add(callbackBehavior = new AjaxCallBackBehavior());
	}

	@Override
	protected void onRender(final MarkupStream markupStream) {
		StringBuffer out = new StringBuffer();
		String boxVar = "boxFactory" + comp.getMarkupId();
		out.append(boxVar + " = new BoxFactory($('" + comp.getMarkupId() + "'),20,'"
				+ callbackBehavior.getCallbackUrl(true) + "');");
		for (MFXLabel label : labels) {
			out.append(boxVar + ".loadBox(" + label.getX() + "," + label.getY() + ",'" + label.getLabel() + "');");
		}
		winLoad.addAction(out.toString());

		super.onRender(markupStream);
	}

	public abstract Component getPicture(String id);

	public void setAjaxImageCallback(final AjaxImageLabelCallback ajaxImageCallback) {
		this.ajaxImageCallback = ajaxImageCallback;
	}

	public AjaxImageLabelCallback getAjaxImageCallback() {
		return ajaxImageCallback;
	}

}
