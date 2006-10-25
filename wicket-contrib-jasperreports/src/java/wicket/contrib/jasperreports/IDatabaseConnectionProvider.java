package wicket.contrib.jasperreports;

import java.sql.Connection;

/**
 * Provides JDBC connection.
 */
public interface IDatabaseConnectionProvider {
    /**
     * Gets a JDBC connection to use when filling the report.
     *
     * @return a JDBC connection
     */
    Connection get();

    /**
     * Called when the report is generated and the connection can be released again.
     */
    void release();
}
