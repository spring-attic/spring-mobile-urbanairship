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

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.RequestMatchers.*;
import static org.springframework.test.web.client.ResponseCreators.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class TagTemplateTest extends AbstractUrbanAirshipApiTest {

	private static final String DEVICE_TOKEN = "FE66489F304DC75B8D6E8200DFF8A456E8DAEACEC428B427E9518741C92C6660";

	@Test
	public void getTags_all() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/tags"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("data/tag-list"), responseHeaders, HttpStatus.OK, "OK"));
		List<String> tags = urbanAirship.tagOperations().getTags();
		assertEquals(3, tags.size());
		assertEquals("tag1", tags.get(0));
		assertEquals("some_tag", tags.get(1));
		assertEquals("portland_or", tags.get(2));		
		mockServer.verify();
	}

	@Test
	public void getTags_forDeviceToken() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN + "/tags"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("data/tag-list"), responseHeaders, HttpStatus.OK, "OK"));
		List<String> tags = urbanAirship.tagOperations().getTags(DEVICE_TOKEN);
		assertEquals(3, tags.size());
		assertEquals("tag1", tags.get(0));
		assertEquals("some_tag", tags.get(1));
		assertEquals("portland_or", tags.get(2));		
		mockServer.verify();
	}
	
	@Test
	public void createTag_noDeviceToken() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/tags/newtag"))
			.andExpect(method(PUT))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.tagOperations().createTag("newtag");
		mockServer.verify();
	}

	@Test
	public void createTag_noDeviceToken_alreadyExisting() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/tags/newtag"))
			.andExpect(method(PUT))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "OK"));
		urbanAirship.tagOperations().createTag("newtag");
		mockServer.verify();
	}

	@Test
	public void createTag_forDeviceToken() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN + "/tags/newtag"))
			.andExpect(method(PUT))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.tagOperations().createTag(DEVICE_TOKEN, "newtag");
		mockServer.verify();
	}

	@Test
	public void createTag_forDeviceToken_alreadyExisting() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN + "/tags/newtag"))
			.andExpect(method(PUT))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "OK"));
		urbanAirship.tagOperations().createTag(DEVICE_TOKEN, "newtag");
		mockServer.verify();
	}
	
	@Test
	public void modifyTagTokens() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/tags/newtag"))
			.andExpect(method(POST))
			.andExpect(body(compactedJson("data/modifytag-tokens")))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "OK"));
		urbanAirship.tagOperations().modifyTagTokens("newtag", asList("token1", "token2"), asList("token3", "token4"));
		mockServer.verify();		
	}

	@Test
	public void modifyTagPins() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/tags/newtag"))
			.andExpect(method(POST))
			.andExpect(body(compactedJson("data/modifytag-pins")))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "OK"));
		urbanAirship.tagOperations().modifyTagPins("newtag", asList("pin1", "pin2"), asList("pin3", "pin4"));
		mockServer.verify();		
	}

	@Test
	public void modifyTagAppIds() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/tags/newtag"))
			.andExpect(method(POST))
			.andExpect(body(compactedJson("data/modifytag-appids")))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "OK"));
		urbanAirship.tagOperations().modifyTagAppIds("newtag", asList("id1", "id2"), asList("id3", "id4"));
		mockServer.verify();		
	}

	@Test
	public void removeTags() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/tags"))
			.andExpect(method(PUT))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "OK"));
		urbanAirship.tagOperations().removeTags();
		mockServer.verify();
	}

	@Test
	public void removeTag() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN + "/tags/newtag"))
			.andExpect(method(DELETE))
			.andRespond(withResponse("", responseHeaders, HttpStatus.NO_CONTENT, "NO CONTENT"));
		urbanAirship.tagOperations().removeTag(DEVICE_TOKEN, "newtag");
		mockServer.verify();
	}

	@Test
	@Ignore("Revisit this")
	public void removeTag_notAssociatedWithDevice() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN + "/tags/newtag"))
			.andExpect(method(DELETE))
			.andRespond(withResponse("", responseHeaders, HttpStatus.NOT_FOUND, "NOT FOUND"));
		urbanAirship.tagOperations().removeTag(DEVICE_TOKEN, "newtag");
		mockServer.verify();
	}

}
