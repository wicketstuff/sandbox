package org.wicketstuff.dojo.markup.html.container.layout;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.dojo.AbstractRequireDojoBehavior;
import org.wicketstuff.dojo.DojoIdConstants;
import org.wicketstuff.dojo.markup.html.container.AbstractDojoContainer;
import org.wicketstuff.dojo.markup.html.container.IDojoContainer;
import org.wicketstuff.dojo.widgets.StyleAttribute;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.Model;

/**
 * A container allowing user to layout a page.
 * <p>
 * 	Layout is done by setting position when adding a child.<br/>
 *  <b>WARNING</b> children have to be of type {@link IDojoContainer}
 * </p>
 * <p>
 * This container size is 100% x 100% by default. You can change default size
 * using setWidth and setHeight. To share inner container size set width or height
 * on children. The space left will be used by the {@link Position#Client} child container
 * </p>
 * <p>
 * 	Each added child (see {@link #add(AbstractDojoContainer, wicket.contrib.dojo.markup.html.container.layout.DojoLayoutContainer.Position)} )
 *  will be placed according to the specified {@link Position}, and using the order specified in the HTML markup.
 * </p>
 * <p>
 *  <b>Example :</b> 
 *  <pre>
 *  	new DojoLayoutContainer("main")
 *  		.add(new DojoSimpleContainer("left"), Position.Left)
 *  		.add(new DojoSimpleContainer("top"), Position.Top)
 *  		.add(new DojoSimpleContainer("bottom"), Position.Bottom)
 *  		.add(new DojoSimpleContainer("left1"), Position.Left1)
 *  		.add(new DojoSimpleContainer("right1"), Position.Right)
 *  		.add(new DojoSimpleContainer("client), Position.Client)
 *  </pre>
 *  <pre>
 *  	&lt;div wicket:id="main">
 *  		&ltdiv wicket:id="left">left&lt;/div>
 *  		&ltdiv wicket:id="top">top&lt;/div>
 *  		&ltdiv wicket:id="bottom">bottom&lt;/div>
 *  		&ltdiv wicket:id="left1">left1&lt;/div>
 *  		&ltdiv wicket:id="right1">right1&lt;/div>
 *  		&ltdiv wicket:id="client">client&lt;/div>
 *  	&lt;/div>
 *  </pre>
 *  
 *  Will be rendered as : 
 *  
 *  ,------------------------------------------------------------------,
 *  |           |                        top                           |
 *  |           |                                                      |
 *  |           | ---------------------------------------------------- |
 *  |           |           |                             |            |
 *  |           |           |                             |            |
 *  |           |           |                             |            |
 *  |           |           |                             |            |
 *  |           |           |                             |            |
 *  |           |           |                             |            |
 *  |   left    |   left1   |                             |   right1   |
 *  |           |           |          client             |            |
 *  |           |           |                             |            |
 *  |           |           |                             |            |
 *  |           |           |                             |            |
 *  |           |           |                             |            |
 *  |           |           |                             |            |
 *  |           |           |                             |            |
 *  |           | ---------------------------------------------------- |
 *  |           |                                                      |
 *  |           |                     bottom                           |
 *  '------------------------------------------------------------------'
 * </p>
 * 
 * @author Vincent Demay
 *
 */
@SuppressWarnings("serial")
public class DojoLayoutContainer extends AbstractDojoContainer{

	/**
	 * A position enum type allowing to set the position of a child 
	 * container.<br/>
	 * <p>
	 * Left, Right, Bottom, Top, will put the child element on the given
	 * position in the current stream (stream is given by html child order)
	 * <br/>
	 * Client will use left space to be render. Usually used as last 
	 * child in the stream
	 * </p>
	 * see {@link DojoLayoutContainer}
	 * 
	 * @author Vincent Demay
	 *
	 */
	public enum Position{
		Left("left"),
		Right("right"),
		Top("top"),
		Bottom("bottom"),
		Client("client");
		
		private final String label;
		
		Position(String label){
			this.label = label;
		}
		
		/**
		 * return a model of the postion
		 * @return model of the position
		 */
		public Model getLabel(){
			return new Model(this.label);
		}
	}
	
	/**
	 * Contructor.
	 * No model can be associted to this component. Its only goal is to
	 * make some layout
	 * @param id
	 */
	public DojoLayoutContainer(String id) {
		super(id);
		add(new DojoLayoutContainerHandler());
		setWidth("100%");
		setHeight("100%");
	}
	
	/**
	 * @see org.wicketstuff.dojo.IDojoWidget#getDojoType()
	 */
	public String getDojoType()
	{
		return DojoIdConstants.DOJO_TYPE_LAYOUTCONTAINER;
	}
	
	/**
	 * Add a default overflow hidden because of scrolling when 
	 * calculating the layout
     */
	protected void onStyleAttribute(StyleAttribute styleAttribute){
		styleAttribute.put("overflow", "hidden");
	}
	
	/**
	 * Add a {@link AbstractDojoContainer} as child of this one. Position in layout is given
	 * by pos attribute : see {@link Position}
	 * <p>
	 * This method has the same behavior as {@link #add(Component)}
	 * </p>
	 * @param child a child container in the layout
	 * @param pos position of the child in the layout
	 * @return this
	 */
	public final DojoLayoutContainer add(final AbstractDojoContainer child, Position pos){
		child.add(new AttributeModifier("layoutAlign", true, pos.getLabel()));
		add(child);
		return this;
	}

	/**
	 * A handler for {@link DojoLayoutContainer}
	 * @author Vincent Demay
	 *
	 */
	private class DojoLayoutContainerHandler extends AbstractRequireDojoBehavior
	{
		@Override
		public void setRequire(RequireDojoLibs libs)
		{
			libs.add("dojo.widget.LayoutContainer");
		}

		@Override
		protected void respond(AjaxRequestTarget target) {
			//Never Called : no callback on this component
		}
		
		public void onComponentReRendered(AjaxRequestTarget ajaxTarget) {
			ajaxTarget.appendJavascript("dojo.widget.byId('" + getComponent().getMarkupId() + "').onResized()");
		}
	}

}
