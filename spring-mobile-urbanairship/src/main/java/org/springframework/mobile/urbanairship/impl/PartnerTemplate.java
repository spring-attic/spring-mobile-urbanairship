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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.urbanairship.Application;
import org.springframework.mobile.urbanairship.ApplicationData;
import org.springframework.mobile.urbanairship.PartnerOperations;
import org.springframework.web.client.RestOperations;

public class PartnerTemplate implements PartnerOperations {

	private final RestOperations restOperations;

	public PartnerTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;
	}
	
	public List<Application> getApplications() {
		return (List<Application>) restOperations.getForObject(URBANAIRSHIP_API_URL + "partner/apps", Applications.class);
	}
	
	public URI createApplication(ApplicationData applicationData) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ApplicationData> requestEntity = new HttpEntity<ApplicationData>(applicationData, headers);
		ResponseEntity<String> responseEntity = restOperations.exchange(URBANAIRSHIP_API_URL + "partner/apps", HttpMethod.POST, requestEntity, String.class);
		return responseEntity.getHeaders().getLocation();
	}

	public void updateApplication(URI applicationUri, ApplicationData applicationData) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ApplicationData> requestEntity = new HttpEntity<ApplicationData>(applicationData, headers);
		restOperations.exchange(applicationUri, HttpMethod.PUT, requestEntity, String.class);
	}

	public void updateApplication(String applicationKey, ApplicationData applicationData) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ApplicationData> requestEntity = new HttpEntity<ApplicationData>(applicationData, headers);
		restOperations.exchange(URBANAIRSHIP_API_URL + "partner/apps/" + applicationKey, HttpMethod.PUT, requestEntity, String.class);
	}

	@SuppressWarnings("serial")
	private static class Applications extends ArrayList<Application> {}

}
