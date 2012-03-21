/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.mobile.urbanairship;

import java.util.Date;
import java.util.List;

public class AndroidDevice {

	private final List<String> apids;
	
	private final String alias;
	
	private final List<String> tags;
	
	private final Date created;
	
	private final boolean active;
	
	public AndroidDevice(List<String> apids, String alias, List<String> tags, Date created, boolean active) {
		this.apids = apids;
		this.alias = alias;
		this.tags = tags;
		this.created = created;		
		this.active = active;
	}
	
	public List<String> getApids() {
		return apids;
	}

	public String getAlias() {
		return alias;
	}

	public Date getCreated() {
		return created;
	}

	public List<String> getTags() {
		return tags;
	}

	public boolean isActive() {
		return active;
	}

}
