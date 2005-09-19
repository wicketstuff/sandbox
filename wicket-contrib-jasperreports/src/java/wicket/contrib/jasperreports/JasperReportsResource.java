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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import wicket.WicketRuntimeException;
import wicket.protocol.http.WebResponse;
import wicket.resource.DynamicByteArrayResource;

/**
 * Base class for jasper reports resources.
 * 
 * @author Eelco Hillenius
 */
public class JasperReportsResource extends DynamicByteArrayResource
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

	/**
	 * Provides the exporter to use.
	 */
	public static interface IExporterFactory extends Serializable
	{
		/**
		 * Gets a new intance of {@link JRAbstractExporter}.
		 * 
		 * @return a new exporter instance
		 */
		JRAbstractExporter newExporter();

		/**
		 * The content type, like 'application/pdf' for pdfs.
		 * 
		 * @return the content type string
		 */
		String getContentType();
	}

	/**
	 * Gets a new PDF exporter factory.
	 * @return a PDF exporter factory
	 */
	public static final IExporterFactory newPdfExporter()
	{
		return new IExporterFactory()
		{
			/**
			 * @see wicket.contrib.jasperreports.JasperReportsResource.IExporterFactory#newExporter()
			 */
			public JRAbstractExporter newExporter()
			{
				return new JRPdfExporter();
			}

			/**
			 * @see wicket.contrib.jasperreports.JasperReportsResource.IExporterFactory#getContentType()
			 */
			public String getContentType()
			{
				return "application/pdf";
			}
		};
	}

	/**
	 * Gets a new RTF exporter factory.
	 * @return a RTF exporter factory
	 */
	public static final IExporterFactory newRtfExporter()
	{
		return new IExporterFactory()
		{
			/**
			 * @see wicket.contrib.jasperreports.JasperReportsResource.IExporterFactory#newExporter()
			 */
			public JRAbstractExporter newExporter()
			{
				return new JRRtfExporter();
			}

			/**
			 * @see wicket.contrib.jasperreports.JasperReportsResource.IExporterFactory#getContentType()
			 */
			public String getContentType()
			{
				return "text/rtf";
			}
		};
	}

	/**
	 * Gets a new HTML exporter factory.
	 * @return a HTML exporter factory
	 */
	public static final IExporterFactory newHtmlExporter()
	{
		return new IExporterFactory()
		{
			/**
			 * @see wicket.contrib.jasperreports.JasperReportsResource.IExporterFactory#newExporter()
			 */
			public JRAbstractExporter newExporter()
			{
				return new JRHtmlExporter();
			}

			/**
			 * @see wicket.contrib.jasperreports.JasperReportsResource.IExporterFactory#getContentType()
			 */
			public String getContentType()
			{
				return "text/html";
			}
		};
	}

	/**
	 * Gets a new text exporter factory.
	 * @param width the page width
	 * @param height the page height
	 * @return a text exporter factory
	 */
	public static final IExporterFactory newTextExporter(final int width, final int height)
	{
		return new IExporterFactory()
		{
			/**
			 * @see wicket.contrib.jasperreports.JasperReportsResource.IExporterFactory#newExporter()
			 */
			public JRAbstractExporter newExporter()
			{
				JRTextExporter exporter = new JRTextExporter();
				exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, new Integer(width));
				exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, new Integer(height));
				return exporter;
			}

			/**
			 * @see wicket.contrib.jasperreports.JasperReportsResource.IExporterFactory#getContentType()
			 */
			public String getContentType()
			{
				return "text/plain";
			}
		};
	}

	/** the compiled report this resource references. */
	private JasperReport jasperReport;

	/** the report parameters. */
	private Map reportParameters;

	/** the datasource if any for filling this report. */
	private JRDataSource reportDataSource;

	/** the connection provider if any for filling this report. */
	private IDatabaseConnectionProvider connectionProvider;

	/** factory that provides exporter instances. */
	private IExporterFactory exporterFactory;

	/**
	 * When set, a header 'Content-Disposition: attachment;
	 * filename="${fileName}"' will be added to the response, resulting in a
	 * download dialog. No magical extensions are added, so you should make sure
	 * the file has the extension you want yourself.
	 */
	private String fileName;

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
	 * Sets the exporter factory. An {@link IExporterFactory} is a factory that
	 * provides intances of {@link JRAbstractExporter} that are used to generate
	 * output. The default factory is a PDF exporter factory.
	 * 
	 * @param exporterFactory
	 *            the exporter factory
	 * @return This
	 */
	public final JasperReportsResource setExporterFactory(IExporterFactory exporterFactory)
	{
		if (exporterFactory == null)
		{
			throw new NullPointerException("argument exporterFactory must be not null");
		}
		this.exporterFactory = exporterFactory;
		return this;
	}

	private final IExporterFactory getExporterFactory()
	{
		if (exporterFactory == null)
		{
			exporterFactory = newPdfExporter();
		}
		return exporterFactory;
	}

	/**
	 * Gets the file name. When set, a header 'Content-Disposition: attachment;
	 * filename="${fileName}"' will be added to the response, resulting in a
	 * download dialog. No magical extensions are added, so you should make sure
	 * the file has the extension you want yourself.
	 * 
	 * @return the file name
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * Sets the file name. When set, a header 'Content-Disposition: attachment;
	 * filename="${fileName}"' will be added to the response, resulting in a
	 * download dialog. No magical extensions are added, so you should make sure
	 * the file has the extension you want yourself.
	 * 
	 * @param fileName
	 *            the file name
	 * @return This
	 */
	public final JasperReportsResource setFileName(String fileName)
	{
		this.fileName = fileName;
		return this;
	}

	/**
	 * @see wicket.resource.DynamicByteArrayResource#getContentType()
	 */
	public String getContentType()
	{
		return getExporterFactory().getContentType();
	}

	/**
	 * Gets the binary data by getting a new instance of JasperPrint and an
	 * exporter for generating the output.
	 * 
	 * @return the binary data
	 * 
	 * @see wicket.resource.DynamicByteArrayResource#getData()
	 */
	protected byte[] getData()
	{
		try
		{
			// get a print instance for exporting
			JasperPrint print = newJasperPrint();

			// get a fresh instance of an exporter for this report
			JRAbstractExporter exporter = getExporterFactory().newExporter();

			// prepare a stream to trap the exporter's output
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

			// execute the export and return the trapped result
			exporter.exportReport();
			return baos.toByteArray();
		}
		catch (JRException e)
		{
			throw new WicketRuntimeException(e);
		}
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
			IDatabaseConnectionProvider connectionProvider = null;
			try
			{
				connectionProvider = getConnectionProvider();
				if (connectionProvider == null)
				{
					throw new IllegalStateException(
							"JasperReportsResources must either have a JRDataSource, "
									+ "or a JDBC Connection provided");
				}
				jasperPrint = JasperFillManager.fillReport(jasperReport,
						reportParameters, connectionProvider.get());
			}
			finally
			{
				if (connectionProvider != null)
				{
					connectionProvider.release();
				}
			}
		}
		return jasperPrint;
	}

	/**
	 * @see wicket.markup.html.WebResource#setHeaders(wicket.protocol.http.WebResponse)
	 */
	protected void setHeaders(WebResponse response)
	{
		super.setHeaders(response);
		String fileName = getFileName();
		if (fileName != null)
		{
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");

		}
	}
}
