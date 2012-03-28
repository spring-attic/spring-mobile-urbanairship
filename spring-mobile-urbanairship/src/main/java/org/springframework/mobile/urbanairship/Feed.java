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

public class Feed {

	private final String id;
	
	private final String url;
	
	private final String feedUrl;
	
	private final Date lastChecked;
	
	private final FeedNotificationTemplate template;

	private final boolean broadcast;
	
	private Feed(String id, String url, String feedUrl, Date lastChecked, FeedNotificationTemplate template, boolean broadcast) {
		this.id = id;
		this.url = url;
		this.feedUrl = feedUrl;
		this.lastChecked = lastChecked;
		this.template = template;
		this.broadcast = broadcast;
	}
	
	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public String getFeedUrl() {
		return feedUrl;
	}

	public Date getLastChecked() {
		return lastChecked;
	}

	public FeedNotificationTemplate getTemplate() {
		return template;
	}

	public boolean isBroadcast() {
		return broadcast;
	}

}

