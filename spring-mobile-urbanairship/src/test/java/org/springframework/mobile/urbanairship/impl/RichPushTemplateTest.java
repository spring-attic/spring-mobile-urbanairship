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

import static org.springframework.test.web.client.RequestMatchers.*;
import static org.springframework.test.web.client.ResponseCreators.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.urbanairship.IncompleteMessageException;
import org.springframework.mobile.urbanairship.OutgoingMessage;

public class RichPushTemplateTest extends AbstractUrbanAirshipApiTest {

	@Test
	public void sendMessage_toUsers_noPush() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/airmail/send"))
			.andExpect(method(HttpMethod.POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/message-tousers-nopush"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));		
		urbanAirship.richPushOperations().sendMessage(OutgoingMessage.builder("Hello").users("user1","user2").build());		
		masterKeyMockServer.verify();
	}

	@Test
	public void sendMessage_toAliases_noPush() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/airmail/send"))
			.andExpect(method(HttpMethod.POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/message-toaliases-nopush"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));		
		urbanAirship.richPushOperations().sendMessage(OutgoingMessage.builder("Hello").aliases("alias1","alias2").build());		
		masterKeyMockServer.verify();
	}

	@Test
	public void sendMessage_toTags_noPush() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/airmail/send"))
			.andExpect(method(HttpMethod.POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/message-totags-nopush"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));		
		urbanAirship.richPushOperations().sendMessage(OutgoingMessage.builder("Hello").tags("tag1","tag2").build());		
		masterKeyMockServer.verify();
	}

	@Test
	public void sendMessage_toUsersAliasesAndTags_noPush() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/airmail/send"))
			.andExpect(method(HttpMethod.POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/message-tousersaliasestags-nopush"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));		
		urbanAirship.richPushOperations().sendMessage(OutgoingMessage.builder("Hello").users("user1","user2").aliases("alias1","alias2").tags("tag1","tag2").build());		
		masterKeyMockServer.verify();
	}

	@Test(expected=IncompleteMessageException.class)
	public void sendMessage_toNone_noPush() {
		urbanAirship.richPushOperations().sendMessage(OutgoingMessage.builder("Hello").build());		
	}

	@Test
	public void sendMessage_toUsersAliasesAndTags_complete_noPush() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/airmail/send"))
			.andExpect(method(HttpMethod.POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/message-tousersaliasestags-complete-nopush"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));		
		Map<String, String> extra = new LinkedHashMap<String, String>();
		extra.put("key1","value1");
		extra.put("key2","value2");
		urbanAirship.richPushOperations().sendMessage(OutgoingMessage.builder("Hello").users("user1","user2").aliases("alias1","alias2").tags("tag1","tag2").title("title").contentType("text/html").extra(extra).build());		
		masterKeyMockServer.verify();
	}

	@Test
	public void sendMessage_toUsersAliasesAndTags_complete_withPush() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/airmail/send"))
			.andExpect(method(HttpMethod.POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/message-tousersaliasestags-complete-withpush"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));		
		Map<String, String> extra = new LinkedHashMap<String, String>();
		extra.put("key1","value1");
		extra.put("key2","value2");
		urbanAirship.richPushOperations().sendMessage(
				OutgoingMessage.builder("Hello").users("user1","user2").aliases("alias1","alias2")
					.tags("tag1","tag2").title("title").contentType("text/html").extra(extra)
					.notify("New message!", "cat.caf").build());		
		masterKeyMockServer.verify();
	}

	@Test
	public void sendBroadcast() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/airmail/send/broadcast"))
			.andExpect(method(HttpMethod.POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/message-broadcast"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));		
		Map<String, String> extra = new LinkedHashMap<String, String>();
		extra.put("key1","value1");
		extra.put("key2","value2");
		urbanAirship.richPushOperations().sendBroadcast(
				OutgoingMessage.builder("Hello").title("title").contentType("text/html").extra(extra)
					.notify("New message!", "cat.caf").build());		
		masterKeyMockServer.verify();
	}

}
