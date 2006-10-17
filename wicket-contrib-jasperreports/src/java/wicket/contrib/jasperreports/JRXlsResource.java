package wicket.contrib.jasperreports;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 * Resource class for jasper reports Excell resources.
 *
 * @author Justin Lee
 */
public final class JRXlsResource extends JRResource {
    private static final long serialVersionUID = 1L;

    /**
     * Construct without a report. You must provide a report before you can use this resource.
     */
    public JRXlsResource() {
        super();
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRXlsResource(InputStream report) {
        super(report);
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRXlsResource(JasperReport report) {
        super(report);
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRXlsResource(URL report) {
        super(report);
    }

    /**
     * Construct.
     *
     * @param report the report input stream
     */
    public JRXlsResource(File report) {
        super(report);
    }

    @Override
    public JRAbstractExporter newExporter() {
        return new JRXlsExporter();
    }

    @Override
    public String getContentType() {
        return "application/excel";
    }

    @Override
    public String getExtension() {
        return "xls";
    }
}