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
package org.springframework.mobile.urbanairship.impl;

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.RequestMatchers.*;
import static org.springframework.test.web.client.ResponseCreators.*;

import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.urbanairship.Feed;
import org.springframework.mobile.urbanairship.FeedConfig;

public class FeedTemplateTest extends AbstractUrbanAirshipApiTest {

	@Test
	public void createFeed() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/feeds/"))
			.andExpect(method(POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/feed-create"))))
			.andRespond(withResponse(jsonResource("data/feed"), responseHeaders));
		FeedConfig feedConfig = FeedConfig.builder("http://example.com/atom.xml", "New item from some place! {{ title }}").sound("cat.caf").badge(1).broadcast(true).build();
		Feed feed = urbanAirship.feedOperations().createFeed(feedConfig);
		assertSingleFeed(feed);
		masterKeyMockServer.verify();
	}

	@Test
	public void createFeed_minimal() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/feeds/"))
			.andExpect(method(POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/feed-create-minimal"))))
			.andRespond(withResponse(jsonResource("data/feed"), responseHeaders));
		FeedConfig feedConfig = FeedConfig.builder("http://example.com/atom.xml", "New item from some place! {{ title }}").build();
		Feed feed = urbanAirship.feedOperations().createFeed(feedConfig);
		assertSingleFeed(feed);
		masterKeyMockServer.verify();
	}

	@Test
	public void getFeed() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/feeds/12345/"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("data/feed"), responseHeaders));
		Feed feed = urbanAirship.feedOperations().getFeed("12345");
		assertSingleFeed(feed);
		masterKeyMockServer.verify();
	}

	@Test
	public void getFeeds() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/feeds/"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("data/feed-list"), responseHeaders));
		List<Feed> feeds = urbanAirship.feedOperations().getFeeds();
		assertSingleFeed(feeds.get(0));
		assertEquals("54321", feeds.get(1).getId());
		assertEquals("https://go.urbanairship.com/api/feeds/54321/", feeds.get(1).getUrl());
		assertEquals(1301160312000L, feeds.get(1).getLastChecked().getTime());
		assertEquals("http://anotherexample.com/feed.rss", feeds.get(1).getFeedUrl());
		assertEquals(2, feeds.get(1).getTemplate().getAps().getBadge());
		assertEquals("dog.caf", feeds.get(1).getTemplate().getAps().getSound());
		assertEquals("New item from some place! {{ title }}", feeds.get(1).getTemplate().getAps().getAlert());
		assertFalse(feeds.get(1).isBroadcast());
		masterKeyMockServer.verify();
	}
	
	@Test
	public void updateFeed() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/feeds/12345/"))
			.andExpect(method(PUT))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/feed-create"))))
			.andRespond(withResponse("", responseHeaders));
		FeedConfig feedConfig = FeedConfig.builder("http://example.com/atom.xml", "New item from some place! {{ title }}").sound("cat.caf").badge(1).broadcast(true).build();
		urbanAirship.feedOperations().updateFeed("12345", feedConfig);
		masterKeyMockServer.verify();
	}
	
	@Test
	public void deleteFeed() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/feeds/12345/"))
			.andExpect(method(DELETE))
			.andRespond(withResponse("", responseHeaders, HttpStatus.NO_CONTENT, ""));
		urbanAirship.feedOperations().deleteFeed("12345");
		masterKeyMockServer.verify();
	}

	private void assertSingleFeed(Feed feed) {
		assertEquals("12345", feed.getId());
		assertEquals("https://go.urbanairship.com/api/feeds/12345/", feed.getUrl());
		assertEquals(1268106741000L, feed.getLastChecked().getTime());
		assertEquals("http://example.com/atom.xml", feed.getFeedUrl());
		assertEquals(1, feed.getTemplate().getAps().getBadge());
		assertEquals("cat.caf", feed.getTemplate().getAps().getSound());
		assertEquals("New item from some place! {{ title }}", feed.getTemplate().getAps().getAlert());
		assertTrue(feed.isBroadcast());
	}

}
