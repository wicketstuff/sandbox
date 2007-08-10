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
package org.wicketstuff.dojo.markup.html.container.split;


import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.markup.html.container.AbstractDojoContainer;
import org.apache.wicket.markup.ComponentTag;

/**
 * <p>
 * TabContainer widget where
 * AbstractDojoContainer should be added 
 * </p>
 * <p>
 * 	<b>Sample</b>
 *  <pre>
 * package org.wicketstuff.dojo.examples;
 * 
 * import org.wicketstuff.dojo.markup.html.container.DojoSimpleContainer;
 * import org.wicketstuff.dojo.markup.html.container.page.DojoPageContainer;
 * import org.wicketstuff.dojo.markup.html.container.split.DojoSplitContainer;
 * import org.apache.wicket.markup.html.WebPage;
 * 
 * public class SplitContainerSample extends WebPage {
 * 
 * 	public SplitContainerSample() {
 * 		super();
 * 		DojoSplitContainer container = new DojoSplitContainer("splitContainer");
 * 		add(container);
 * 		container.setOrientation(DojoSplitContainer.ORIENTATION_VERTICAL);
 * 		container.setHeight("500px");
 * 		container.add(new DojoSimpleContainer("tab1", "title1"));
 * 		container.add(new DojoSimpleContainer("tab2", "title2"));
 * 		DojoPageContainer page = new DojoPageContainer("tab3", DatePickerShower.class);
 * 		page.setTitle("title3");
 * 		container.add(page);
 * 		
 * 	}
 * 
 * }
 * 
 *  </pre>
 * </p>
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoSplitContainer extends AbstractDojoContainer
{
	
	//TODO make an enum instead of constants
	public static final String ORIENTATION_VERTICAL = "vertical";
	public static final String ORIENTATION_HORIZONTAL = "horizontal";
	
	private String orientation = ORIENTATION_HORIZONTAL;
	private int sizerWidth = 5;
	private Boolean activeSizing = new Boolean(false);
	
	/**
	 * Construct a DojoTabContainer
	 * @param id container id
	 * @param title container title
	 */
	public DojoSplitContainer(String id, String title)
	{
		super(id, title);
		add(new DojoSplitContainerHandler());
	}

	/**
	 * Construct a DojoTabContainer
	 * @param id container id
	 */
	public DojoSplitContainer(String id)
	{
		this(id, null);
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_SPLITCONTAINER;
	}

	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("label", getTitle());
		tag.put("orientation", getOrientation());
		tag.put("sizerWidth", Integer.toString(getSizerWidth()));
		tag.put("activeSizing", getActiveSizing().toString());
		tag.put("persist", "false");
	}

	/**
	 * Return true if resize is done in real time when the handler is moved and false otherwise
	 * @return true if resize is done in real time when the handler is moved and false otherwise
	 */
	public Boolean getActiveSizing()
	{
		return activeSizing;
	}

	/**
	 * set activeSizing to true to resize in real time when the handler is moved
	 * @param activeSizing true to resize in real time when the handler is moved
	 */
	public void setActiveSizing(Boolean activeSizing)
	{
		this.activeSizing = activeSizing;
	}

	/**
	 * return the handler orientation use ORIENTATION_VERTICAL or ORIENTATION_HORIZONTAL constants
	 * @return the handler orientation use ORIENTATION_VERTICAL or ORIENTATION_HORIZONTAL constants
	 */
	public String getOrientation()
	{
		return orientation;
	}

	/**
	 * Set the orientation : use ORIENTATION_VERTICAL or ORIENTATION_HORIZONTAL constants
	 * @param orientation use ORIENTATION_VERTICAL or ORIENTATION_HORIZONTAL constants
	 */
	public void setOrientation(String orientation)
	{
		this.orientation = orientation;
	}

	/**
	 * Return the handler size
	 * @return the handler size
	 */
	public int getSizerWidth()
	{
		return sizerWidth;
	}

	/**
	 * set the handler size
	 * @param sizerWidth set the handler size
	 */
	public void setSizerWidth(int sizerWidth)
	{
		this.sizerWidth = sizerWidth;
	}

}
