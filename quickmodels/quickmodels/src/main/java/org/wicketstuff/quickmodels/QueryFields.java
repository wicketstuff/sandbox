/*
 * Fields.java
 * 
 * Created on Aug 3, 2007, 9:42:16 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wicketstuff.quickmodels;

/**
 * Field definitions for queries.  Each item corresponds to a setter 
 * method on ModelBuilder.
 * 
 * @see Queries.getIllegalFields()
 * @see Queries.getRequiredFields()
 * @see Queries.getOptionalFields()
 * @author Tim Boudreau
 */
public enum QueryFields {
        UID, 
        UUID, 
        TYPE, 
        POLICY, 
        FACTORY, 
        OBJECT, 
        FIELD_TYPE, 
        FIELD_VALUE,
        CUSTOM_QUERY, 
        NEW_OBJECT_COUNT, 
        FIELD_NAME, 
        CUSTOM_QUERY_ARG,
}
