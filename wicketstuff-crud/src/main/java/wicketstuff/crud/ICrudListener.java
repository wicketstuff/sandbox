/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicketstuff.crud;

import org.apache.wicket.IClusterable;
import org.apache.wicket.model.IModel;

/**
 * Listener used by views to communicate with the crud panel
 * 
 * @author igor.vaynberg
 * 
 */
public interface ICrudListener extends IClusterable
{
	/**
	 * Actvate the view screen
	 * 
	 * @param selected
	 */
	void onView(IModel selected);

	/**
	 * Activate the create screen
	 */
	void onCreate();

	/**
	 * Activate the edit screen
	 * 
	 * @param selected
	 */
	void onEdit(IModel selected);

	/**
	 * Save object after it has been edited
	 * 
	 * @param selected
	 */
	void onSave(IModel selected);

	/**
	 * Delete object
	 * 
	 * @param selected
	 */
	void onDeleteConfirmed(IModel selected);

	/**
	 * Activate delete-confirmation screen
	 * 
	 * @param selected
	 */
	void onDelete(IModel selected);

	/**
	 * Cancel current screen and go back to previous
	 */
	void onCancel();
}
