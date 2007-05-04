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
package org.wicketstuff.minis.apanel;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.util.resource.StringBufferResourceStream;
import org.apache.wicket.util.tester.ITestPageSource;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Tests
 */
public class APanelTest {
	@Test
	public void testFlowLayout() throws Exception {
		final WicketTester tester = new WicketTester();
		tester.startPage(new TestPageSource(new FlowLayout() {
			@Override
			public void onAfterTag(final Component component, final StringBuilder stringBuilder) {
				stringBuilder.append("]");
			}

			@Override
			public void onBeforeTag(final Component component, final StringBuilder stringBuilder) {
				stringBuilder.append("[");
			}
		}));

		testComponentsPresence(tester);

		// test generated markup
		final APanel aPanel = (APanel) tester.getComponentFromLastRenderedPage("aPanel");
		final StringBufferResourceStream resourceStream =
				(StringBufferResourceStream) aPanel.getMarkupResourceStream(aPanel, APanel.class);

		Assert.assertEquals(
				"<wicket:panel>" +
						"[<span wicket:id=\"label\"></span>]" +
						"[<a wicket:id=\"link\"><span wicket:id=\"label\"></span></a>]" +
						"[<form wicket:id=\"form\"><span wicket:id=\"label\"></span><input type=\"submit\" wicket:id=\"button\"/></form>]" +
						"[<span wicket:id=\"listView\"><span wicket:id=\"label\"></span></span>]" +
						"</wicket:panel>",
				resourceStream.asString()
		);
	}

	@Test
	public void testGridLayout() {
		final WicketTester tester = new WicketTester();
		// layout size is for 6 components so that to test empty cells processing
		final TestPageSource pageSource = new TestPageSource(new GridLayout(2, 3)) {
			@Override
			protected void addConstraints() {
				label.add(new GridLayoutConstraint(0, 0));
				link.add(new GridLayoutConstraint(1, 0));
				form.add(new GridLayoutConstraint(0, 1));
				listView.add(new GridLayoutConstraint(1, 1));
			}
		};
		tester.startPage(pageSource);

		testComponentsPresence(tester);

		// test generated markup
		final APanel aPanel = (APanel) tester.getComponentFromLastRenderedPage("aPanel");
		final StringBufferResourceStream resourceStream =
				(StringBufferResourceStream) aPanel.getMarkupResourceStream(aPanel, APanel.class);
		Assert.assertEquals(
				"<wicket:panel>" +
						"<table>" +
						"<tr>" +
						"<td><span wicket:id=\"label\"></span></td>" +
						"<td><a wicket:id=\"link\"><span wicket:id=\"label\"></span></a></td>" +
						"</tr><tr>" +
						"<td><form wicket:id=\"form\"><span wicket:id=\"label\"></span><input type=\"submit\" wicket:id=\"button\"/></form></td>" +
						"<td><span wicket:id=\"listView\"><span wicket:id=\"label\"></span></span></td>" +
						"</tr><tr>" +
						"<td></td><td></td>" +
						"</tr>" +
						"</table>" +
						"</wicket:panel>",
				resourceStream.asString()
		);
	}

	@Test
	public void testGridLayoutWithAutoConstraints() {
		final WicketTester tester = new WicketTester();
		final TestPageSource pageSource = new TestPageSource(new GridLayout(2, 2)) {
			@Override
			protected void addConstraints() {
//				label.add(new GridLayoutConstraint(0, 1)); // auto added
				link.add(new GridLayoutConstraint(1, 0));
				form.add(new GridLayoutConstraint(0, 0));
//				listView.add(new GridLayoutConstraint(1, 1)); // auto added
			}
		};
		tester.startPage(pageSource);

		testComponentsPresence(tester);

		// test generated markup
		final APanel aPanel = (APanel) tester.getComponentFromLastRenderedPage("aPanel");
		final StringBufferResourceStream resourceStream =
				(StringBufferResourceStream) aPanel.getMarkupResourceStream(aPanel, APanel.class);
		Assert.assertEquals(
				"<wicket:panel>" +
						"<table>" +
						"<tr>" +
						"<td><form wicket:id=\"form\"><span wicket:id=\"label\"></span><input type=\"submit\" wicket:id=\"button\"/></form></td>" +
						"<td><a wicket:id=\"link\"><span wicket:id=\"label\"></span></a></td>" +
						"</tr><tr>" +
						"<td><span wicket:id=\"label\"></span></td>" +
						"<td><span wicket:id=\"listView\"><span wicket:id=\"label\"></span></span></td>" +
						"</tr>" +
						"</table>" +
						"</wicket:panel>",
				resourceStream.asString()
		);
	}

