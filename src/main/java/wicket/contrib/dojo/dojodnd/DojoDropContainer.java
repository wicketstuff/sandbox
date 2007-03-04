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

import wicket.Component;
import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.markup.html.form.ImmediateCheckBox;
import wicket.markup.html.WebMarkupContainer;

/**
 * Dojo drrop container
 * <p>
 * 	A drop container is a HTML container used to define a Drop area.
 *  This area is associated with a pattern. This pattern is used to know 
 *  if a {@link DojoDragContainer} can be drag and drop on it 
 * </p>
 * <p>
 * 	<b>Sample</b>
 *  <pre>
 * package wicket.contrib.dojo.examples;
 * 
 * import wicket.PageParameters;
 * import wicket.contrib.dojo.dojodnd.DojoDragContainer;
 * import wicket.contrib.dojo.dojodnd.DojoDropContainer;
 * import wicket.markup.html.WebPage;
 * import wicket.markup.html.image.Image;
 * 
 * public class DnDShower extends WebPage {
 * 	
 * 	public DnDShower(PageParameters parameters){
 * 		DojoDropContainer dropContainer = new DojoDropContainer("dropContainer"){
 * 		
 * 			public void onDrop(DojoDragContainer container, int position) {
 * 				System.out.println("position = " + position);
 * 				System.out.println("DojoDragContainer" + container.getId());
 * 				
 * 			}
 * 		
 * 		};
 * 		add(dropContainer);
 * 		
 * 		DojoDragContainer dragContainer1 = new DojoDragContainer("dragContainer1");
 * 		DojoDragContainer dragContainer2 = new DojoDragContainer("dragContainer2");
 * 		DojoDragContainer dragContainer3 = new DojoDragContainer("dragContainer3");
 * 		add(dragContainer1);
 * 		add(dragContainer2);
 * 		add(dragContainer3);
 * 		
 * 		
 * 		DojoDragContainer dragContainer4 = new DojoDragContainer("dragContainer4");
 * 		DojoDragContainer dragContainer5 = new DojoDragContainer("dragContainer5");
 * 		dropContainer.add(dragContainer4);
 * 		dropContainer.add(dragContainer5);
 * 		
 * 		dragContainer1.add(new Image("pic1"));
 * 		dragContainer2.add(new Image("pic2"));
 * 		dragContainer3.add(new Image("pic3"));
 * 		dragContainer4.add(new Image("pic4"));
 * 		dragContainer5.add(new Image("pic5"));
 * 	}
 * }
 *  </pre>
 * </p>
 * @author <a href="http://www.demay-fr.net/blog/index.php/en">Vincent Demay</a>
 *
 */
public abstract class DojoDropContainer extends WebMarkupContainer
{

	private String dropId;
	
	/**
	 * Create a DropContainer
	 * @param id Drop container id
	 */
	public DojoDropContainer(String id)
	{
		super(id);
		this.setOutputMarkupId(true);
		//all by default
		dropId = "*";
		add(new DojoDropContainerHandler());
	}
	
	/**
	 * Set the drop pattern. The drop container only accept dragContainer with
	 * the same pattern or all id *
	 * @param pattern drop pattern
	 */
	public void setDropPattern(String pattern){
		this.dropId = pattern;
	}
	
	/**
	 * Drop pattern to specified which Drag container can be dropped on
	 * this Container
	 * @return the Drop 
	 */
	public String getDropPattern(){
		return dropId;
	}
	
	/**
	 * Returns the name of the javascript method that will be invoked when the
	 * processing of the ajax callback is complete. The method must have the
	 * following signature: <code>function(type, data, evt)</code> where the
	 * data argument will be the value of the resouce stream provided by
	 * <code>getResponseResourceStream</code> method.
	 * 
	 * For example if we want to echo the value returned by
	 * getResponseResourceStream stream we can implement it as follows: <code>
	 * <pre>
	 *       
	 *       getJsCallbackFunctionName() {return(&quot;handleit&quot;);}
	 *       
	 *       in javascript:
	 *       
	 *       function handleit(type, data, evt) { alert(data); } 
	 * </pre>
	 * </code>
	 * 
	 * @see ImmediateCheckBox#getResponseResourceStream()
	 * @return name of the client-side javascript callback handler
	 */
	protected String getJSCallbackFunctionName()
	{
		return null;
	}
	
	/**
	 * Called after the model is updated. Use this method to e.g. update the
	 * persistent model. Does nothing by default.
	 */
	protected void onAjaxModelUpdated(AjaxRequestTarget target)
	{
		String dragSource = getRequest().getParameter("dragSource");
		int position = Integer.parseInt(getRequest().getParameter("position"));
		LookupChildVisitor visitor = new LookupChildVisitor(dragSource);
		getPage().visitChildren(visitor);
		Component container = visitor.getComponent();
		onDrop(target, (DojoDragContainer) container, position);  
	}

	/**
	 * This method is triggered when a {@link DojoDragContainer} is dropped
	 * on this container.
	 * @param container {@link DojoDragContainer} dropped
	 * @param position position where it is dropped
	 * @param target {@link AjaxRequestTarget}
	 */
	public abstract void onDrop(AjaxRequestTarget target, DojoDragContainer container, int position);
	
	/**
	 * This class lookup a component in the page component tree by its id
	 * 
	 * @author Vincent Demay
	 *
	 */
	protected class LookupChildVisitor implements IVisitor{

		private String markupId;
		private Component component = null;
		
		/**
		 * Create the visitor using the markupId 
		 * @param markupId markupId to lookup
		 */
		public LookupChildVisitor(String markupId)
		{
			this.markupId = markupId;
		}

		/* (non-Javadoc)
		 * @see wicket.Component.IVisitor#component(wicket.Component)
		 */
		public Object component(Component component)
		{
			if (/*!component.getId().startsWith(Component.AUTO_COMPONENT_PREFIX) &&*/ component.getMarkupId() != null && component.getMarkupId().equals(this.markupId)){
				this.component = component;
			}
			if (this.component == null){
				return IVisitor.CONTINUE_TRAVERSAL;
			}
			return IVisitor.STOP_TRAVERSAL;
		}
		
		/**
		 * Return component if it has been found or null otherwise
		 * @return component if it has been found or null otherwise
		 */
		public Component getComponent(){
			return this.component;
		}
		
		
	}
}
