package wicket.addons.hibernate;

import wicket.addons.hibernate.base.BaseWorkitem;

/**
 * This is the object class that relates to the workitems table.
 * Any customizations belong here.
 */
public class Workitem extends BaseWorkitem {

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Workitem () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Workitem (java.lang.Integer _id) {
		super(_id);
	}

	/**
	 * Constructor for required fields
	 */
	public Workitem (
		java.lang.Integer _id,
		java.util.Date _created,
		java.lang.String _description,
		java.util.Date _lastModified,
		java.lang.Integer _status,
		java.lang.Integer _priority,
		java.lang.String _headline) {

		super (
			_id,
			_created,
			_description,
			_lastModified,
			_status,
			_priority,
			_headline);
	}

/*[CONSTRUCTOR MARKER END]*/
}