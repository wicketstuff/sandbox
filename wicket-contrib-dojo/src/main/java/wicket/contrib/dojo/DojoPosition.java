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
package wicket.contrib.dojo;


public enum DojoPosition
{
	//"br-up", "br-left", "bl-up", "bl-right", "tr-down", "tr-left", "tl-down", "tl-right"
	BOTTOM_RIGHT_UP("br-up"),
	BOTTOM_RIGHT_LEFT("br-left"),
	BOTTOM_LEFT_UP("bl-up"),
	BOTTOM_LEFT_RIGHT("bl-right"),
	TOP_RIGHT_DOWM("tr-down"),
	TOP_RIGHT_LEFT("tr-left"),
	TOP_LEFT_DOWN("tl-down"),
	TOP_LEFT_RIGHT("tl-right");

	
	private final String position;

	private DojoPosition(String position)
	{
		this.position = position;
	}

	/**
	 * Actual Position
	 * 
	 * @return the position
	 */
	public String getPosition()
	{
		return position;
	}
}
