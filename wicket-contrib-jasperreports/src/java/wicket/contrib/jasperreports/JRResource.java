/*
 * $Id$
 * $Revision$ $Date$
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import wicket.WicketRuntimeException;
import wicket.markup.html.DynamicWebResource;
import wicket.protocol.http.WebResponse;

/**
 * Base class for jasper reports resources.
 *
 * @author Eelco Hillenius
 * @author Matej Knopp
 * @author Justin Lee
 */
public abstract class JRResource extends DynamicWebResource {
    private static final Log log = LogFactory.getLog(JRResource.class);

    /**
     * factory for delayed report creation.
     */
    private JasperReportFactory jasperReportFactory;

    /**
     * The compiled report this resource references. Made transient as we don't want our report to be serialized while
     * we can recreate it at other servers at will using the factory.
     */
    private transient JasperReport jasperReport;

    /**
     * the report parameters.
     */
    private Map reportParameters;

    /**
     * When set, a header 'Content-Disposition: attachment; filename="${fileName}"' will be added to the response,
     * resulting in a download dialog. No magical extensions are added, so you should make sure the file has the
     * extension you want yourself.
     */
    private String fileName;

    /**
     * Construct without a report. You must provide a report before you can use this resource.
     */
    public JRResource() {
        super();
        setCacheable(false);
    }

