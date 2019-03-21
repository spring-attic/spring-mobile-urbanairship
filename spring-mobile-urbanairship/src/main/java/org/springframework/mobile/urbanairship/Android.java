/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.mobile.urbanairship;

import java.util.Map;

public class Android {

	private final String alert;
	
	private Map<String, String> extra;

	Android(String alert) {
		this.alert = alert;
	}

	public String getAlert() {
		return alert;
	}
	
	public Map<String, String> getExtra() {
		return extra;
	}
	
	public void setExtra(Map<String, String> extra) {
		this.extra = extra;
	}
	
}
