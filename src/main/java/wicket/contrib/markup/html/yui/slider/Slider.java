/*
 * $Id: Slider.java 5132 2006-03-26 02:13:41 -0800 (Sun, 26 Mar 2006)
 * jdonnerstag $ $Revision: 5189 $ $Date: 2006-03-26 02:13:41 -0800 (Sun, 26 Mar
 * 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.markup.html.yui.slider;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.Application;
import wicket.AttributeModifier;
import wicket.Component;
import wicket.IInitializer;
import wicket.behavior.HeaderContributor;
import wicket.contrib.markup.html.yui.AbstractYuiPanel;
import wicket.extensions.util.resource.TextTemplateHeaderContributor;
import wicket.markup.html.PackageResource;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.image.Image;
import wicket.markup.html.internal.HtmlHeaderContainer;
import wicket.model.AbstractReadOnlyModel;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.model.PropertyModel;
import wicket.util.collections.MiniMap;

/**
 * Slider component based on the Slider of Yahoo UI Library.
 * 
 * @author Eelco Hillenius
 */
public class Slider extends AbstractYuiPanel
{
	private static final long serialVersionUID = 1L;

    private Log log = LogFactory.getLog(Slider.class);
	/**
	 * Initializer for this component; binds static resources.
	 */
	public final static class ComponentInitializer implements IInitializer
	{
		/**
		 * @see wicket.IInitializer#init(wicket.Application)
		 */
		public void init(Application application)
		{
			// register all javascript files
			PackageResource.bind(application, Slider.class, Pattern.compile(".*\\.js"));
			// images
			PackageResource.bind(application, Slider.class, Pattern.compile(".*\\.gif|.*\\.png"),
					true);
			// and a css
			PackageResource.bind(application, Slider.class, "css/slider.css");
		}
	}

	/**
	 * The id of the background element.
	 */
	private String backgroundElementId;

	/**
	 * The id of the image element.
	 */
	private String imageElementId;

	/**
	 * The JavaScript variable name of the slider component.
	 */
	private String javaScriptId;

    /**
     * TODO : maybe abstract out to "length" and "interval" ??
     * 
     */
    private Integer leftUp;
    private Integer rightDown;
    private Integer tick;
    
