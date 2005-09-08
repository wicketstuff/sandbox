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
package wicket.contrib.jasperreports;

import wicket.IResourceListener;
import wicket.markup.ComponentTag;
import wicket.markup.html.WebComponent;

/**
 * Component for displaying a jasper report. Must be attached to either a frame-
 * or an iframe tag.
 * 
 * @author Eelco Hillenius
 */
public final class JasperReportsPDFReport extends WebComponent implements
		IResourceListener
{
	/** the report resource. */
	private final JasperReportsResource resource;

	/**
	 * Construcxt.
	 * 
	 * @param id
	 *            component id
	 * @param resource
	 *            the resource
	 */
	public JasperReportsPDFReport(String id, JasperReportsResource resource)
	{
		super(id);
		this.resource = resource;
	}

	/**
	 * @see wicket.Component#onComponentTag(wicket.markup.ComponentTag)
	 */
	protected void onComponentTag(ComponentTag tag)
	{
		if ((!tag.getName().equalsIgnoreCase("frame"))
				&& (!tag.getName().equalsIgnoreCase("iframe")))
		{
			findMarkupStream().throwMarkupException(
					"Component "
							+ getId()
							+ " must be applied to a tag of type 'frame' or 'iframe'"
							+ "', not " + tag.toUserDebugString());
		}
		String url = urlFor(IResourceListener.class);
		tag.put("src", getResponse().encodeURL(url));
		super.onComponentTag(tag);
	}

	/**
	 * @see wicket.IResourceListener#onResourceRequested()
	 */
	public void onResourceRequested()
	{
		resource.onResourceRequested();
	}
}