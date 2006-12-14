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
package wicket.contrib.dojo.markup.html.toaster;

import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE;
import static wicket.contrib.dojo.DojoIdConstants.DOJO_TYPE_TOASTER;
import wicket.MarkupContainer;
import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.DojoPosition;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebMarkupContainer;
import wicket.model.IModel;
import wicket.util.time.Duration;

/**
 * A dojo toaster is a message that will appear in a corner of the screen such.
 * <p>
 * <b>Sample</b>
 * 	<pre>
 * public class ToasterSample extends WebPage {
 *	
 *	public ToasterSample(PageParameters parameters){
 *		final DojoToaster toaster1 = new DojoToaster(this,"toaster1",new Model<String>("Some messages here. Funy isn\'t it ;)"));
 *		AjaxLink link1 = new AjaxLink(this, "link1"){
 *			public void onClick(AjaxRequestTarget target) {
 *				toaster1.publishMessage(target);
 *				
 *			}
 *		};
 *		
 *		final DojoToaster toaster2 = new DojoToaster(this,"toaster2",new Model<String>("Some messages here. Funy isn\'t it ;)"));
 *		AjaxLink link2 = new AjaxLink(this, "link2"){
 *			public void onClick(AjaxRequestTarget target) {
 *				toaster2.publishMessage(target,DojoToaster.ERROR);
 *				
 *			}
 *		}; 
 *
 *	
 *		final DojoToaster toaster4 = new DojoToaster(this,"toaster4",new Model<String>("Some messages here. Funy isn\'t it ;)"));
 *		toaster4.setPosition(DojoPosition.BOTTOM_LEFT_UP);
 *		toaster4.setDuration(Duration.seconds(10));
 *		AjaxLink link4 = new AjaxLink(this, "link4"){
 *			public void onClick(AjaxRequestTarget target) {
 *				toaster4.publishMessage(target,DojoToaster.WARNING);
 *				
 *			}
 *		};
 *	}
 *}
 *
 *  </pre>
 * </p>
 * @author <a href="http://www.demay-fr.net/blog/index.php/en">Vincent Demay</a>
 *
 */
public class DojoToaster extends WebMarkupContainer<String>{

	public static final String INFO = "INFO";
	public static final String WARNING = "WARNING";
	public static final String ERROR = "ERROR";
	public static final String FATAL = "FATAL";
	
	private DojoPosition position;
	private Duration duration;
	/**
	 * Construct a new DojoToaster. The message displayed in it is the model
	 * @param parent parent where the toaster will be display
	 * @param id the name of the widget
	 * @param model A String representing the message
	 */
	public DojoToaster(MarkupContainer parent, String id, IModel<String> model){
		super(parent, id, model);
		add(new DojoToasterHandler());
	}

	/**
	 * Construct a new DojoToaster. The message displayed in it is the model
	 * @param parent parent where the toaster will be display
	 * @param id the name of the widget
	 */
	public DojoToaster(MarkupContainer parent, String id){
		super(parent, id);
		add(new DojoToasterHandler());
	}

	@Override
	protected void onComponentTag(ComponentTag tag){
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, DOJO_TYPE_TOASTER);
		if (position != null){
			tag.put("positionDirection", position.getPosition());
		}
		tag.put("messageTopic", getMarkupId());
		tag.put("templateCssPath", urlFor(new ResourceReference(DojoToaster.class, "Toaster.css")));
		if (duration != null){
			tag.put("showDelay", duration.getMilliseconds()+"");
		}
		tag.put("separator", "<hr/>");
	}

	/**
	 * get the position where the toaster will be displayed
	 * @return the position where the toaster will be displayed
	 */
	public DojoPosition getPosition(){
		return position;
	}

	/**
	 * set the position where the toaster will be displayed
	 * @param position the position where the toaster will be displayed
	 */
	public void setPosition(DojoPosition position){
		this.position = position;
	}
	
	/**
	 * Show the massage
	 * @param target ajax target
	 */
	public void publishMessage(AjaxRequestTarget target){
		target.appendJavascript("dojo.event.topic.publish(\"" + getMarkupId() + "\",\"" + getModelObject() + "\")");
	}

	/**
	 * get the duration
	 * @return duration see {@link Duration}
	 */
	public Duration getDuration(){
		return duration;
	}

	/**
	 * set the direction
	 * @param duration duration see {@link Duration}
	 */
	public void setDuration(Duration duration){
		this.duration = duration;
	}
	
	/**
	 * Show the massage
	 * @param target ajax target
	 */
	public void publishMessage(AjaxRequestTarget target, String type){
		target.appendJavascript("dojo.event.topic.publish(\"" + getMarkupId() + "\",{message:\"" + getModelObject() + "\",type:\"" + type + "\"})");
	}

}
