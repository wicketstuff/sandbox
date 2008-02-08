/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.progressbar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.progressbar.support.DynamicAjaxSelfUpdatingTimerBehavior;


/**
 * A ProgressBar component displays a progress bar that is updatable via AJAX
 * and displays progress active.
 *
 * The current progress is given with a Progression value object.
 *
 * <pre>
 * final ProgressBar;
 * add(bar = new ProgressBar(&quot;progress&quot;, new ProgressionModel() {
 *     protected Progression getProgression() {
 *         return new Progression(progress);
 *     }
 * }));
 * </pre>
 *
 * The model can also be later injected.
 *
 * The progress bar can be used both active or passive to show progress in a
 * wizard etc. The active progress bar must be started from within an ajax
 * request (e.g. AjaxButton):
 *
 * <pre>
 * form.add(new AjaxButton(&quot;button&quot;) {
 *     protected void onSubmit(AjaxRequestTarget target, Form form) {
 *         bar.start(target);
 *         // start some task
 *     }
 * }
 * </pre>
 *
 * <p>
 * The ProgressBar is automatically stopped (no more AJAX updates) when the
 * Progress object isDone() method returns true.
 * </p>
 *
 * @author Christopher Hlubek (hlubek)
 *
 */
public class ProgressBar extends Panel {

	private int width = 400;

	private static final ResourceReference CSS = new ResourceReference(
			ProgressBar.class, "ProgressBar.css");

	public ProgressBar(String id, ProgressionModel model) {
		super(id, model);

		HeaderContributor cssContributor = HeaderContributor.forCss(CSS);
		if(getParent() != null) {
			getParent().add(cssContributor);
		} else {
			add(cssContributor);
		}

		add(new Label("label", getLabelModel(model)));
		add(new WebMarkupContainer("bar").add(new AttributeModifier("style",
				true, new AbstractReadOnlyModel() {
					@Override
					public Object getObject() {
						ProgressionModel model = (ProgressionModel) getModel();
						Progression progression = model.getProgression();

						// set the width of the bar in % of the progress
						// this is coupled with the specific CSS
						return "width: " + progression.getProgress() + "%";
					}
				})));
		setOutputMarkupId(true);
	}

	/**
	 * Create the model for the label on the bar.
	 *
	 * This could be overridden for
	 * a custom label
	 *
	 * @param model the ProgressionModel
	 *
	 * @return A model for the bar label
	 */
	protected AbstractReadOnlyModel getLabelModel(final ProgressionModel model) {
		return new AbstractReadOnlyModel() {
			@Override
			public Object getObject() {
				Progression progression = model.getProgression();
				return progression.getProgress() + "%";
			}
		};
	}

	/**
	 * Start the progress bar.
	 *
	 * This must happen in an AJAX request.
	 *
	 * @param target
	 */
	public void start(AjaxRequestTarget target) {
		setVisible(true);
		add(new DynamicAjaxSelfUpdatingTimerBehavior(
				Duration.ONE_SECOND) {
			@Override
			protected void onPostProcessTarget(AjaxRequestTarget target) {
				ProgressionModel model = (ProgressionModel) getModel();
				Progression progression = model.getProgression();
				if (progression.isDone()) {
					// stop the self update
					stop();
					// do custom action
					onFinished(target);
				}
			}
		});
		if (getParent() != null) {
			target.addComponent(getParent());
		} else {
			target.addComponent(this);
		}
	}

	/**
	 * Override this method for custom action
	 * on finish of the task when progression.isDone()
	 *
	 * This could be cleaning up or hiding the ProgressBar
	 * for example.
	 *
	 * @param target
	 */
	protected void onFinished(AjaxRequestTarget target) {

	}

	/**
	 *
	 * @return the width of the ProgressBar
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set the width of the progress bar.
	 *
	 * @param width the width of the ProgressBar in px
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}
