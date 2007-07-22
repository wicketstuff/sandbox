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

import java.util.HashMap;

/**
 * Modelise a style Attribute on a tag
 * @author vdemay
 *
 */
@SuppressWarnings("serial")
public class StyleAttribute extends HashMap<String, String>
{

	public final static String HEIGHT      = "height";
	public final static String WIDTH       = "width";
	public final static String DISPLAY     = "display";
	public final static String MIN_HEIGHT  = "min-height";
	public final static String MIN_WIDTH   = "min-width";
	public final static String TOP		   = "top";
	public final static String LEFT		   = "left";
	
	/**
	 * Constructor
	 */
	public StyleAttribute()
	{
		super();
	}
	
	public void setTop(String top){
		put(TOP, top);
	}
	
	public void setLeft(String left){
		put(LEFT, left);
	}
	
	public void setHeight(String height){
		put(HEIGHT, height);
	}
	
	public void setWidth(String width){
		put(WIDTH, width);
	}
	
	public void setMinHeight(String minHeight){
		put(MIN_HEIGHT, minHeight);
	}
	
	public void setMinWidth(String minWidth){
		put(MIN_WIDTH, minWidth);
	}
	
	public void setDisplay(String display){
		put(DISPLAY, display);
	}
	
	
	
}
