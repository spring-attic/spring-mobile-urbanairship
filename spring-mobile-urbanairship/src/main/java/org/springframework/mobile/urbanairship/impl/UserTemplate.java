/*
 * Copyright 2011 the original author or authors.
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
import org.springframework.mobile.urbanairship.UserConfig;
import org.springframework.mobile.urbanairship.UserCredentials;
import org.springframework.mobile.urbanairship.UserOperations;
import org.springframework.mobile.urbanairship.impl.json.RecoveryResponse;
import org.springframework.web.client.RestOperations;

public class UserTemplate implements UserOperations {

	private final RestOperations restOperations;

	public UserTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;
	}
	
	public UserCredentials createUser() {
		return createUser(null);
	}

	public UserCredentials createUser(UserConfig userConfig) {
		if (userConfig != null && userConfig.isEmpty()) {
			return restOperations.postForObject(USER_URL, "", UserCredentials.class);
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<UserConfig> requestEntity = new HttpEntity<UserConfig>(userConfig, headers);
			return restOperations.exchange(USER_URL, HttpMethod.POST, requestEntity, UserCredentials.class).getBody();
		}
	}
	
	public void modifyUser(String userId, UserConfig userConfig) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserConfig> requestEntity = new HttpEntity<UserConfig>(userConfig, headers);
		restOperations.exchange(USER_URL + "/" + userId, HttpMethod.PUT, requestEntity, String.class);		
	}
	
	public void resetPassword(String userId) {
		restOperations.postForObject(USER_URL + "/" + userId + "/creds/reset", "", UserCredentials.class);
	}
	
	public void deleteUser(String userId) {
		restOperations.delete(USER_URL + "/" + userId);
	}
	
	public String recoverAccount(String emailAddress) {
		return restOperations.postForObject(USER_URL + "/recover", new RecoverRequest(emailAddress), RecoveryResponse.class).getRecoveryStatusId();
	}
	
	private static final String USER_URL = URBANAIRSHIP_API_URL + "user";

	
	private static class RecoverRequest {
		private String email;
		
		public RecoverRequest(String email) {
			this.email = email;
		}
		
		public String getEmail() {
			return email;
		}
	}
}
