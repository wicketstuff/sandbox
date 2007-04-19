/*
 * $Id$
 * $Revision$
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
package wicket.contrib.markup.html.freemarker;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.resource.IStringResourceStream;
import org.apache.wicket.util.string.Strings;
/**
 * Panel that displays the result of rendering a FreeMarker template. The
 * template itself can be any IStringResourceStream implementation, of which
 * there are a number of convenient implementations in wicket.util. The model
 * can be any normal Wicket MapModel.
 *
 * @author	Jonas Klingstedt
 */
public final class FreeMarkerPanel extends WebComponent {

	private static final long serialVersionUID = 1L;

	/** Whether to escape HTML characters. The default value is false. */
	private boolean escapeHtml = false;

	/** FreeMarker template resource */
	private final IStringResourceStream templateResource;

	/**
	 * Whether any FreeMarker exception should be trapped and displayed on the
	 * panel (false) or thrown up to be handled by the exception mechanism of
	 * Wicket (true). The default is false, which traps and displays any
	 * exception without having consequences for the other components on the
	 * page.
	 * <p>
	 * Trapping these exceptions without disturbing the other components is
	 * especially usefull in CMS like applications, where 'normal' users are
	 * allowed to do basic scripting. On errors, you want them to be able to
	 * have them correct them while the rest of the application keeps on
	 * working.
	 */
	private boolean throwFreeMarkerExceptions = false;

	/** FreeMarker configuration */
	private SerializableFreeMarkerConfiguration config;
	
	/**
	 * Constructor.
	 *
	 * @param id the component id.
	 * @param templateResource the FreeMarker template as a String resource.
	 * @param model a MapModel with variables that can be substituted by
	 * FreeMarker.
	 *
	 * @see Component
	 */
	public FreeMarkerPanel(final String id,
						   final IStringResourceStream templateResource,
						   final Model model) {
		super(id, model);

		this.templateResource = templateResource;
	
		// Create FreeMarker configuration
		config = new SerializableFreeMarkerConfiguration();
	}

	/**
	 * Gets whether to escape HTML characters.
	 *
	 * @return whether to escape HTML characters.
	 */
	public final boolean getEscapeHtml() {
		return escapeHtml;
	}

	/**
	 * Sets whether to escape HTML characters. The default value is false.
	 *
	 * @param escapeHtml whether to escape HTML characters.
	 * @return this
	 */
	public final FreeMarkerPanel setEscapeHtml(boolean escapeHtml) {
		this.escapeHtml = escapeHtml;

		return this;
	}
	
	/**
	 * Gets whether any FreeMarker exception should be trapped and displayed on
	 * the panel (false) or thrown up to be handled by the exception mechanism
	 * of Wicket (true). The default is true, which traps and displays any
	 * exception without having consequences for the other components on the
	 * page.
	 * 
	 * @return whether any FreeMarker exceptions should be thrown or trapped.
	 */
	public final boolean getThrowFreeMarkerExceptions() {
		return throwFreeMarkerExceptions;
	}

	/**
	 * Sets whether any FreeMarker exception should be trapped and displayed on
	 * the panel (true) or thrown up to be handled by the exception mechanism of
	 * Wicket (false).
	 * 
	 * @param throwFreeMarkerExceptions whether any exception should be trapped 
	 * * or rethrown
	 * @return this
	 */
	public final FreeMarkerPanel setThrowFreeMarkerExceptions(
			boolean throwFreeMarkerExceptions) {
		this.throwFreeMarkerExceptions = throwFreeMarkerExceptions;
		
		return this;
	}

	/**
	 * @see wicket.Component#onComponentTagbody(wicket.markup.MarkupStream,
	 *		wicket.markup.ComponentTag)
	 */
	protected void onComponentTagBody(final MarkupStream markupStream,
									  final ComponentTag openTag) {
		final Reader templateReader = getTemplateReader();

		if (null == templateReader) {
			// Just empty it
			replaceComponentTagBody(markupStream, openTag, "");
		} else {
			// Get model as a map
			final Map map = (Map) getModelObject();
			// Create a writer for capturing the FreeMarker output
			StringWriter writer = new StringWriter();
			
			try {
				// Create a new FreeMarker template
				Template template = new Template(
					getId(), templateReader, config);
		
				// Process the FreeMarker template and capture the output in
				// the writer
				template.process(map, writer);
			
				// Replace the tag's body with the FreeMarker output
				String result = writer.toString();
				
				// Escape the HTML ?
				if (escapeHtml) {
					// Encode the result in order to get valid HTML output that
					// does not break the rest of the page
					result = Strings.escapeMarkup(result).toString();
				}

				// Finally, actually replace the tag body
				replaceComponentTagBody(markupStream, openTag, result);
			} catch (TemplateException e) {
				onException(e, markupStream, openTag);
			} catch (IOException e) {
				onException(e, markupStream, openTag);
			}
		}
	}
	
	/**
	 * Either print or rethrow the throwable.
	 *
	 * @param exception the cause.
	 * @param markupStream the markup stream.
	 * @param openTag the open tag.
	 */
	private void onException(final Exception exception,
							 final MarkupStream markupStream,
							 final ComponentTag openTag) {
		if (!throwFreeMarkerExceptions) {
			// Print the exception on the panel
			String stackTraceAsString = Strings.toString(exception);
			replaceComponentTagBody(markupStream, openTag, stackTraceAsString);
		} else {
			// Rethrow the exception
			throw new WicketRuntimeException(exception);
		}
	}

	/**
	 * Gets a reader for the FreeMarker template.
	 *
	 * @return a reader for the FreeMarker template.
	 */
	private Reader getTemplateReader() {
		final String template = templateResource.asString();

		if (null == template) {
			return null;
		}

		return new StringReader(template);
	}
}
