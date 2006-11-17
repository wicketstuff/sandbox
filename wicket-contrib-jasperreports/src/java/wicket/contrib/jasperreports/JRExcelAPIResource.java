package wicket.contrib.jasperreports;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;

/**
 * Created Nov 6, 2006
 *
 * @author <a href="mailto:jlee@antwerkz.com">Justin Lee</a>
 */
public class JRExcelAPIResource extends JRXlsResource {
    public JRExcelAPIResource() {
    }

    public JRExcelAPIResource(File report) {
        super(report);
    }

    public JRExcelAPIResource(InputStream report) {
        super(report);
    }

    public JRExcelAPIResource(JasperReport report) {
        super(report);
    }

    public JRExcelAPIResource(URL report) {
        super(report);
    }

    @Override
    public JRAbstractExporter newExporter() {
        return new JExcelApiExporter();
    }
}
