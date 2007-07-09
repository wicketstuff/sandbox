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

    @Override
	public String[] getColumnNames(){
        return new String[]{"ID", "FIRSTNAME", "LASTNAME", "EMAIL", "PHONE"};
    }


    @Override
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


    @Override
	public Class<?> getBeanClass() {
        return Contact.class;
    }

    @Override
	public boolean isGeneratedKey(String columnName){
        return isIdentityColumn(columnName);
    }

    @Override
	public boolean isIdentityColumn(String columnName){
        return columnName.endsWith("ID");
    }

    @Override
	public String[] getNonPojoColumns() {
        return new String[]{};
    }

}
