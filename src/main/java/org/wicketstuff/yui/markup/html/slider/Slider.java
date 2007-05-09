/*
 * $Id: Slider.java 5132 2006-03-26 02:13:41 -0800 (Sun, 26 Mar 2006)
 * jdonnerstag $ $Revision: 5189 $ $Date: 2006-03-26 02:13:41 -0800 (Sun, 26 Mar
 * 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.markup.html.slider;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.template.TextTemplateHeaderContributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.yui.markup.html.AbstractYuiPanel;


/**
 * Slider component based on the Slider of Yahoo UI Library.
 * 
 * @author Eelco Hillenius
 * @author Joshua Lim
 */
public class Slider extends AbstractYuiPanel implements IHeaderContributor {
	private static final long serialVersionUID = 1L;

	/**
	 * The id of the background element.
	 */
	private String backgroundElementId;

	/**
	 * The id of the image element.
	 */
	private String imageElementId;

	/**
	 * The JavaScript variable name of the slider component.
	 */
	private String javaScriptId;

	private static final Logger log = LoggerFactory.getLogger(Slider.class);

	/**
	 * Construct.
	 * 
	 * @param id
	 *            the component id
	 * @param model
	 *            the model for this component
	 */
	public Slider(String id, IModel model, final FormComponent element,
			final SliderSettings settings) {
		super(id, model);

		add(HeaderContributor.forCss(Slider.class, "css/slider.css"));

		/*
		 * default settings if null
		 */

		if (settings == null) {
			// error
		}

		/* handle form element */
		if (element != null) {
			element.add(new AttributeModifier("id", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						@Override
						public Object getObject() {
							return element.getId();
						}
					}));
		}

		IModel variablesModel = new AbstractReadOnlyModel() {
			private static final long serialVersionUID = 1L;

			/** cached variables; we only need to fill this once. */
			private Map<String, Object> variables;

			/**
			 * @see wicket.model.AbstractReadOnlyModel#getObject(wicket.Component)
			 */
			@Override
			public Object getObject() {
				if (variables == null) {
					this.variables = new HashMap<String, Object>(7);
					variables.put("javaScriptId", javaScriptId);
					variables.put("backGroundElementId", backgroundElementId);
					variables.put("imageElementId", imageElementId);
					variables.put("leftUp", settings.getLeftUp());
					variables.put("rightDown", settings.getRightDown());
					variables.put("tick", settings.getTick());
					variables.put("formElementId", element.getId());
				}
				return variables;
			}
		};

		add(TextTemplateHeaderContributor.forJavaScript(Slider.class,
				"init.js", variablesModel));

		/*
		 * LEFT Corner Images & Tick Marks
		 */

		Image leftTickImg = new Image("leftTickImg", settings
				.getLeftTickResource());
		leftTickImg.add(new AttributeModifier("onclick", true,
				new AbstractReadOnlyModel() {

					private static final long serialVersionUID = 1L;

					@Override
					public Object getObject() {
						return javaScriptId + ".setValue(" + javaScriptId
								+ ".getXValue() - " + settings.getTick() + ");";
					}
				}));
		add(leftTickImg.setVisible(settings.isShowTicks()));

		Image leftCornerImg = new Image("leftCornerImg", settings
				.getLeftCornerResource());
		leftCornerImg.add(new AttributeModifier("onclick", true,
				new AbstractReadOnlyModel() {
					private static final long serialVersionUID = 1L;

					@Override
					public Object getObject() {
						return javaScriptId + ".setValue(-"
								+ settings.getLeftUp() + ")";
					}
				}));
		add(leftCornerImg);

		/*
		 * RIGHT Corner Images & Tick Marks
		 */

		Image rightCornerImg = new Image("rightCornerImg", settings
				.getRightCornerResource());
		rightCornerImg.add(new AttributeModifier("onclick", true,
				new AbstractReadOnlyModel() {
					private static final long serialVersionUID = 1L;

					@Override
					public Object getObject() {
						return javaScriptId + ".setValue("
								+ settings.getRightDown() + ")";
					}
				}));
		add(rightCornerImg);

		Image rightTickImg = new Image("rightTickImg", settings
				.getRightTickResource());
		rightTickImg.add(new AttributeModifier("onclick", true,
				new AbstractReadOnlyModel() {
					private static final long serialVersionUID = 1L;

					@Override
					public Object getObject() {
						return javaScriptId + ".setValue(" + javaScriptId
								+ ".getXValue() + " + settings.getTick() + ");";
					}
				}));
		add(rightTickImg.setVisible(settings.isShowTicks()));

		/*
		 * Background Div
		 */

		WebMarkupContainer backgroundElement = new WebMarkupContainer(
				"backgroundDiv");
		backgroundElement.add(new AttributeModifier("id", true,
				new PropertyModel(this, "backgroundElementId")));
		backgroundElement.add(new AttributeModifier("style", true, new Model(
				settings.getBackground().getStyle())));
		add(backgroundElement);

		/*
		 * Element Div and Thumb Div
		 */

		WebMarkupContainer imageElement = new WebMarkupContainer("handleDiv");
		imageElement.add(new AttributeModifier("id", true, new PropertyModel(
				this, "imageElementId")));
		imageElement.add(new AttributeModifier("style", true, new Model(
				settings.getHandle().getStyle())));

		WebMarkupContainer thumbElement = new WebMarkupContainer("thumbDiv");
		thumbElement.add(new AttributeModifier("style", true, new Model(
				settings.getThumb().getStyle())));

		imageElement.add(thumbElement);
		backgroundElement.add(imageElement);

	}

	/**
	 * Contruct. creates a default Slider.
	 * 
	 * @param id
	 * @param model
	 * @param leftUp
	 * @param rightDown
	 * @param tick
	 * @param element
	 */

	public Slider(String id, IModel model, final int leftUp,
			final int rightDown, final int tick, final FormComponent element) {
		this(id, model, element, SliderSettings.getDefault(leftUp, rightDown,
				tick));
	}

	/**
	 * Gets backgroundElementId.
	 * 
	 * @return backgroundElementId
	 */
	public final String getBackgroundElementId() {
		return backgroundElementId;
	}

	/**
	 * Gets imageElementId.
	 * 
	 * @return imageElementId
	 */
	public final String getImageElementId() {
		return imageElementId;
	}

	/**
	 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response) {
		response.renderOnLoadJavascript("init" + javaScriptId + "();");
	}

	/**
	 * TODO implement
	 */
	public void updateModel() {
		log.info("updateModel");
	}

	/**
	 * @see wicket.Component#onAttach()
	 */
	@Override
	protected void onAttach() {
		super.onAttach();

		// initialize lazily
		if (backgroundElementId == null) {
			// assign the markup id
			String id = getMarkupId();
			backgroundElementId = id + "Bg";
			imageElementId = id + "Img";
			javaScriptId = backgroundElementId + "JS";
		}
	}
}
