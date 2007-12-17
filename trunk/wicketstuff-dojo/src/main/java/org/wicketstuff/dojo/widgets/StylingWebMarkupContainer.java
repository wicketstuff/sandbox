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
package org.wicketstuff.dojo.widgets;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * {@link WebMarkupContainer} with style attribute
 * <p>
 * You can add css attribute on this container using the following behavior :
 * Html attributes have bigger priority as java set attributes. Priority order 
 * of the css attribute is : <br/>
 * file.css &lt; style attribute in markup &lt; style attribute added with java
 * </p>
 * <p>
 * If you need to set attributes which are not available in this class you can extend
 * <code>onStyleAttribute</code> as the following:
 * <pre>
 * protected void onStyleAttribute(StyleAttribute styleAttribute){
 *		styleAttribute.put("myCssAttribute", "itsValue");		
 *	}
 * </pre>
 * </p>
 * @author vdemay
 *
 */
@SuppressWarnings("serial")
public class StylingWebMarkupContainer extends WebMarkupContainer
{

	private StyleAttribute style;
	
	/**
	 * @param parent
	 * @param id
	 */
	public StylingWebMarkupContainer(String id)
	{
		super(id);
		style = new StyleAttribute();
	}
	
	/**
	 * @param parent
	 * @param id
	 * @param model
	 */
	public StylingWebMarkupContainer(String id, IModel model)
	{
		super(id, model);
		style = new StyleAttribute();
	}
	
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		
		//getHtmlStyle :
		String styleAtt = (String)tag.getAttributes().get("style");
		if(styleAtt != null){
			String[] items = styleAtt.split(";");
			for(int i=0; i<items.length; i++){
				String item = items[i];
				style.put(
						item.substring(0, item.indexOf(":")).replaceAll(" ", ""), 
						item.substring(item.indexOf(":") + 1, item.length()).replaceAll(" ", ""));
			}
		}
		
		onStyleAttribute(style);
		Iterator ite = style.entrySet().iterator();
		
		String styleTag = "";
		while (ite.hasNext()){
			Entry entry = (Entry)ite.next();
			styleTag += entry.getKey()+":"+entry.getValue()+";";
		}
		if (styleTag != null && !"".equals(styleTag)){
			tag.put("style", styleTag);	
		}
	}
	
	/**
	 * Method to overwrite in order to add new css Attributes
	 * @param styleAttribute see {@link StyleAttribute} to add new css attributes
	 */
	protected void onStyleAttribute(StyleAttribute styleAttribute){
		
	}
	
	//-----------------------------------------------------------------------//
	
	/**
	 * @param height
	 */
	public final void setHeight(String height){
		style.setHeight(height);
	}
	
	/**
	 * @param width
	 */
	public final void setWidth(String width){
		style.setWidth(width);
	}
	
	/**
	 * @param minHeight
	 */
	public void setMinHeight(String minHeight){
		style.setMinHeight(minHeight);
	}
	
	/**
	 * @param minWidth
	 */
	public void setMinWidth(String minWidth){
		style.setMinWidth(minWidth);
	}
	
	/**
	 * @param display
	 */
	public final void setDisplay(String display){
		style.setDisplay(display);
	}
	
	/**
	 * @param left
	 */
	public void setLeft(String left){
		style.setLeft(left);
	}
	
	/**
	 * @param top
	 */
	public final void setTop(String top){
		style.setTop(top);
	}
}
