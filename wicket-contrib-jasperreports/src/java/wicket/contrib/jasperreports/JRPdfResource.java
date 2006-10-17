/*
 * $Id$ $Revision:
 * 1.3 $ $Date$
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

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * Resource class for jasper reports PDF resources.
 *
 * @author Eelco Hillenius
 * @author Justin Lee
 */
public final class JRPdfResource extends JRResource {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct without a report. You must provide a report before you can use this resource.
     */
    public JRPdfResource() {
        super();
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRPdfResource(InputStream report) {
        super(report);
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRPdfResource(JasperReport report) {
        super(report);
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRPdfResource(URL report) {
        super(report);
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRPdfResource(File report) {
        super(report);
    }

    /**
     * @see JRResource#newExporter()
     */
    @Override
    public JRAbstractExporter newExporter() {
        return new JRPdfExporter();
    }

    /**
     * @see JRResource#getContentType()
     */
    @Override
    public String getContentType() {
        return "application/pdf";
    }

    @Override
    public String getExtension() {
        return "pdf";
    }
}
