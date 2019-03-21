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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AndroidDeviceConfig {

	// TODO: Consider extracting apid, alias, and tags into a common device config class that is the base for
	//       IOSDeviceConfig and can be used for both Android and Blackberry

	private final String apid;
	
	private String alias;
	
	private List<String> tags;
	
	private boolean empty;

	private AndroidDeviceConfig(String apid) {
		this.apid = apid;
	}
	
	public String getApid() {
		return apid;
	}

	public String getAlias() {
		return alias;
	}

	public List<String> getTags() {
		return tags;
	}

	public boolean isEmpty() {
		return empty;
	}
	
	public static AndroidDeviceConfigBuilder builder(String apid) {
		return new AndroidDeviceConfigBuilder(apid);
	}
	
	public static class AndroidDeviceConfigBuilder {
		private final String apid;
		private String alias;
		private List<String> tags;
		private boolean empty = true;

		private AndroidDeviceConfigBuilder(String apid) {
			this.apid = apid;			
			tags = new ArrayList<String>();
		}
		
		public AndroidDeviceConfigBuilder alias(String alias) {
			this.alias = alias;
			this.empty = false;
			return this;
		}
		
		public AndroidDeviceConfigBuilder tags(String tag, String... moreTags) {
			this.tags.add(tag);
			this.tags.addAll(Arrays.asList(moreTags));
			this.empty = false;
			return this;
		}

		public AndroidDeviceConfig build() {
			AndroidDeviceConfig config = new AndroidDeviceConfig(apid);
			config.alias = alias;
			if (!tags.isEmpty()) {
				config.tags = tags;
			}
			config.empty = empty;			
			return config;
		}
		
	}
}
