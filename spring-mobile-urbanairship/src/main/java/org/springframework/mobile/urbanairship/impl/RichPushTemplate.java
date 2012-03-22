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

import static org.springframework.mobile.urbanairship.impl.UrbanAirshipTemplate.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mobile.urbanairship.IncompleteMessageException;
import org.springframework.mobile.urbanairship.OutgoingMessage;
import org.springframework.mobile.urbanairship.RichPushOperations;
import org.springframework.web.client.RestOperations;

class RichPushTemplate implements RichPushOperations {

	private final RestOperations restOperations;

	public RichPushTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;
	}

	public void sendMessage(OutgoingMessage message) {
		if (message.hasRecipients()) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<OutgoingMessage> requestEntity = new HttpEntity<OutgoingMessage>(message, headers);
			restOperations.exchange(AIRMAIL_URL, HttpMethod.POST, requestEntity, String.class);
		} else {
			throw new IncompleteMessageException("A message must have at least one user, alias, or tag as recipient unless sending as a broadcast.");
		}
	}
	
	public void sendBroadcast(OutgoingMessage message) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<OutgoingMessage> requestEntity = new HttpEntity<OutgoingMessage>(message, headers);
		restOperations.exchange(AIRMAIL_URL + "/broadcast", HttpMethod.POST, requestEntity, String.class);
	}
	
//	"https://go.urbanairship.com/api/airmail/send"
	
	private static final String AIRMAIL_URL = URBANAIRSHIP_API_URL + "airmail/send";
}
