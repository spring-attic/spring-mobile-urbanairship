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

public class IOSDeviceConfig {

	// TODO: Consider extracting deviceToken, alias, and tags into a common device config class that is the base for
	//       IOSDeviceConfig and can be used for both Android and Blackberry
	
	private final String deviceToken;
	
	private String alias;
	
	private List<String> tags;
	
	private Integer badge;
	
	private QuietTime quietTime;
	
	private String timeZone;
	
	private boolean empty = true;
	
	private IOSDeviceConfig(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	
	public String getDeviceToken() {
		return deviceToken;
	}

	public String getAlias() {
		return alias;
	}

	public List<String> getTags() {
		return tags;
	}

	public Integer getBadge() {
		return badge;
	}

	public QuietTime getQuietTime() {
		return quietTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public boolean isEmpty() {
		return empty;
	}

	public static IOSDeviceConfigBuilder builder(String deviceToken) {
		return new IOSDeviceConfigBuilder(deviceToken);
	}
	
	public static class IOSDeviceConfigBuilder {		
		private final String deviceToken;		
		private String alias;
		private List<String> tags;
		private Integer badge;
		private String quietTimeStart;
		private String quietTimeEnd;
		private String timeZone;
		private boolean empty = true;
		
		public IOSDeviceConfigBuilder(String deviceToken) {
			this.deviceToken = deviceToken;
			tags = new ArrayList<String>();
		}
		
		public IOSDeviceConfigBuilder alias(String alias) {
			this.alias = alias;
			this.empty = false;
			return this;
		}

		public IOSDeviceConfigBuilder tags(String tag, String... moreTags) {
			this.tags.add(tag);
			this.tags.addAll(Arrays.asList(moreTags));
			this.empty = false;
			return this;
		}
		
		public IOSDeviceConfigBuilder badge(int badge) {
			this.badge = badge;
			this.empty = false;
			return this;
		}

		public IOSDeviceConfigBuilder quietTime(String start, String end, String timeZone) {
			this.quietTimeStart = start;
			this.quietTimeEnd = end;
			this.timeZone = timeZone;
			this.empty = false;
			return this;
		}
		
		public IOSDeviceConfig build() {
			IOSDeviceConfig deviceConfig = new IOSDeviceConfig(deviceToken);
			deviceConfig.alias = alias;
			deviceConfig.badge = badge;
			if (!tags.isEmpty()) {
				deviceConfig.tags = tags;
			}			
			if (quietTimeStart != null && quietTimeEnd != null) {
				deviceConfig.quietTime = new QuietTime(quietTimeStart, quietTimeEnd);
			}
			deviceConfig.timeZone = timeZone;
			deviceConfig.empty = empty;
			return deviceConfig;
		}
	}

	public static class QuietTime {
		private final String start;
		private final String end;

		public QuietTime(String start, String end) {
			this.start = start;
			this.end = end;			
		}

		public String getStart() {
			return start;
		}

		public String getEnd() {
			return end;
		}
	}
}
