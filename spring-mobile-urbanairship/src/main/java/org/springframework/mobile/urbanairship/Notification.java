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
import java.util.Map;

public class Notification {

	private List<String> deviceTokens;

	private List<String> apids;

	private List<String> devicePins;

	private List<String> aliases;
	
	private List<String> tags;
	
	private List<String> excludeTokens;

	private List<String> scheduleFor;
	
	private Aps aps;
	
	private Android android;
	
	private Blackberry blackberry;
	
	private Notification() {}

	public List<String> getDeviceTokens() {
		return deviceTokens;
	}

	public List<String> getApids() {
		return apids;
	}

	public List<String> getDevicePins() {
		return devicePins;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public List<String> getTags() {
		return tags;
	}

	public List<String> getScheduleFor() {
		return scheduleFor;
	}

	public List<String> getExcludeTokens() {
		return excludeTokens;
	}
	
	public Aps getAps() {
		return aps;
	}
	
	public Android getAndroid() {
		return android;
	}
	
	public Blackberry getBlackberry() {
		return blackberry;
	}
	
	public static NotificationBuilder builder(String message) {
		return new NotificationBuilder(message);
	}
	
	public static class NotificationBuilder {	
		private List<String> deviceTokens;
		private List<String> apids;
		private List<String> devicePins;
		private List<String> aliases;
		private List<String> tags;
		private List<String> excludeTokens;
		private List<String> scheduleFor;
		private Map<String, String> extra;
		private String message;
		private String sound;
		private Integer badgeCount;
		private String autoBadge;

		private NotificationBuilder(String message) {
			this.message = message;
			deviceTokens = new ArrayList<String>();
			apids = new ArrayList<String>();
			devicePins = new ArrayList<String>();
			aliases = new ArrayList<String>();
			tags = new ArrayList<String>();
			excludeTokens = new ArrayList<String>();
			scheduleFor = new ArrayList<String>();
		}

		public NotificationBuilder deviceTokens(String token, String... moreTokens) {
			this.deviceTokens.add(token);
			this.deviceTokens.addAll(Arrays.asList(moreTokens));
			return this;
		}

		public NotificationBuilder apids(String apid, String... moreApids) {
			this.apids.add(apid);
			this.apids.addAll(Arrays.asList(moreApids));
			return this;
		}

		public NotificationBuilder devicePins(String devicePin, String... moreDevicePins) {
			this.devicePins.add(devicePin);
			this.devicePins.addAll(Arrays.asList(moreDevicePins));
			return this;
		}

		public NotificationBuilder aliases(String alias, String... moreAliases) {
			this.aliases.add(alias);
			this.aliases.addAll(Arrays.asList(moreAliases));
			return this;
		}

		public NotificationBuilder tags(String tag, String... moreTags) {
			this.tags.add(tag);
			this.tags.addAll(Arrays.asList(moreTags));
			return this;
		}

		public NotificationBuilder excludeTokens(String token, String... moreTokens) {
			this.excludeTokens.add(token);
			this.excludeTokens.addAll(Arrays.asList(moreTokens));
			return this;
		}

		public NotificationBuilder scheduleFor(String firstSchedule, String... moreSchedules) {
			this.scheduleFor.add(firstSchedule);
			this.scheduleFor.addAll(Arrays.asList(moreSchedules));
			return this;
		}
		
		public NotificationBuilder sound(String sound) {
			this.sound = sound;
			return this;
		}
		
		/**
		 * Sets the badge to a specific value.
		 * @param badgeCount the value to set the badge to
		 */
		public NotificationBuilder badge(int badgeCount) {
			this.autoBadge = null;
			this.badgeCount = badgeCount;
			return this;
		}
		
		/**
		 * Sets the badge to "auto" to enable auto-badging.
		 */
		public NotificationBuilder autoBadge() {
			this.badgeCount = null;
			this.autoBadge = "auto";
			return this;
		}

		/**
		 * Increments the badge by the given value.
		 * @param count the amount to increment the badge by.
		 */
		public NotificationBuilder badgeIncrement(int count) {
			this.badgeCount = null;
			this.autoBadge = "+" + count;
			return this;
		}

		/**
		 * Decrements the badge by the given value.
		 * @param count the amount to decrement the badge by.
		 */
		public NotificationBuilder badgeDecrement(int count) {
			this.badgeCount = null;
			this.autoBadge = "-" + count;
			return this;
		}
		
		public NotificationBuilder extra(Map<String, String> extra) {
			this.extra = extra;
			return this;
		}
		
		public Notification build() {
			Notification notification = new Notification();
			addAps(notification);			
			addAndroid(notification);			
			addBlackberry(notification);			
			if (!aliases.isEmpty()) { notification.aliases = aliases; }
			if (!tags.isEmpty()) { notification.tags = tags; }
			if (!excludeTokens.isEmpty()) { notification.excludeTokens = excludeTokens; }
			if (!scheduleFor.isEmpty()) { notification.scheduleFor = scheduleFor; }
			return notification;
		}

		private void addBlackberry(Notification notification) {
			if (!devicePins.isEmpty()) { notification.devicePins = devicePins; }

			if (!aliases.isEmpty() || !tags.isEmpty() || !devicePins.isEmpty()) {
				notification.blackberry = new Blackberry(message);
			}
		}

		private void addAndroid(Notification notification) {
			if (!apids.isEmpty()) { notification.apids = apids; }

			if (!aliases.isEmpty() || !tags.isEmpty() || !apids.isEmpty()) {
				notification.android = new Android(message);
				if (extra != null && !extra.isEmpty()) {
					notification.android.setExtra(extra);
				}
			}
		}

		private void addAps(Notification notification) {
			if (!deviceTokens.isEmpty()) { notification.deviceTokens = deviceTokens; }

			if (!aliases.isEmpty() || !tags.isEmpty() || !deviceTokens.isEmpty()) {
				notification.aps = new Aps(message);
				notification.aps.setSound(sound);
				if (badgeCount != null) {
					notification.aps.setBadge(badgeCount);
				} else if (autoBadge != null) {
					notification.aps.setBadge(autoBadge);
				}
			}
		}

	}

}