    /**
     * Construct.
     *
     * @param report the report input stream.  This stream must be the compiled .jasper file.
     */
    public JRResource(final InputStream report) {
        this(new JasperReportFactory() {
            public JasperReport newJasperReport() throws JRException {
                return (JasperReport)JRLoader.loadObject(report);
            }
        });
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRResource(final JasperReport report) {
        this(new JasperReportFactory() {
            public JasperReport newJasperReport() {
                return report;
            }
        });
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRResource(final URL report) {
        this(new JasperReportFactory() {
            public JasperReport newJasperReport() throws JRException {
                return (JasperReport)JRLoader.loadObject(report);
            }
        });
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRResource(final File report) {
        this(new JasperReportFactory() {
            @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
            public JasperReport newJasperReport() throws JRException {
                try {
                    if(report.getName().endsWith("xml")) {
                        return JasperCompileManager.compileReport(new FileInputStream(report));
                    }
                    return (JasperReport)JRLoader.loadObject(report);
                } catch(FileNotFoundException e) {
                    throw new JRException(e.getMessage());
                }
            }
        });
    }

    /**
     * Construct.
     *
     * @param factory report factory for lazy initialization
     */
    private JRResource(JasperReportFactory factory) {
        super();
        setCacheable(false);
        jasperReportFactory = factory;
    }

    /**
     * Gets jasperReport. This implementation uses an internal factory to lazily create the report. After creation the
     * report is cached (set as the jasperReport property). Override this method in case you want to provide some
     * alternative creation/ caching scheme.
     *
     * @return jasperReport
     */
    public JasperReport getJasperReport() {
        // if the report has not yet been initialized and can be, initialize it
        if(jasperReport == null && jasperReportFactory != null) {
            try {
                setJasperReport(jasperReportFactory.newJasperReport());
            } catch(JRException e) {
                throw new WicketRuntimeException(e);
            }
        }
        return jasperReport;
    }

    /**
     * Sets {bjasperReport.
     *
     * @param report report
     */
    public final void setJasperReport(JasperReport report) {
        jasperReport = report;
    }

    /**
     * Gets the report parameters.
     *
     * @return report parameters
     */
    public Map getReportParameters() {
        return reportParameters;
    }

    /**
     * Sets the report parameters.
     *
     * @param params report parameters
     *
     * @return This
     */
    public final JRResource setReportParameters(Map params) {
        reportParameters = params;
        return this;
    }

    /**
     * Gets the datasource if any for filling this report.
     *
     * @return the datasource if any for filling this report
     */
    public JRDataSource getReportDataSource() {
        return null;
    }

    /**
     * Gets the connection provider if any for filling this report.  This should be overridden if needed.  It is
     * not a good idea to store references to this provider as Wicket will try to serialize this to the session and
     * you most likely don't want a hibernate session or JDBC connection to be serialized.
     *
     * @return the connection provider if any for filling this report
     */
    public IDatabaseConnectionProvider getConnectionProvider() {
        return null;
    }

    /**
     * Gets the file name. When set, a header 'Content-Disposition: attachment; filename="${fileName}"' will be added to
     * the response, resulting in a download dialog. No magical extensions are added, so you should make sure the file
     * has the extension you want yourself.
     *
     * @return the file name
     */
    public String getFileName() {
        if(fileName == null) {
            fileName = getJasperReport().getName() + "." + getExtension();
        }
        return fileName;
    }

    /**
     * Sets the file name. When set, a header 'Content-Disposition: attachment; filename="${name}"' will be added to the
     * response, resulting in a download dialog. No magical extensions are added, so you should make sure the file has
     * the extension you want yourself.
     *
     * @param name the file name
     *
     * @return This
     */
    public final JRResource setFileName(String name) {
        fileName = name;
        return this;
    }

    /**
     * Called by getData to obtain an exporter instance.
     *
     * @return an exporter instance
     */
    protected abstract JRAbstractExporter newExporter();

    /**
     * Gets the binary data by getting a new instance of JasperPrint and an exporter for generating the output.
     *
     * @return the binary data
     *
     * @see DynamicWebResource#getResourceState()
     */
    @Override
    protected ResourceState getResourceState() {
        try {
            long t1 = System.currentTimeMillis();

            // get a print instance for exporting
            JasperPrint print = newJasperPrint();

            // get a fresh instance of an exporter for this report
            JRAbstractExporter exporter = newExporter();

            // prepare a stream to trap the exporter's output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            // execute the export and return the trapped result
            exporter.exportReport();
            final byte[] data = baos.toByteArray();
            if(log.isDebugEnabled()) {
                long t2 = System.currentTimeMillis();
                log.debug("loaded report data; bytes: " + data.length + " in " + (t2 - t1) + " miliseconds");
            }
            return new ResourceState() {
                @Override
                public int getLength() {
                    return data.length;
                }

                @Override
                public byte[] getData() {
                    return data;
                }

                @Override
                public String getContentType() {
                    return JRResource.this.getContentType();
                }
            };
        }
        catch(JRException e) {
            throw new WicketRuntimeException(e);
        }
    }

    /**
     * @return The content type of the reports
     */
    public abstract String getContentType();

    /**
     * Returns the extension for the resource's file. This string should not contain the leading "."
     *
     * @return The extension for the resource's file.
     */
    public abstract String getExtension();

    /**
     * Creates a new {@link JasperPrint} instance. This instance is specific for this render, but it not yet designated
     * for one output format only.
     *
     * @return a new {@link JasperPrint} instance.
     *
     * @throws JRException
     */
    protected JasperPrint newJasperPrint() throws JRException {
        JasperPrint jasperPrint;
        JasperReport report = getJasperReport();
        Map params = getReportParameters();
        JRDataSource dataSource = getReportDataSource();
        if(dataSource != null) {
            jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
        } else {
            IDatabaseConnectionProvider provider = null;
            try {
                provider = getConnectionProvider();
                if(provider == null) {
                    throw new IllegalStateException(
                        "JasperReportsResources must either have a JRDataSource, or a JDBC Connection provided");
                }
                jasperPrint = JasperFillManager.fillReport(report, params, provider.get());
            }
            finally {
                if(provider != null) {
                    provider.release();
                }
            }
        }
        return jasperPrint;
    }

    @Override
    protected void setHeaders(WebResponse response) {
        super.setHeaders(response);
        String name = getFileName();
        if(name != null) {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        }
    }
}