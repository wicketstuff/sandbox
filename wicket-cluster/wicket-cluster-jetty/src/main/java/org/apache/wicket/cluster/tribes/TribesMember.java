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
package org.apache.wicket.cluster.tribes;

import org.apache.catalina.tribes.Member;

public class TribesMember implements org.apache.wicket.cluster.Member {
	
	private final Member delegate;
	
	TribesMember(Member delegate) {
		
		if (delegate == null)
		{
			throw new IllegalArgumentException("Tribes member may not be null");
		}
		
		this.delegate = delegate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		{
			return true;
		}
		if (obj instanceof TribesMember == false)
		{
			return false;
		}
		
		TribesMember rhs = (TribesMember) obj;
		return delegate.equals(rhs.delegate);
	}
	
	@Override
	public int hashCode() {
		return delegate.hashCode();
	}
	
	@Override
	public String toString() {
		return delegate.toString();
	}
	
	public Member getTribesMember() {
		return delegate;
	}
}
