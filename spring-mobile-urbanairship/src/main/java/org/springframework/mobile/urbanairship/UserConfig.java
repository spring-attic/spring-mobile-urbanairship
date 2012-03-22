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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserConfig {
	
	private boolean airmail;
	
	private String alias;
	
	private List<String> tags;
	
	private List<String> deviceTokens;
	
	private String udid;
	
	private String email;
	
	private boolean empty;
	
	private UserConfig() {}
	
	public boolean isAirmail() {
		return airmail;
	}

	public String getAlias() {
		return alias;
	}

	public List<String> getTags() {
		return tags;
	}

	public List<String> getDeviceTokens() {
		return deviceTokens;
	}

	public String getUdid() {
		return udid;
	}
	
	public String getEmail() {
		return email;
	}
	
	public boolean isEmpty() {
		return empty;
	}

	public static UserConfigBuilder builder() {
		return new UserConfigBuilder();
	}
	
	public static class UserConfigBuilder {
		private boolean airmail;		
		private String alias;
		private List<String> tags;
		private List<String> deviceTokens;
		private String udid;
		private String email;
		private boolean empty = true;;

		private UserConfigBuilder() {
			tags = new ArrayList<String>();
			deviceTokens = new ArrayList<String>();
		}
		
		public UserConfigBuilder airmail(boolean airmail) {
			this.airmail = airmail;
			this.empty = false;
			return this;
		}
		
		public UserConfigBuilder alias(String alias) {
			this.alias = alias;
			this.empty = false;
			return this;
		}
		
		public UserConfigBuilder tags(String tag, String... moreTags) {
			this.tags.add(tag);
			this.tags.addAll(Arrays.asList(moreTags));
			this.empty = false;
			return this;
		}
		
		public UserConfigBuilder deviceTokens(String token, String... moreTokens) {
			this.deviceTokens.add(token);
			this.deviceTokens.addAll(Arrays.asList(moreTokens));
			this.empty = false;
			return this;
		}

		public UserConfigBuilder udid(String udid) {
			this.udid = udid;
			this.empty = false;
			return this;
		}
		
		public UserConfigBuilder email(String email) {
			this.email = email;
			this.empty = false;
			return this;
		}
		
		public UserConfig build() {
			UserConfig config = new UserConfig();
			config.airmail = airmail;
			config.alias = alias;
			if (!tags.isEmpty()) {
				config.tags = tags;
			}
			if (!deviceTokens.isEmpty()) {
				config.deviceTokens = deviceTokens;
			}
			config.udid = udid;
			config.email = email;
			config.empty = empty;
			return config;
		}
	}
	
}
