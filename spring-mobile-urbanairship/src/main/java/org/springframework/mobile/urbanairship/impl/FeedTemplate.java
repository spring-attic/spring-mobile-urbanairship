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
package org.springframework.mobile.urbanairship.impl;

import static org.springframework.mobile.urbanairship.impl.UrbanAirshipTemplate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mobile.urbanairship.Feed;
import org.springframework.mobile.urbanairship.FeedConfig;
import org.springframework.mobile.urbanairship.FeedOperations;
import org.springframework.web.client.RestOperations;

public class FeedTemplate implements FeedOperations {

	private final RestOperations restOperations;

	public FeedTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;		
	}
	
	public Feed createFeed(FeedConfig feedConfig) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<FeedConfig> requestEntity = new HttpEntity<FeedConfig>(feedConfig, headers);
		return restOperations.exchange(FEED_URL, HttpMethod.POST, requestEntity, Feed.class).getBody();
	}
	
	public Feed getFeed(String feedId) {
		return restOperations.getForObject(FEED_URL + feedId + "/", Feed.class);
	}
	
	public List<Feed> getFeeds() {
		return restOperations.getForObject(FEED_URL, FeedList.class);
	}
	
	public void updateFeed(String feedId, FeedConfig feedConfig) {
		restOperations.put(FEED_URL + feedId + "/", feedConfig);
	}
	
	public void deleteFeed(String feedId) {
		restOperations.delete(FEED_URL + feedId + "/");
	}
	
	private static final String FEED_URL = URBANAIRSHIP_API_URL + "feeds/";

	@SuppressWarnings("serial")
	private static class FeedList extends ArrayList<Feed> {}

}
