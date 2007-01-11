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
package wicket.contrib.dojo.dojodnd;

import wicket.MarkupContainer;
import wicket.markup.html.WebMarkupContainer;

/**
 * Dojo drag container
 * <p>
 * 	A drag container is a HTML container used to define a Drag area.
 *  This area is associated with a pattern. This pattern is used to know 
 *  if a DojoDragContainer can be drag and drop on a {@link DojoDropContainer} which 
 *  contains a list of pattern
 * </p>
 * <p>
 * 	<b>Sample</b>
 *  <pre>
 *   package wicket.contrib.dojo.examples;
 *   
 *   import wicket.PageParameters;
 *   import wicket.contrib.dojo.dojodnd.DojoDragContainer;
 *   import wicket.contrib.dojo.dojodnd.DojoDropContainer;
 *   import wicket.markup.html.WebPage;
 *   import wicket.markup.html.image.Image;
 *   
 *   public class DnDShower extends WebPage {
 *   	
 *   	public DnDShower(PageParameters parameters){
 *   		DojoDropContainer dropContainer = new DojoDropContainer(this,"dropContainer"){
 *   		
 *   			public void onDrop(DojoDragContainer container, int position) {
 *   				System.out.println("position = " + position);
 *   				System.out.println("DojoDragContainer" + container.getId());
 *   				
 *   			}
 *   		
 *   		};
 *   		
 *   		DojoDragContainer dragContainer1 = new DojoDragContainer(this,"dragContainer1");
 *   		DojoDragContainer dragContainer2 = new DojoDragContainer(this,"dragContainer2");
 *   		DojoDragContainer dragContainer3 = new DojoDragContainer(this,"dragContainer3");
 *   		
 *   		DojoDragContainer dragContainer4 = new DojoDragContainer(dropContainer,"dragContainer4");
 *   		DojoDragContainer dragContainer5 = new DojoDragContainer(dropContainer,"dragContainer5");
 *   		
 *   		new Image(dragContainer1,"pic1");
 *   		new Image(dragContainer2,"pic2");
 *     		new Image(dragContainer3,"pic3");
 *   		new Image(dragContainer4,"pic4");
 *   		new Image(dragContainer5,"pic5");
 *   	}
 *   }
 *   
 *   
 *  </pre>
 * </p>
 * @author <a href="http://www.demay-fr.net/blog/index.php/en">Vincent Demay</a>
 *
 */
public class DojoDragContainer extends WebMarkupContainer
{

	private String dragId;
	
	/**
	 * Constructor of a drag container
	 * @param parent parent where to add this widget
	 * @param id widgte id
	 */
	public DojoDragContainer(MarkupContainer parent, String id)
	{
		super(parent, id);
		this.setOutputMarkupId(true);
		//all by default
		dragId = "*";
		add(new DojoDragContainerHandler());
	}
	
	/**
	 * Drag Pattern
	 * @param pattern pattern use to allow to be dragged on a dropContainer with the same pattern
	 */
	public void setDragPattern(String pattern){
		this.dragId = pattern;
	}
	
	/**
	 * return pattern used to allow to be dragged on a dropContainer with the same pattern
	 * @return pattern used to allow to be dragged on a dropContainer with the same pattern
	 */
	public String getDragPattern(){
		return dragId;
	}

}
