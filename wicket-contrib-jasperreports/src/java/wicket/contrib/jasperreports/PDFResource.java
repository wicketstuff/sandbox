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

import java.io.InputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import wicket.WicketRuntimeException;
import wicket.resource.DynamicByteArrayResource;

/**
 * TODO docme.
 *
 * @author Eelco Hillenius
 */
public abstract class PDFResource extends DynamicByteArrayResource
{
	/**
	 * default constructor
	 */
	public PDFResource()
	{
		super();
	}

	/**
	 * Gets the data stream.
	 * @return the data stream
	 */
	protected abstract InputStream getReportInputStream();

	/**
	 * Gets the report parameters.
	 * @return report parameters
	 */
	protected abstract Map getReportParameters();

	/**
	 * Gets the datasource if any for filling this report.
	 * @return the datasource if any for filling this report
	 */
	protected abstract JRDataSource getReportDataSource();

	/**
	 * @see wicket.resource.DynamicByteArrayResource#getData()
	 */
	protected byte[] getData()
	{
		try
		{
			InputStream reportInputStream = getReportInputStream();
			Map reportParameters = getReportParameters();
			JRDataSource reportDataSource = getReportDataSource();
			JasperPrint print = JasperFillManager.fillReport(reportInputStream, reportParameters, reportDataSource);
			return JasperExportManager.exportReportToPdf(print);
		}
		catch (JRException e)
		{
			throw new WicketRuntimeException(e);
		}
	}
	
	/**
	 * @see wicket.resource.DynamicByteArrayResource#getContentType()
	 */
	public String getContentType()
	{
		return "application/pdf";
	}
}
