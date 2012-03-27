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

public class Broadcast {

	private List<String> excludeTokens;

	private List<String> scheduleFor;
	
	private Aps aps;
	
	private Android android;
	
	private Blackberry blackberry;
	
	private Broadcast() {}

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
	
	public static BroadcastBuilder builder(String alert) {
		return new BroadcastBuilder(alert);
	}
	
	public static class BroadcastBuilder {
		
		private List<String> excludeTokens;
		private List<String> scheduleFor;
		private String alert;
		private String sound;
		private Integer badgeCount;
		private String autoBadge;

		private BroadcastBuilder(String alert) {
			this.alert = alert;
			excludeTokens = new ArrayList<String>();
			scheduleFor = new ArrayList<String>();
		}

		public BroadcastBuilder excludeTokens(String token, String... moreTokens) {
			this.excludeTokens.add(token);
			this.excludeTokens.addAll(Arrays.asList(moreTokens));
			return this;
		}

		public BroadcastBuilder scheduleFor(String firstSchedule, String... moreSchedules) {
			this.scheduleFor.add(firstSchedule);
			this.scheduleFor.addAll(Arrays.asList(moreSchedules));
			return this;
		}
		
		public BroadcastBuilder sound(String sound) {
			this.sound = sound;
			return this;
		}
		
		/**
		 * Sets the badge to a specific value.
		 * @param badgeCount the value to set the badge to
		 */
		public BroadcastBuilder badge(int badgeCount) {
			this.autoBadge = null;
			this.badgeCount = badgeCount;
			return this;
		}
		
		/**
		 * Sets the badge to "auto" to enable auto-badging.
		 */
		public BroadcastBuilder autoBadge() {
			this.badgeCount = null;
			this.autoBadge = "auto";
			return this;
		}

		/**
		 * Increments the badge by the given value.
		 * @param count the amount to increment the badge by.
		 */
		public BroadcastBuilder badgeIncrement(int count) {
			this.badgeCount = null;
			this.autoBadge = "+" + count;
			return this;
		}

		/**
		 * Decrements the badge by the given value.
		 * @param count the amount to decrement the badge by.
		 */
		public BroadcastBuilder badgeDecrement(int count) {
			this.badgeCount = null;
			this.autoBadge = "-" + count;
			return this;
		}

		public Broadcast build() {
			Broadcast notification = new Broadcast();
			if (!excludeTokens.isEmpty()) { notification.excludeTokens = excludeTokens; }
			if (!scheduleFor.isEmpty()) { notification.scheduleFor = scheduleFor; }
			addAps(notification);
			addAndroid(notification);
			addBlackberry(notification);
			return notification;
		}

		private void addBlackberry(Broadcast notification) {
			notification.blackberry = new Blackberry(alert);
		}

		private void addAndroid(Broadcast notification) {
			notification.android = new Android(alert);
		}

		private void addAps(Broadcast notification) {
			notification.aps = new Aps(alert);
			if (badgeCount != null) {
				notification.aps.setBadge(badgeCount);
			} else if (autoBadge != null) {
				notification.aps.setBadge(autoBadge);
			}
			notification.aps.setSound(sound);
		}
		
		
	}

}
