/*
 * $Id$ $Revision$
 * $Date$
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
package wicket.contrib.markup.html.form.fvalidate;

import wicket.markup.ComponentTag;
import wicket.markup.html.form.TextField;
import wicket.model.IModel;

/**
 * Textfield that uses javascript validation.
 * 
 * @author Eelco Hillenius
 */
public class FValidateTextField extends TextField
{
	/** spec resolver. */
	private IValidationSpecResolver validationSpecResolver =
		new ValidationSpecResolver();

	/**
	 * The fValidate 'code' attribute, which defaults (with fValidate as well)
	 * to 'alt'.
	 */
	private String fValidateCode = "alt";

	/**
	 * The fValidate 'message' attribute, which defaults (with fValidate as well)
	 * to 'emsg'.
	 */
	private String fValidateMsg = "emsg";

	/**
	 * Construct.
	 * @param name
	 */
	public FValidateTextField(String name)
	{
		super(name);
	}

	/**
	 * Construct.
	 * @param name
	 * @param type
	 */
	public FValidateTextField(String name, Class type)
	{
		super(name, type);
	}
	/**
	 * Construct.
	 * @param name
	 * @param model
	 * @param type
	 */
	public FValidateTextField(String name, IModel model, Class type)
	{
		super(name, model, type);
	}
	/**
	 * Construct.
	 * @param name
	 * @param object
	 */
	public FValidateTextField(String name, IModel object)
	{
		super(name, object);
	}

	/**
	 * Gets fValidateCode.
	 * @return fValidateCode
	 */
	public final String getFValidateCode()
	{
		return fValidateCode;
	}
	/**
	 * Sets fValidateCode.
	 * @param validateCode fValidateCode
	 */
	public final void setFValidateCode(String validateCode)
	{
		fValidateCode = validateCode;
	}
	/**
	 * Gets fValidateMsg.
	 * @return fValidateMsg
	 */
	public final String getFValidateMsg()
	{
		return fValidateMsg;
	}
	/**
	 * Sets fValidateMsg.
	 * @param validateMsg fValidateMsg
	 */
	public final void setFValidateMsg(String validateMsg)
	{
		fValidateMsg = validateMsg;
	}

	/**
	 * Gets validationSpecResolver.
	 * @return validationSpecResolver
	 */
	protected final IValidationSpecResolver getValidationSpecResolver()
	{
		return validationSpecResolver;
	}

	/**
	 * Sets validationSpecResolver.
	 * @param validationSpecResolver validationSpecResolver
	 */
	protected final void setValidationSpecResolver(
			IValidationSpecResolver validationSpecResolver)
	{
		this.validationSpecResolver = validationSpecResolver;
	}

	/**
	 * Processes the component tag.
	 * @param tag Tag to modify
	 * @see wicket.Component#onComponentTag(ComponentTag)
	 */
	protected final void onComponentTag(final ComponentTag tag)
	{
		checkComponentTag(tag, "input");
		checkComponentTagAttribute(tag, "type", "text");
		super.onComponentTag(tag);

		tag.put("value", getValue());

		ValidationSpec spec = validationSpecResolver.getSpec(this);
		if(spec != null)
		{
			String code = spec.getCode();
			if(code != null)
			{
				tag.put(getFValidateCode(), code);
			}
			String msg = spec.getErrorMsg();
			if(msg != null)
			{
				tag.put(getFValidateMsg(), msg);
			}
		}
	}
}