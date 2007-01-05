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
package wicket.contrib.dojo.markup.html.form.validation;

import java.io.Serializable;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.Response;
import wicket.WicketRuntimeException;
import wicket.ajax.AjaxRequestTarget;
import wicket.behavior.AbstractAjaxBehavior;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.markup.html.IHeaderResponse;
import wicket.markup.html.form.FormComponent;
import wicket.model.Model;

/**
 * Ajaxhandler to be bound to FormComponents.<br/> This handler executes wicket
 * validation on set event (e.g. onblur, onchange) with an Ajax server call.<br/>
 * The Formcomponent is highlighted based on the validation result. Example:
 * TextField's background will turn red if validation fails. For usage tutorials
 * see: TODO link to tutorials.
 * 
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 * FIXME : valid and invalid rgb does not work for the moment
 */
public class FXValidationAjaxHandler extends AbstractRequireDojoBehavior
{
	/** name event, like onblur. */
	private final String eventName;

	/** component this handler is attached to. */
	private FormComponent formComponent;

	private final RGB invalidRGB;
	/** not mandatory, if not set validRGB will be components background color */
	private RGB validRGB;

	private String componentId;

	/**
	 * Default constructor which uses node's current background color when
	 * component is valid.
	 * 
	 * @param eventName
	 * @see #eventName
	 */
	public FXValidationAjaxHandler(String eventName)
	{
		if (eventName == null)
		{
			throw new NullPointerException("argument eventName must be not null");
		}
		this.eventName = eventName;
		this.invalidRGB = RGB.DEFAULT_INVALID;
	}

	/**
	 * Constructor which sets default valid highlight color
	 * 
	 * @param eventName
	 * @see #eventName
	 * @param colorValid
	 *            True if default highlight color should be used in stead of
	 *            node's current background color when component is valid.
	 */
	public FXValidationAjaxHandler(String eventName, boolean colorValid)
	{
		if (eventName == null)
		{
			throw new NullPointerException("argument eventName must be not null");
		}
		this.eventName = eventName;
		this.invalidRGB = RGB.DEFAULT_INVALID;
		this.validRGB = RGB.DEFAULT_VALID;
	}

	/**
	 * Constructor with custom invalid RGB values.
	 * 
	 * @param eventName
	 * @see #eventName
	 * @param r
	 *            int representing Red value for this.invalidRGB
	 * @param g
	 *            int representing Green value for this.invalidRGB
	 * @param b
	 *            int representing Blue value for this.invalidRGB
	 */
	public FXValidationAjaxHandler(String eventName, int r, int g, int b)
	{
		if (eventName == null)
		{
			throw new NullPointerException("argument eventName must be not null");
		}
		this.eventName = eventName;
		this.invalidRGB = new RGB(r, g, b);
	}

	/**
	 * Constructor with custom invalid RGB values and valid RGB values.
	 * 
	 * @param eventName
	 * @see #eventName
	 * @param ir
	 *            int representing Red value for this.invalidRGB
	 * @param ig
	 *            int representing Green value for this.invalidRGB
	 * @param ib
	 *            int representing Blue value for this.invalidRGB
	 * @param vr
	 *            int representing Red value for this.validRGB
	 * @param vg
	 *            int representing Green value for this.validRGB
	 * @param vb
	 *            int representing Blue value for this.validRGB
	 */
	public FXValidationAjaxHandler(String eventName, int ir, int ig, int ib, int vr, int vg, int vb)
	{
		if (eventName == null)
		{
			throw new NullPointerException("argument eventName must be not null");
		}
		this.eventName = eventName;
		this.validRGB = new RGB(ir, ig, ib);
		this.invalidRGB = new RGB(vr, vg, vb);
	}

	/**
	 * Write the validate/highlight javascript function to the page's head.
	 * @param r 
	 * 
	 * @see AbstractAjaxBehavior#onRenderHeadContribution(Response response)
	 * FIXME : fader does not work on invalid state
	 */
	public final void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		String s = "";
		s += "<script language='JavaScript' type='text/javascript'>\n";
		s += "	var " + componentId + "_first = false; \n";
		s += "	function " + componentId + "_validate(type) { \n";
		s += "		var startbc\n;";
		s += "		if(!" + componentId + "_first){\n" ;
		s += "			" + componentId + "_first = true; \n";
		s += "			startbc = dojo.html.getBackgroundColor('" + componentId + "');\n";
		s += "		}\n";
		s += "		if (type=='valid'){\n";
		s += "			dojo.lfx.html.unhighlight('" + componentId + "', [255,255,255] , 300).play()\n";
		s += "		}else{\n";	
		s += "			dojo.lfx.html.unhighlight('" + componentId + "', [255, 0, 0], 300).play()\n;";
		s += "		}\n";
		s += "	}\n";
		s += "</script>\n";

		response.renderString(s);
	}

	/**
	 * Bind this handler to the FormComponent and set the corresponding HTML id
	 * attribute.
	 * 
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		Component c = getComponent();
		if (!(c instanceof FormComponent))
		{
			throw new WicketRuntimeException("This handler must be bound to FormComponents");
		}


		this.formComponent = (FormComponent)c;
		this.componentId = this.formComponent.getId();
		this.formComponent.add(new AttributeModifier("id", true, new Model<String>(this.formComponent
				.getId())));

		this.formComponent.add(new AttributeModifier(eventName,true,new Model(){
			public java.lang.Object getObject(){
			     return "javascript:"
					+ "var wcall=wicketAjaxGet('" + getCallbackUrl() + "&" + formComponent.getInputName() + "=' + this.value, function() { }, function() { });return !wcall;";
			   }}));
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		formComponent.validate();	
		if (!formComponent.isValid())
		{
			target.appendJavascript(componentId + "_validate('invalid')");
		}
		else{
			target.appendJavascript(componentId + "_validate('valid')");
		}
	}
	
	/**
	 * Let subclasses define their very own duration.
	 * 
	 * @return duration
	 */
	protected int getDuration()
	{
		return 300;
	}

	/**
	 * Inner class for structured storage of colors using simple RGB values.
	 * 
	 * @author Marco van de Haar
	 * @author Ruud Booltink
	 */
	private static class RGB implements Serializable
	{
		private final int R;
		private final int G;
		private final int B;

		/** Create new RGB representing the default color for valid components */
		public static final RGB DEFAULT_VALID = new RGB(152, 194, 125);
		/** Create new RGB representing the default color for invalid components */
		public static final RGB DEFAULT_INVALID = new RGB(252, 134, 130);

		/**
		 * Construct an RGB with given R, G and B values.
		 * 
		 * @param R
		 * @param G
		 * @param B
		 */
		public RGB(int R, int G, int B)
		{
			this.R = R;
			this.G = G;
			this.B = B;

		}

		/**
		 * @return Red value
		 */
		public int getR()
		{
			return R;
		}

		/**
		 * @return Green value
		 */
		public int getG()
		{
			return G;
		}

		/**
		 * @return Blue value
		 */
		public int getB()
		{
			return B;
		}

		/**
		 * return RGB value in a string which Dojo can use (javascript array).
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			return "[" + R + ", " + G + ", " + B + "]";
		}
	}

	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.html.*");
		libs.add("dojo.lfx.*");
	}


}
