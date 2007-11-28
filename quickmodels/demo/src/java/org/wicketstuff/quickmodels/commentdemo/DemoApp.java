/*
 * WicketApplication.java
 *
 * Created on November 23, 2007, 9:37 AM
 */
 
package org.wicketstuff.quickmodels.commentdemo;           

import com.db4o.ObjectContainer;
import org.wicketstuff.persistence.Db;
import org.wicketstuff.persistence.db4o.Db4oDbImpl;
import org.wicketstuff.quickmodels.DbApplication;
/** 
 * Our application instance, subclassing DbApplication.  Currently uses
 * the db4o implementation of persistence, but could use any implementation
 *
 * @author Tim Boudreau
 */
public class DemoApp extends DbApplication<ObjectContainer> {
    public Class getHomePage() {
        return Home.class;
    }

    @Override
    protected Db<ObjectContainer> createDatabase() {
        //Create database file in the application's working dir
        //In Glassfish w/ defaults this will be under $GF_HOME/domains/domain1/...
        return Db4oDbImpl.create("database");
    }
}
