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
package org.wicketstuff.dojo.markup.html.form.validation;


import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackIndicator;
import org.apache.wicket.markup.html.image.Image;
import org.wicketstuff.dojo.markup.html.tooltip.DojoTooltip;

/**
 * Feedback indicator that adds a small image when validation fails. Also
 * creates an FXFeedbackTooltip connected to the image that shows the
 * errormessages for connected formcomponent.
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 * 
 */
@SuppressWarnings("serial")
public class FXFeedbackIndicator extends FormComponentFeedbackIndicator
{
	private DojoTooltip feedbackTooltip;

	/**
	 * Construct
	 * 
	 * @param id
	 *            Component ID
	 */
	public FXFeedbackIndicator(String id)
	{
		super(id);
		final ResourceReference resource = new ResourceReference(FXFeedbackIndicator.class, "alerticon.gif");
		Image image = new Image("image", resource);
		add(image);
		feedbackTooltip = new DojoTooltip("feedbacktooltip", image);
		add(feedbackTooltip);
		FXTooltipFeedbackPanel panel = new FXTooltipFeedbackPanel("content");
		feedbackTooltip.add(panel);
	}
}
