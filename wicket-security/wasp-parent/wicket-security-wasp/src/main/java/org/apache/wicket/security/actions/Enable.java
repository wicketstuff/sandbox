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
package org.apache.wicket.security.actions;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;

/**
 * the right to enable a component. For {@link Link} this means click on it, for
 * a {@link Form} submit it, for a {@link TextField} write to it, etc. Enable
 * must implie {@link Render}
 * 
 * @author marrink
 * @see Action#ENABLE
 * @see Component#ENABLE
 */
public interface Enable extends WaspAction
{

}