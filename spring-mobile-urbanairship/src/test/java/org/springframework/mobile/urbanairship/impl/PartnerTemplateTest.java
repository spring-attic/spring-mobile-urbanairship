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

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.RequestMatchers.*;
import static org.springframework.test.web.client.ResponseCreators.*;

import java.net.URI;
import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.urbanairship.Application;
import org.springframework.mobile.urbanairship.ApplicationData;
import org.springframework.mobile.urbanairship.Application.PushServiceStatus;
import org.springframework.mobile.urbanairship.Application.PushStatus;
import org.springframework.mobile.urbanairship.Application.StoreKitMode;

public class PartnerTemplateTest extends AbstractUrbanAirshipApiTest {

	@Test
	public void getApplications() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/partner/apps"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("data/application-list"), responseHeaders, HttpStatus.OK, "OK"));
		List<Application> apps = urbanAirship.partnerOperations().getApplications();
		assertEquals(2, apps.size());
		assertEquals("com.foo.bar", apps.get(0).getName());
		assertEquals("XYZ123", apps.get(0).getKey());
		assertEquals("F89X12", apps.get(0).getSecret());
		assertEquals("AbC232", apps.get(0).getMasterSecret());
		assertEquals(PushServiceStatus.DEVELOPMENT, apps.get(0).getPushServiceStatus());
		assertEquals(PushStatus.NONE, apps.get(0).getPushStatus());
		assertEquals(StoreKitMode.DISABLED, apps.get(0).getStoreKitMode());
		assertEquals("https://go.urbanairship.com/api/partner/apps/XYZ123", apps.get(0).getUrl());
		assertEquals("com.foo.bar", apps.get(0).getAndroidPackageName());
		assertEquals("c2dmToken", apps.get(0).getC2dmAuthToken());
		assertEquals("bbUser", apps.get(0).getBlackberryUsername());
		assertEquals("bbPassword", apps.get(0).getBlackberryPassword());
		assertEquals("http://bbpush.com", apps.get(0).getBlackberryPushUrl());
		assertFalse(apps.get(0).isRichPushEnabled());
		assertEquals("com.foo.baz", apps.get(1).getName());
		assertEquals("ABC321", apps.get(1).getKey());
		assertEquals("53CR3T", apps.get(1).getSecret());
		assertEquals("B1G53CR3T", apps.get(1).getMasterSecret());
		assertEquals(PushServiceStatus.PRODUCTION, apps.get(1).getPushServiceStatus());
		assertEquals(PushStatus.READY, apps.get(1).getPushStatus());
		assertEquals(StoreKitMode.DISABLED, apps.get(1).getStoreKitMode());
		assertEquals("https://go.urbanairship.com/api/partner/apps/ABC321", apps.get(1).getUrl());
		assertEquals("com.foo.baz", apps.get(1).getAndroidPackageName());
		assertEquals("c2dmToken", apps.get(1).getC2dmAuthToken());
		assertEquals("bbUser", apps.get(1).getBlackberryUsername());
		assertEquals("bbPassword", apps.get(1).getBlackberryPassword());
		assertEquals("http://bbpush.com", apps.get(1).getBlackberryPushUrl());
		assertFalse(apps.get(1).isRichPushEnabled());		
		mockServer.verify();
	}
	
	@Test
	public void createApplication() {
		responseHeaders.setLocation(URI.create("https://go.urbanairship.com/api/partner/apps/XYZ123"));
		String applicationDataJson = "{\"name\":\"com.foo.bar\",\"push_service_status\":\"development\",\"certificate\":\"somecertificate\",\"certificate_password\":\"certpassword\"}";
		mockServer.expect(requestTo("https://go.urbanairship.com/api/partner/apps"))
			.andExpect(method(POST))
			.andExpect(body(applicationDataJson))
			.andRespond(withResponse(applicationDataJson, responseHeaders, HttpStatus.CREATED, "Created"));
		ApplicationData applicationData = new ApplicationData("com.foo.bar", PushServiceStatus.DEVELOPMENT, "somecertificate", "certpassword");		
		URI applicationUrl = urbanAirship.partnerOperations().createApplication(applicationData);
		assertEquals(URI.create("https://go.urbanairship.com/api/partner/apps/XYZ123"), applicationUrl);
		mockServer.verify();		
	}
	
	@Test
	public void updateApplication_withKey() {
		String applicationDataJson = "{\"name\":\"com.foo.bar\",\"push_service_status\":\"development\",\"certificate\":\"somecertificate\",\"certificate_password\":\"certpassword\"}";
		mockServer.expect(requestTo("https://go.urbanairship.com/api/partner/apps/XYZ123"))
			.andExpect(method(PUT))
			.andExpect(body(applicationDataJson))
			.andRespond(withResponse(applicationDataJson, responseHeaders, HttpStatus.OK, "OK"));
		ApplicationData applicationData = new ApplicationData("com.foo.bar", PushServiceStatus.DEVELOPMENT, "somecertificate", "certpassword");		
		urbanAirship.partnerOperations().updateApplication("XYZ123", applicationData);
		mockServer.verify();		
	}

	@Test
	public void updateApplication_withUri() {
		URI applicationUri = URI.create("https://go.urbanairship.com/api/partner/apps/XYZ123");
		String applicationDataJson = "{\"name\":\"com.foo.bar\",\"push_service_status\":\"development\",\"certificate\":\"somecertificate\",\"certificate_password\":\"certpassword\"}";
		mockServer.expect(requestTo("https://go.urbanairship.com/api/partner/apps/XYZ123"))
			.andExpect(method(PUT))
			.andExpect(body(applicationDataJson))
			.andRespond(withResponse(applicationDataJson, responseHeaders, HttpStatus.OK, "OK"));
		ApplicationData applicationData = new ApplicationData("com.foo.bar", PushServiceStatus.DEVELOPMENT, "somecertificate", "certpassword");		
		urbanAirship.partnerOperations().updateApplication(applicationUri, applicationData);
		mockServer.verify();		
	}

	
}
