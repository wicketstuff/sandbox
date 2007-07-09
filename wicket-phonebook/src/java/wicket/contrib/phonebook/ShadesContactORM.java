/*
 * ContactORM.java
 *
 * Created on September 14, 2006, 9:04 PM
 *
 * Copyright Geoffrey Rummens Hendrey 2006.
 */

package wicket.contrib.phonebook;

import hendrey.shades.DefaultHsqlORMapping;

/**
 *
 * @author ghendrey
 */
public class ShadesContactORM extends DefaultHsqlORMapping{
     
    public String[] getColumnNames(){
        return new String[]{"ID", "FIRSTNAME", "LASTNAME", "EMAIL", "PHONE"};       
    }
       
    
    public String[] getColumnSet(String columnSetName){
        if(columnSetName.equalsIgnoreCase("NonKeyFields")) return new String[]{"FIRSTNAME", "LASTNAME", "EMAIL", "PHONE"};
        else throw new RuntimeException("unknown columnSetName: "+ columnSetName);
    }
    
    /*
    public Object getColumn(String columnName, ResultSet resultSet) throws SQLException {
        if(columnName.endsWith("ID"))return resultSet.getLong(columnName);
        return resultSet.getString(columnName)
    }
     */

    
    public Class<?> getBeanClass() {
        return Contact.class;
    }

    public boolean isGeneratedKey(String columnName){
        return isIdentityColumn(columnName);
    }
    
    public boolean isIdentityColumn(String columnName){
        return columnName.endsWith("ID");
    }

    public String[] getNonPojoColumns() {
        return new String[]{};
    }
    
}
