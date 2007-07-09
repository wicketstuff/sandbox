/*
 * ShadesPhonebookORMDictionary.java
 *
 * Created on September 14, 2006, 8:51 PM
 *
 * Copyright Geoffrey Rummens Hendrey 2006.
 */

package wicket.contrib.phonebook;

import hendrey.orm.ORMDictionary;
import hendrey.orm.ORMapping;
import hendrey.orm.Query;
import hendrey.orm.RecordCandidate;
import hendrey.shades.ORMDictionaryFactory;
import hendrey.shades.QueryFactory;
import hendrey.shades.tools.TableCreator;
import java.sql.Connection;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;


/**
 *
 * @author ghendrey
 */
public class ShadesORMDictionary implements InitializingBean{
    private static final ORMDictionary dict = ORMDictionaryFactory.getInstance("phonebook-schema");
    private DataSource dataSource; //required for table creation
    static RecordCandidate filterCandidate; //DAO can grab filterCandidate and call .resembles on the fly to dynamical reconfigure the query

    static{
        ORMapping orm = new ShadesContactORM();
        dict.defineORMapping("CONTACT", orm);

        /************************* DEFINE QUERY BY ID *********************************/
        Query q = QueryFactory.newQuery(dict);
        q.candidate(orm).where("ID=${id}", new String[]{});
        dict.defineQuery("byId", q);
        /***************************************************************************************/

        /************************DEFINE QUERY BY RESEMBLANCE******************/
        q = QueryFactory.newQuery(dict);
        q.candidate(orm, "CONTACT");
        dict.defineQuery("byResemblance", q);
        /***************************************************************************************/

         /*******************DEFINE QUERY BY RESEMBLENCE WITH FILTER*******/
        q = QueryFactory.newQuery(dict);
        filterCandidate = q.candidate(orm, "CONTACT");
	q.clause("ORDER BY").append("${order} ${direction} LIMIT ${count} OFFSET ${first}");
        dict.defineQuery("byOrderedResemblance", q);
        /***************************************************************************************/

        /*******************DEFINE QUERY FOR DISTINCT LASTNAME ***************/
        q = QueryFactory.newImmutableQuery("SELECT DISTINCT LASTNAME AS \"CONTACT.LASTNAME\" FROM CONTACT");
        q.candidate(orm).setFetchColumns(new String[]{"LASTNAME"});
        dict.defineQuery("selectDistinctLastnameOnly", q);
        /****************************************************************************************/

    }

    public static ORMDictionary getInstance(){
        return dict; //return the static, configured ORMDictionary
    }

    public void afterPropertiesSet() throws Exception {
        Connection c = null;
        try{
            String ddl = TableCreator.getDDL(dict);
            c = dataSource.getConnection();
            c.createStatement().execute(ddl);
            c.commit();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally{
            c.close();
        }
    }

    /** Creates a new instance of ShadesPhonebookORMDictionary */
    private ShadesORMDictionary() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}

