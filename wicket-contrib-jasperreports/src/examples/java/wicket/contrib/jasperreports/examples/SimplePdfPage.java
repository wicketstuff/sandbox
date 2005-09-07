/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.jasperreports.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRDataSource;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.jasperreports.PDFReport;
import wicket.contrib.jasperreports.PDFResource;
import wicket.protocol.http.WebApplication;

/**
 * Simple Jasper reports example with PDF output and a jasper reports panel..
 * 
 * @author Eelco Hillenius
 */
public class SimplePdfPage extends WicketExamplePage
{
    /**
     * Constructor.
     */
    public SimplePdfPage()
    {
		try
		{
			ServletContext context = ((WebApplication)getApplication()).getWicketServlet().getServletContext();
			File reportFile = new File(context.getRealPath("/reports/example.jasper"));
			final Map parameters = new HashMap();
			parameters.put("ReportTitle", "Address Report");
			parameters.put("BaseDir", reportFile.getParentFile());
			final FileInputStream inputStream = new FileInputStream(reportFile);
			PDFResource pdfResource = new PDFResource()
			{
				protected InputStream getReportInputStream()
				{
					return inputStream;
				}

				protected Map getReportParameters()
				{
					return parameters;
				}

				protected JRDataSource getReportDataSource()
				{
					return null;
				}
			};
			add(new PDFReport("report", pdfResource));
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
    }
}