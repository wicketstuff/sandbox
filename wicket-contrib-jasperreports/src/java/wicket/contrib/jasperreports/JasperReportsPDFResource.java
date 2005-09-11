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

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import wicket.WicketRuntimeException;

/**
 * TODO docme.
 * 
 * @author Eelco Hillenius
 */
public class JasperReportsPDFResource extends JasperReportsResource
{
	/**
	 * Construct without a report. You must provide a report before you can use
	 * this resource.
	 */
	public JasperReportsPDFResource()
	{
		super();
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JasperReportsPDFResource(InputStream report)
	{
		super(report);
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JasperReportsPDFResource(URL report)
	{
		super(report);
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JasperReportsPDFResource(File report)
	{
		super(report);
	}

	/**
	 * @see wicket.resource.DynamicByteArrayResource#getContentType()
	 */
	public String getContentType()
	{
		return "application/pdf";
	}

	/**
	 * @see wicket.resource.DynamicByteArrayResource#getData()
	 */
	protected byte[] getData()
	{
		try
		{
			JasperPrint print = newJasperPrint();
			return JasperExportManager.exportReportToPdf(print);
		}
		catch (JRException e)
		{
			throw new WicketRuntimeException(e);
		}
	}
}
