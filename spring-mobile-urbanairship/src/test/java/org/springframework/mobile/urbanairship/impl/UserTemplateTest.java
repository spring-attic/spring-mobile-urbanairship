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

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.RequestMatchers.*;
import static org.springframework.test.web.client.ResponseCreators.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.urbanairship.UserConfig;
import org.springframework.mobile.urbanairship.UserCredentials;

public class UserTemplateTest extends AbstractUrbanAirshipApiTest {

	@Test
	public void createUser_noConfig() {		
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/user"))
			.andExpect(method(POST))
			.andRespond(withResponse(jsonResource("data/user-credentials"), responseHeaders, HttpStatus.CREATED, "Created"));
		UserCredentials credentials = urbanAirship.userOperations().createUser();
		assertEquals("https://go.urbanairship.com/api/user/someuser", credentials.getUserUrl());
		assertEquals("someuser", credentials.getUserId());
		assertEquals("somepassword", credentials.getPassword());
		masterKeyMockServer.verify();
	}

	@Test
	public void createUser_completeConfig() {		
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/user"))
			.andExpect(method(POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/user-config-complete"))))
			.andRespond(withResponse(jsonResource("data/user-credentials"), responseHeaders, HttpStatus.CREATED, "Created"));
		UserConfig userConfig = UserConfig.builder().airmail(true).alias("alias").deviceTokens("token1", "token2").tags("tag1", "tag2").udid("udid").email("someuser@somedomain.com").build();
		UserCredentials credentials = urbanAirship.userOperations().createUser(userConfig);
		assertEquals("https://go.urbanairship.com/api/user/someuser", credentials.getUserUrl());
		assertEquals("someuser", credentials.getUserId());
		assertEquals("somepassword", credentials.getPassword());
		masterKeyMockServer.verify();
	}
	
	@Test
	public void modifyUser() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/user/someuser"))
			.andExpect(method(PUT))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/user-config-complete"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "OK"));
		UserConfig userConfig = UserConfig.builder().airmail(true).alias("alias").deviceTokens("token1", "token2").tags("tag1", "tag2").udid("udid").email("someuser@somedomain.com").build();
		urbanAirship.userOperations().modifyUser("someuser", userConfig);
		masterKeyMockServer.verify();
	}

	@Test
	public void resetPassword() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/user/someuser/creds/reset"))
			.andExpect(method(POST))
			.andRespond(withResponse(jsonResource("data/user-credentials"), responseHeaders, HttpStatus.OK, "OK"));
		urbanAirship.userOperations().resetPassword("someuser");
		masterKeyMockServer.verify();
	}
	
	@Test
	public void deleteUser() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/user/someuser"))
			.andExpect(method(DELETE))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "OK"));
		urbanAirship.userOperations().deleteUser("someuser");
		masterKeyMockServer.verify();
	}
	
	@Test
	public void recoverAccount() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/user/recover"))
			.andExpect(method(POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/user-recover"))))
			.andRespond(withResponse(jsonResource("data/user-recover-response"), responseHeaders, HttpStatus.OK, "OK"));
		String recoveryId = urbanAirship.userOperations().recoverAccount("someuser@somedomain.com");
		assertEquals("1232134124", recoveryId);
		masterKeyMockServer.verify();		
	}
}
