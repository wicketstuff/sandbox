/*
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.jasperreports.examples;

import java.io.File;
import javax.servlet.ServletContext;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.jasperreports.JRCsvResource;
import wicket.contrib.jasperreports.JRHtmlResource;
import wicket.contrib.jasperreports.JRImageResource;
import wicket.contrib.jasperreports.JRPdfResource;
import wicket.contrib.jasperreports.JRRtfResource;
import wicket.contrib.jasperreports.JRTextResource;
import wicket.markup.html.link.ResourceLink;
import wicket.protocol.http.WebApplication;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * Simple Jasper reports example
 *
 * @author Eelco Hillenius
 * @author Justin Lee
 */
public class ReportLinksPage extends WicketExamplePage {
    /**
     * Constructor.
     */
    public ReportLinksPage() {
        ServletContext context = ((WebApplication)getApplication()).getServletContext();
        final File reportFile = new File(context.getRealPath("/reports/example.jrxml"));
        new ResourceLink(this, "linkToPdf", new JRPdfResource(reportFile) {
            @Override
            public JRDataSource getReportDataSource() {
                return new ExampleDataSource();
            }
        });
        new ResourceLink(this, "linkToRtf", new JRRtfResource(reportFile) {
            @Override
            public JRDataSource getReportDataSource() {
                return new ExampleDataSource();
            }
        });
        new ResourceLink(this, "linkToHtml",
            new JRHtmlResource(reportFile) {
                @Override
                public JRDataSource getReportDataSource() {
                    return new ExampleDataSource();
                }
            });
        new ResourceLink(this, "linkToText",
            new JRTextResource(reportFile) {
                @Override
                public JRDataSource getReportDataSource() {
                    return new ExampleDataSource();
                }
            });

        JRImageResource jrImageResource = new JRImageResource(reportFile) {
            @Override
            public JRDataSource getReportDataSource() {
                return new ExampleDataSource();
            }
        };
        // defaults to png but you can change that by setting the format
        jrImageResource.setFormat("jpg");
        new ResourceLink(this, "linkToImage", jrImageResource);

        new ResourceLink(this, "linkToCsv", new JRCsvResource(reportFile) {
            @Override
            public JRDataSource getReportDataSource() {
                return new ExampleDataSource();
            }
        });
    }

    @Override
    public boolean isVersioned() {
        return false;
    }
}