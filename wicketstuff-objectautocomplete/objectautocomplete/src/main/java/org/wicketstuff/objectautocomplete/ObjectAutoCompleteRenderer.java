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
package org.wicketstuff.objectautocomplete;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.Response;
import org.apache.wicket.util.lang.PropertyResolver;

/**
 * @author roland
 * @since May 20, 2008
 */
public class ObjectAutoCompleteRenderer<T> implements IAutoCompleteRenderer<T> {

    private static final long serialVersionUID = 1L;

    private static final IAutoCompleteRenderer INSTANCE = new ObjectAutoCompleteRenderer();

    @SuppressWarnings("unchecked")
    public static final <T> IAutoCompleteRenderer<T> instance() {
        return INSTANCE;
    }

    private String idProperty = "id";


    public final void render(T object, Response response, String criteria)
	{
		String textValue = getTextValue(object);
        if (textValue == null)
		{
			throw new IllegalStateException(
				"A call to textValue(Object) returned an illegal value: null for object: " +
					object.toString());
		}
		textValue = textValue.replaceAll("\\\"", "&quot;");

        String idValue = getIdValue(object);
        if (idValue == null) {
			throw new IllegalStateException(
				"A call to idValue(Object) returned an illegal value: null for object: " +
					object.toString());
        }
        response.write("<li idvalue=\"" + idValue + "\" textvalue=\"" + textValue + "\">");
		renderChoice(object, response, criteria);
		response.write("</li>");
	}

    /**
     * Get id of the object to render. By default this is extracted by reflection using the
     * {@link #idProperty}, but can be overwritten by a subclass
     * @param object object from which to extract the id
     * @return the id value
     * @throws IllegalArgumentException if no id could be extracted
     */
    protected String getIdValue(T object) {
        Object returnValue = PropertyResolver.getValue(idProperty,object);
        if (returnValue == null) {
            throw new IllegalArgumentException("Id property " + idProperty +
                    " could not be extracted from obect " + object);
        }
        return returnValue.toString();
    }

    public final void renderHeader(Response response)
	{
		response.write("<ul>");
	}

	public final void renderFooter(Response response)
	{
		response.write("</ul>");
	}

	/**
	 * Render the visual portion of the assist. Usually the html representing the assist choice
	 * object is written out to the response use {@link Response#write(CharSequence)}
	 *
	 * @param object
	 *            current assist choice
	 * @param response
	 * @param criteria
	 */
	protected void renderChoice(T object, Response response, String criteria)
	{
		response.write(getTextValue(object));
	}

	/**
	 * Retrieves the text value that will be set on the textbox if this assist is selected
	 * Can be overwritten, by default it returns the object's toString()\
     *
	 * @param object
	 *            assist choice object
	 * @return the text value that will be set on the textbox if this assist is selected
	 */
    protected String getTextValue(T object) {
        return object.toString();
    }

    /**
     * Set the property for extracting the id of an object
     *
     * @param pIdProperty property name of the object id
     */
    public void setIdProperty(String pIdProperty) {
        this.idProperty = pIdProperty;
    }
}
