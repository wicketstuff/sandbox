package wicket.contrib.jasperreports;

import java.io.Serializable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Factory class for lazy initialization of the jasper report.
 */
public interface JasperReportFactory extends Serializable {
    /**
     * Create a jasper report instance.
     *
     * @return the new jasper report instance.
     *
     * @throws JRException
     */
    JasperReport newJasperReport() throws JRException;
}
