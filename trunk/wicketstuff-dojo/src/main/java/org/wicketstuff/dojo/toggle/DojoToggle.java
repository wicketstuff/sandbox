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
package org.wicketstuff.dojo.toggle;

/**
 * Define a Dojo toggle. Toggles are used to make effect in widget
 * @author <a href="http://www.demay-fr.net/blog/index.php/en">Vincent Demay</a>
 *
 */
public abstract class DojoToggle
{
	private int duration;

	public DojoToggle(){
		this.duration = 200;
	}
	
	/**
	 * Construct a toggler
	 * @param duration effect duration in ms
	 */
	public DojoToggle(int duration){
		this.duration = duration;
	}
	
	/**
	 * return the effet
	 * @return the effet
	 */
	public abstract String getToggle();
	
	/**
	 * return the duration of the effet
	 * @return the effet duration
	 */
	public int getDuration(){
		return this.duration;
	}
	
}
