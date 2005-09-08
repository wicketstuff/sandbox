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
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import wicket.WicketRuntimeException;
import wicket.resource.DynamicByteArrayResource;

/**
 * Base class for jasper reports resources.
 * 
 * @author Eelco Hillenius
 */
public abstract class JasperReportsResource extends DynamicByteArrayResource
{
	/**
	 * Provides JDBC connection.
	 */
	public static interface IDatabaseConnectionProvider
	{
		/**
		 * Gets a JDBC connection to use when filling the report.
		 * 
		 * @return a JDBC connection
		 */
		Connection get();

		/**
		 * Called when the report is generated and the connection can be
		 * released again.
		 */
		void release();
	}

	/** the compiled report this resource references. */
	private JasperReport jasperReport;

	/** the report parameters. */
	private Map reportParameters;

	/** the datasource if any for filling this report. */
	private JRDataSource reportDataSource;

	/** the connection provider if any for filling this report. */
	private IDatabaseConnectionProvider connectionProvider;

	/**
	 * Construct without a report. You must provide a report before you can use
	 * this resource.
	 */
	public JasperReportsResource()
	{
		super();
		setCacheable(false);
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JasperReportsResource(InputStream report)
	{
		super();
		setCacheable(false);
		try
		{
			jasperReport = (JasperReport) JRLoader.loadObject(report);
		}
		catch (JRException e)
		{
			throw new WicketRuntimeException(e);
		}
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JasperReportsResource(URL report)
	{
		super();
		setCacheable(false);
		try
		{
			jasperReport = (JasperReport) JRLoader.loadObject(report);
		}
		catch (JRException e)
		{
			throw new WicketRuntimeException(e);
		}
	}

	/**
	 * Construct.
	 * 
	 * @param report
	 *            the report input stream
	 */
	public JasperReportsResource(File report)
	{
		super();
		setCacheable(false);
		try
		{
			jasperReport = (JasperReport) JRLoader.loadObject(report);
		}
		catch (JRException e)
		{
			throw new WicketRuntimeException(e);
		}
	}

	/**
	 * Gets jasperReport.
	 * 
	 * @return jasperReport
	 */
	public JasperReport getJasperReport()
	{
		return jasperReport;
	}

	/**
	 * Sets {bjasperReport.
	 * 
	 * @param jasperReport
	 *            jasperReport
	 */
	public final void setJasperReport(JasperReport jasperReport)
	{
		this.jasperReport = jasperReport;
	}

	/**
	 * Gets the report parameters.
	 * 
	 * @return report parameters
	 */
	public Map getReportParameters()
	{
		return reportParameters;
	}

	/**
	 * Sets the report parameters.
	 * 
	 * @param reportParameters
	 *            report parameters
	 * @return This
	 */
	public final JasperReportsResource setReportParameters(Map reportParameters)
	{
		this.reportParameters = reportParameters;
		return this;
	}

	/**
	 * Gets the datasource if any for filling this report.
	 * 
	 * @return the datasource if any for filling this report
	 */
	public JRDataSource getReportDataSource()
	{
		return reportDataSource;
	}

	/**
	 * Sets the datasource if any for filling this report.
	 * 
	 * @param reportDataSource
	 *            the datasource if any for filling this report
	 * @return This
	 */
	public JasperReportsResource setReportDataSource(JRDataSource reportDataSource)
	{
		this.reportDataSource = reportDataSource;
		return this;
	}

	/**
	 * Gets the connection provider if any for filling this report.
	 * 
	 * @return the connection provider if any for filling this report
	 */
	public IDatabaseConnectionProvider getConnectionProvider()
	{
		return connectionProvider;
	}

	/**
	 * Sets the connection provider if any for filling this report.
	 * 
	 * @param connectionProvider
	 *            the connection provider if any for filling this report
	 * @return This
	 */
	public final JasperReportsResource setConnectionProvider(
			IDatabaseConnectionProvider connectionProvider)
	{
		this.connectionProvider = connectionProvider;
		return this;
	}

	/**
	 * Creates a new {@link JasperPrint} instance. This instance is specific for
	 * this render, but it not yet designated for one output format only.
	 * 
	 * @return a new {@link JasperPrint} instance.
	 * @throws JRException
	 */
	protected JasperPrint newJasperPrint() throws JRException
	{
		final JasperPrint jasperPrint;
		JasperReport jasperReport = getJasperReport();
		Map reportParameters = getReportParameters();
		JRDataSource reportDataSource = getReportDataSource();
		if (reportDataSource != null)
		{
			jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters,
					reportDataSource);
		}
		else
		{
			IDatabaseConnectionProvider connectionProvider = getConnectionProvider();
			
			try
			{
				connectionProvider = getConnectionProvider();
				if (connectionProvider == null)
				{
				}
				jasperPrint = JasperFillManager.fillReport(jasperReport, reportParameters,
						reportDataSource);
			}
			finally
			{
				
			}
		}
		return jasperPrint;
	}
}