	@Test(expected = WicketRuntimeException.class)
	public void testGridLayoutOutOfCellsException() {
		final WicketTester tester = new WicketTester();
		final TestPageSource pageSource = new TestPageSource(new GridLayout(2, 1));
		tester.startPage(pageSource);
	}

	@Test
	public void testGridLayoutWithSpan() {
		final WicketTester tester = new WicketTester();
		final TestPageSource pageSource = new TestPageSource(new GridLayout(2, 3)) {
			@Override
			protected void addConstraints() {
				label.add(new GridLayoutConstraint(0, 0).setColSpan(2));
				form.add(new GridLayoutConstraint(0, 1).setRowSpan(2));
				link.add(new GridLayoutConstraint(1, 1));
//				listView.add(new GridLayoutConstraint(1, 2)); // auto added
			}
		};
		tester.startPage(pageSource);

		testComponentsPresence(tester);

		// test generated markup
		final APanel aPanel = (APanel) tester.getComponentFromLastRenderedPage("aPanel");
		final StringBufferResourceStream resourceStream =
				(StringBufferResourceStream) aPanel.getMarkupResourceStream(aPanel, APanel.class);
		Assert.assertEquals(
				"<wicket:panel>" +
						"<table>" +
						"<tr>" +
						"<td colspan=\"2\"><span wicket:id=\"label\"></span></td>" +
						"</tr><tr>" +
						"<td rowspan=\"2\"><form wicket:id=\"form\"><span wicket:id=\"label\"></span><input type=\"submit\" wicket:id=\"button\"/></form></td>" +
						"<td><a wicket:id=\"link\"><span wicket:id=\"label\"></span></a></td>" +
						"</tr><tr>" +
						"<td><span wicket:id=\"listView\"><span wicket:id=\"label\"></span></span></td>" +
						"</tr>" +
						"</table>" +
						"</wicket:panel>",
				resourceStream.asString()
		);
	}

	private void testComponentsPresence(final WicketTester tester) {
		tester.assertComponent("aPanel", APanel.class);
		tester.assertLabel("aPanel:label", "some text");

		tester.assertComponent("aPanel:link", Link.class);
		tester.assertLabel("aPanel:link:label", "another text");

		tester.assertComponent("aPanel:form", Form.class);
		tester.assertLabel("aPanel:form:label", "label on the form");
		tester.assertComponent("aPanel:form:button", Button.class);

		tester.assertComponent("aPanel:listView", ListView.class);
		tester.assertComponent("aPanel:listView:0", ListItem.class);
		tester.assertComponent("aPanel:listView:1", ListItem.class);
		tester.assertComponent("aPanel:listView:2", ListItem.class);
		tester.assertLabel("aPanel:listView:0:label", "a1");
		tester.assertLabel("aPanel:listView:1:label", "2");
		tester.assertLabel("aPanel:listView:2:label", "3");
	}

	private static class TestPageSource implements ITestPageSource {
		private final ILayout layout;
		protected Label label;
		protected Link link;
		protected Form form;
		protected ListView listView;

		public TestPageSource(final ILayout layout) {
			this.layout = layout;
		}

		public Page getTestPage() {
			final APanel aPanel = new APanel("aPanel", layout);
			final TestPage testPage = new TestPage(aPanel);

			label = new Label("label", "some text");
			aPanel.add(label);

			link = new Link("link") {
				public void onClick() {
					// do nothing
				}
			};
			link.add(new Label("label", "another text"));
			aPanel.add(link);

			form = new Form("form");
			form.add(new Label("label", "label on the form"));
			form.add(new Button("button"));
			aPanel.add(form);

			listView = new ListView("listView", Arrays.asList("a1", "2", "3")) {
				protected void populateItem(final ListItem item) {
					final String s = (String) item.getModelObject();
					item.add(new Label("label", s));
				}
			};
			aPanel.add(listView);

			addConstraints();

			return testPage;
		}

		protected void addConstraints() {
		}
	}
}
