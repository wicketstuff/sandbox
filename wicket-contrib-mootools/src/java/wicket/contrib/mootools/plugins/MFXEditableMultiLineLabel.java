package wicket.contrib.mootools.plugins;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;

import wicket.contrib.mootools.behaviors.MFXFadeOutBehavior;

public class MFXEditableMultiLineLabel extends Panel {
	private static final long serialVersionUID = 1L;
	private WebMarkupContainer container;
	private Label label;
	private EditPanel editPanel;
	private int height = 400;

	protected boolean getEscapeStrings() {
		return true;
	}

	protected boolean getEditable() {
		return true;
	}

	protected void onEdit(final AjaxRequestTarget target) {
	}

	protected void onSubmit(final AjaxRequestTarget target) {
	}

	public MFXEditableMultiLineLabel(final String id, final IModel model) {
		super(id, model);

		add(container = new WebMarkupContainer("container"));
		container.setOutputMarkupId(true);

		container.add(label = new Label("label", getModel()));
		label.setEscapeModelStrings(MFXEditableMultiLineLabel.this.getEscapeStrings());
		label.setOutputMarkupId(true);

		container.add(editPanel = new EditPanel("editPanel"));

		label.add(new MFXFadeOutBehavior("ondblclick", Duration.milliseconds(500)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(final AjaxRequestTarget arg0) {
				if (getEditable()) {
					onEdit(arg0);
					editPanel.setVisible(true);
					label.setVisible(false);
				}
				arg0.addComponent(container);
			}
		});

		editPanel.setVisible(false);
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public class EditPanel extends WebMarkupContainer {
		private static final long serialVersionUID = 1L;
		private TextArea txt;

		public EditPanel(final String id) {
			super(id);

			setOutputMarkupId(true);
			add(txt = new TextArea("editor", new IModel() {
				private static final long serialVersionUID = 1L;

				public Object getObject() {
					return MFXEditableMultiLineLabel.this.getModelObject();
				}

				public void setObject(final Object arg0) {
					MFXEditableMultiLineLabel.this.setModelObject(arg0);
				}

				public void detach() {
				}
			}));
			txt.setOutputMarkupId(true);
			txt.add(new SimpleAttributeModifier("style", "height: " + getHeight()
					+ "px !important; width: 90% !important;"));

			add(new WebMarkupContainer("cancel").add(new MFXFadeOutBehavior("onclick", Duration.milliseconds(500),
					EditPanel.this) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(final AjaxRequestTarget arg0) {
					EditPanel.this.setVisible(false);
					label.setVisible(true);
					arg0.addComponent(container);
				}
			}));

			add(new WebMarkupContainer("save").add(new MFXFadeOutBehavior("onclick", Duration.milliseconds(500),
					EditPanel.this) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(final AjaxRequestTarget arg0) {
					if (getEditable()) {
						txt.processInput();
						MFXEditableMultiLineLabel.this.onSubmit(arg0);
						EditPanel.this.setVisible(false);
						label.setVisible(true);
					}
					arg0.addComponent(container);
				}

				@Override
				protected void onComponentTag(final ComponentTag tag) {
					super.onComponentTag(tag);

					String callback = "wicketAjaxPost('" + getCallbackUrl() + "',wicketSerialize(Wicket.$('"
							+ txt.getMarkupId() + "'))";

					tag.put("onclick", "javascript:" + generateCallbackScript(callback));

				}
			}));
		}
	}
}
