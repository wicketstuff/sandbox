/*
 * $Id: SliderPage.java 4820 2006-03-08 08:21:01Z eelco12 $ $Revision: 4820 $
 * $Date: 2006-03-08 16:21:01 +0800 (Wed, 08 Mar 2006) $
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
package wicket.contrib.yui.examples.pages;

import wicket.MarkupContainer;
import wicket.contrib.markup.html.yui.slider.Slider;
import wicket.contrib.markup.html.yui.slider.SliderSettings;
import wicket.contrib.yui.examples.WicketExamplePage;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.CompoundPropertyModel;

/**
 * Page that displays the calendar component of the Yahoo UI library.
 * 
 * @author Joshua Lim
 * @author Eelco Hillenius
 */
public class SliderPage extends WicketExamplePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public SliderPage() {
		new SliderForm(this, "sliderForm");
		new FeedbackPanel(this, "feedback");
	}

	private class SliderForm extends Form<SliderForm> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private Integer wicketScore = new Integer(0);

		private Integer strutsScore = new Integer(0);

		private TextField tfWicket;

		private TextField tfStruts;

		public SliderForm(MarkupContainer parent, String id) {

			super(parent, id);
			setModel(new CompoundPropertyModel<SliderForm>(this));

			int leftUp = 100;
			int rightDown = 100;
			int tick = 1;

			tfWicket = new TextField(this, "wicketScore");
			new Slider(this, "wicketSlider", tfWicket, SliderSettings
					.getDefault(leftUp, rightDown, tick));

			tfStruts = new TextField(this, "strutsScore");
			new Slider(this, "strutsSlider", tfStruts, SliderSettings.getAqua(
					0, 300, 30));

		}

		@Override
		protected void onSubmit() {
			info("Wicket: " + this.wicketScore.toString());
			info("Struts: " + this.strutsScore.toString());
		}

		public Integer getStrutsScore() {
			return strutsScore;
		}

		public void setStrutsScore(Integer strutsScore) {
			this.strutsScore = strutsScore;
		}

		public Integer getWicketScore() {
			return wicketScore;
		}

		public void setWicketScore(Integer wicketScore) {
			this.wicketScore = wicketScore;
		}
	}

}
