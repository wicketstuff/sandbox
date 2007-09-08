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
package wicketstuff.crud.filter;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Filter component that generates a 'go' and 'clear' buttons.
 * 
 * @author Igor Vaynberg (ivaynberg)
 * 
 */
public class ApplyAndClearFilter extends Panel
{
	public ApplyAndClearFilter(String id)
	{
		super(id);

		add(new Button("apply")
		{
			@Override
			public void onSubmit()
			{
				FilterToolbar toolbar = (FilterToolbar)findParent(FilterToolbar.class);
				toolbar.onApplyFilter();

			}
		}.setVisible(true));


		Button clear = new Button("clear")
		{
			public void onSubmit()
			{
				FilterToolbar toolbar = (FilterToolbar)findParent(FilterToolbar.class);
				toolbar.onClearFilter();
			}
		};
		clear.setDefaultFormProcessing(false).setVisible(false);

		add(clear);
	}


}
