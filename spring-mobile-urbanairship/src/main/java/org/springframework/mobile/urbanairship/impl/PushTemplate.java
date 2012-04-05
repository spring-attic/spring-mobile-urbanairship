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

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mobile.urbanairship.Broadcast;
import org.springframework.mobile.urbanairship.Notification;
import org.springframework.mobile.urbanairship.PushOperations;
import org.springframework.mobile.urbanairship.PushResponse;
import org.springframework.mobile.urbanairship.PushStatistics;
import org.springframework.web.client.RestOperations;

public class PushTemplate implements PushOperations {

	private final RestOperations masterKeyRestOperations;

	public PushTemplate(RestOperations masterKeyRestOperations) {
		this.masterKeyRestOperations = masterKeyRestOperations;		
	}
	
	public PushResponse pushNotification(Notification notification) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Notification> requestEntity = new HttpEntity<Notification>(notification, headers);
		
		// TODO: As far as I can tell, the response body will be empty unless schedule-for is set. This creates a challenge in parsing the JSON.
		//       So, if schedule-for is set, we'll get the response into PushResponse. Otherwise, we'll ignore the response and return null.
		if (notification.getScheduleFor() != null && !notification.getScheduleFor().isEmpty()) {
			return masterKeyRestOperations.exchange(PUSH_URL, HttpMethod.POST, requestEntity, PushResponse.class).getBody();			
		}
		masterKeyRestOperations.exchange(PUSH_URL, HttpMethod.POST, requestEntity, String.class);
		return null;
	}

	public void pushNotifications(List<Notification> notifications) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<Notification>> requestEntity = new HttpEntity<List<Notification>>(notifications, headers);
		masterKeyRestOperations.exchange(PUSH_URL + "batch", HttpMethod.POST, requestEntity, String.class);
	}
	
	public void sendBroadcast(Broadcast broadcast) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Broadcast> requestEntity = new HttpEntity<Broadcast>(broadcast, headers);
		masterKeyRestOperations.exchange(PUSH_URL + "broadcast/", HttpMethod.POST, requestEntity, String.class);
	}
	
	public List<PushStatistics> getStatistics(Date start, Date end) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd+HH:mm");
		String startString = format.format(start);
		String endString = format.format(end);
		try {
			return (List<PushStatistics>) masterKeyRestOperations.getForObject(new URI(PUSH_URL + "stats/?start=" + startString + "&end=" + endString), PushStatisticsList.class);
		} catch (URISyntaxException shouldntHappen) {
			return null;
		}		
	}
	
	private static final String PUSH_URL = URBANAIRSHIP_API_URL + "push/";

	@SuppressWarnings("serial")
	private static class PushStatisticsList extends ArrayList<PushStatistics> {}
	
}
