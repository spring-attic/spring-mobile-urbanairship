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

import java.util.Map;


public class FeedConfig {

	private String url;
	
	private Boolean broadcast;
	
	private FeedNotificationTemplate template;
	
	public String getUrl() {
		return url;
	}
	
	public Boolean isBroadcast() {
		return broadcast;
	}
	
	public FeedNotificationTemplate getTemplate() {
		return template;
	}
	
	private FeedConfig(String url) {
		this.url = url;
	}
	
	public static FeedConfigBuilder builder(String url, String messageTemplate) {
		return new FeedConfigBuilder(url, messageTemplate);
	}
	
	public static class FeedConfigBuilder {
		private String url;		
		private String messageTemplate;
		private String sound;
		private Integer badgeCount;
		private String autoBadge;
		private Boolean broadcast;
		private Map<String, String> extra;
		
		public FeedConfigBuilder(String url, String messageTemplate) {
			this.url = url;
			this.messageTemplate = messageTemplate;
		}
		
		/**
		 * Sets the badge to a specific value.
		 * @param badgeCount the value to set the badge to
		 */
		public FeedConfigBuilder badge(int badgeCount) {
			this.autoBadge = null;
			this.badgeCount = badgeCount;
			return this;
		}
		
		/**
		 * Sets the badge to "auto" to enable auto-badging.
		 */
		public FeedConfigBuilder autoBadge() {
			this.badgeCount = null;
			this.autoBadge = "auto";
			return this;
		}

		/**
		 * Increments the badge by the given value.
		 * @param count the amount to increment the badge by.
		 */
		public FeedConfigBuilder badgeIncrement(int count) {
			this.badgeCount = null;
			this.autoBadge = "+" + count;
			return this;
		}

		/**
		 * Decrements the badge by the given value.
		 * @param count the amount to decrement the badge by.
		 */
		public FeedConfigBuilder badgeDecrement(int count) {
			this.badgeCount = null;
			this.autoBadge = "-" + count;
			return this;
		}

		public FeedConfigBuilder broadcast(boolean broadcast) {
			this.broadcast = broadcast;
			return this;
		}
		
		public FeedConfigBuilder sound(String sound) {
			this.sound = sound;
			return this;
		}
		
		public FeedConfigBuilder extra(Map<String, String> extra) {
			this.extra = extra;
			return this;
		}

		
		public FeedConfig build() {
			FeedConfig feedConfig = new FeedConfig(url);
			feedConfig.broadcast = broadcast;
			feedConfig.template = new FeedNotificationTemplate(createAps(), createAndroid(), createBlackberry());
			return feedConfig;
		}
		
		private Aps createAps() {
			Aps aps = new Aps(messageTemplate);
			aps.setSound(sound);
			if (badgeCount != null) {
				aps.setBadge(badgeCount);
			} else if (autoBadge != null) {
				aps.setBadge(autoBadge);
			}
			return aps;
		}
		
		private Android createAndroid() {
			Android android = new Android(messageTemplate);
			if (extra != null) {
				android.setExtra(extra);
			}
			return android;
		}
		
		private Blackberry createBlackberry() {
			return new Blackberry(messageTemplate);
		}

	}
	
}