    /**
     * Contruct. 
     * 
     * @param id
     * @param model
     * @param leftUp
     * @param rightDown
     * @param tick
     * @param element
     */
    public Slider(String id, IModel model, 
            final Integer leftUp, final Integer rightDown, final Integer tick,
            final FormComponent element)
    {
        this(id, model, element,  new SliderSettings(leftUp, rightDown, tick));
    }
	/**
	 * Construct.
	 * 
	 * @param id
	 *            the component id
	 * @param model
	 *            the model for this component
	 */
	public Slider(String id, IModel model, 
                    final FormComponent element, 
                    final SliderSettings settings)
	{
		super(id, model);
        
		add(HeaderContributor.forJavaScript(Slider.class, "slider.js"));
		add(HeaderContributor.forCss(Slider.class, "css/slider.css"));

        /*
         * default settings if null
         */
        
        if (settings == null) {
            // error 
        }
        
        /* handle form element */
        if (element != null) {
            element.add(new AttributeModifier("id", true, new AbstractReadOnlyModel() {
                private static final long serialVersionUID = 1L;

                public Object getObject(Component component) {
                    return element.getId();
                }
            }));
        }
        
		IModel variablesModel = new AbstractReadOnlyModel()
		{
			private static final long serialVersionUID = 1L;

			/** cached variables; we only need to fill this once. */
			private Map variables;

			/**
			 * @see wicket.model.AbstractReadOnlyModel#getObject(wicket.Component)
			 */
			public Object getObject(Component component)
			{
				if (variables == null)
				{
					this.variables = new MiniMap(7);
					variables.put("javaScriptId", javaScriptId);
					variables.put("backGroundElementId", backgroundElementId);
					variables.put("imageElementId", imageElementId);
                    variables.put("leftUp", settings.getLeftUp());
                    variables.put("rightDown", settings.getRightDown());
                    variables.put("tick", settings.getTick());
                    variables.put("formElementId", element.getId());
				}
				return variables;
			}
		};

		add(TextTemplateHeaderContributor.forJavaScript(Slider.class, "init.js", variablesModel));

        /*
         * Corner Images
         */
        
        Image leftImg = new Image("leftImg", settings.getLeftCornerResource());
        leftImg.add(new AttributeModifier("onclick", true, new AbstractReadOnlyModel() {
           public Object getObject(Component component) {
               return javaScriptId + ".moveThumb(0)";
           }
        }));
        add(leftImg);
        
        Image rightImg = new Image("rightImg", settings.getRightCornerResource());
        rightImg.add(new AttributeModifier("onclick", true, new AbstractReadOnlyModel() {
            public Object getObject(Component component) {
                // return javaScriptId + ".moveThumb(this.x)";
                return javaScriptId + ".moveThumb(YAHOO.util.Dom.getX("+ getImageElementId() +") + " + settings.getTickSize() + ");";
            } 
        }));
        add(rightImg);
       
        /* 
         * Background Div 
         */
        
		WebMarkupContainer backgroundElement = new WebMarkupContainer("backgroundDiv");
		backgroundElement.add(new AttributeModifier("id", true, new PropertyModel(this, "backgroundElementId")));
        backgroundElement.add(new AttributeModifier("style", true, new Model(settings.getBackground().getStyle())));
        add(backgroundElement);

        /* 
         * Element Div and Thumb Div 
         */
        
		WebMarkupContainer imageElement = new WebMarkupContainer("handleDiv");
		imageElement.add(new AttributeModifier("id", true, new PropertyModel(this, "imageElementId")));
        imageElement.add(new AttributeModifier("style", true, new Model(settings.getHandle().getStyle())));
        
        WebMarkupContainer thumbElement = new WebMarkupContainer("thumbDiv");
        thumbElement.add(new AttributeModifier("style", true, new Model(settings.getThumb().getStyle())));
        
        imageElement.add(thumbElement);
        backgroundElement.add(imageElement);
        
	}

	/**
	 * @see wicket.Component#renderHead(wicket.markup.html.internal.HtmlHeaderContainer)
	 */
	public void renderHead(HtmlHeaderContainer container)
	{
		((WebPage)getPage()).getBodyContainer().addOnLoadModifier("init" + javaScriptId + "();");
		super.renderHead(container);
	}

	/**
	 * Gets backgroundElementId.
	 * 
	 * @return backgroundElementId
	 */
	public final String getBackgroundElementId()
	{
		return backgroundElementId;
	}

	/**
	 * Gets imageElementId.
	 * 
	 * @return imageElementId
	 */
	public final String getImageElementId()
	{
		return imageElementId;
	}

	/**
	 * TODO implement
	 */
	public void updateModel()
	{
        log.info("updateModel");
	}

	/**
	 * @see wicket.Component#onAttach()
	 */
	protected void onAttach()
	{
		super.onAttach();

		// initialize lazily
		if (backgroundElementId == null)
		{
			// assign the markup id
			String id = getMarkupId();
			backgroundElementId = id + "Bg";
			imageElementId = id + "Img";
			javaScriptId = backgroundElementId + "JS";
		}
	}
    
    public Integer getLeftUp() 
    {
        return leftUp;
    }

    public void setLeftUp(Integer leftUp) 
    {
        this.leftUp = leftUp;
    }

    public Integer getRightDown() 
    {
        return rightDown;
    }

    public void setRightDown(Integer rightDown) 
    {
        this.rightDown = rightDown;
    }

    public Integer getTick() 
    {
        return tick;
    }

    public void setTick(Integer tick) 
    {
        this.tick = tick;
    }
}
