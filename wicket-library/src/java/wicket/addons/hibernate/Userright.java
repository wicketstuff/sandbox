package wicket.addons.hibernate;

import wicket.addons.hibernate.base.BaseUserright;


/**
 * This is the object class that relates to the userrights table.
 * Any customizations belong here.
 */
public class Userright extends BaseUserright {

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Userright () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Userright (java.lang.Integer _id) {
		super(_id);
	}

	/**
	 * Constructor for required fields
	 */
	public Userright (
		java.lang.Integer _id,
		java.lang.String _name) {

		super (
			_id,
			_name);
	}

/*[CONSTRUCTOR MARKER END]*/
}