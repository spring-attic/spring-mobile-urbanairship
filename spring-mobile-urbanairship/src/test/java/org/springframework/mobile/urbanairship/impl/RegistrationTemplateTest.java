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

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.urbanairship.AndroidDeviceConfig;
import org.springframework.mobile.urbanairship.BlackberryDeviceConfig;
import org.springframework.mobile.urbanairship.Device;
import org.springframework.mobile.urbanairship.IOSDeviceConfig;

public class RegistrationTemplateTest extends AbstractUrbanAirshipApiTest {

	private static final String DEVICE_TOKEN = "FE66489F304DC75B8D6E8200DFF8A456E8DAEACEC428B427E9518741C92C6660";

	// iOS	
	@Test
	public void registeriOSDevice_tokenOnly() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN))
			.andExpect(method(PUT))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.registrationOperations().registerIOSDevice(IOSDeviceConfig.builder(DEVICE_TOKEN).build());
		mockServer.verify();
	}

	@Test
	public void registeriOSDevice_completeConfig() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN))
			.andExpect(method(PUT))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/registration-complete"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.registrationOperations().registerIOSDevice(IOSDeviceConfig.builder(DEVICE_TOKEN).alias("myAlias").tags("tag1", "tag2").badge(2).quietTime("22:00", "8:00", "America/Los_Angeles").build());
		mockServer.verify();
	}

	@Test
	public void registeriOSDevice_aliasOnly() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN))
			.andExpect(method(PUT))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/registration-aliasonly"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.registrationOperations().registerIOSDevice(IOSDeviceConfig.builder(DEVICE_TOKEN).alias("myAlias").build());
		mockServer.verify();
	}

	@Test
	public void registeriOSDevice_tagsOnly() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN))
			.andExpect(method(PUT))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/registration-tagsonly"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.registrationOperations().registerIOSDevice(IOSDeviceConfig.builder(DEVICE_TOKEN).tags("tag1", "tag2").build());
		mockServer.verify();
	}

	@Test
	public void registeriOSDevice_badgeOnly() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN))
			.andExpect(method(PUT))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/registration-badgeonly"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.registrationOperations().registerIOSDevice(IOSDeviceConfig.builder(DEVICE_TOKEN).badge(8).build());
		mockServer.verify();
	}

	@Test
	public void registeriOSDevice_quietTimeOnly() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN))
			.andExpect(method(PUT))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/registration-qtonly"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.registrationOperations().registerIOSDevice(IOSDeviceConfig.builder(DEVICE_TOKEN).quietTime("22:00", "8:00", "America/Los_Angeles").build());
		mockServer.verify();
	}

	@Test
	public void getDevice() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("data/token"), responseHeaders, HttpStatus.OK, "OK"));
		Device device = urbanAirship.registrationOperations().getiOSDevice(DEVICE_TOKEN);

		assertEquals(DEVICE_TOKEN, device.getDeviceToken());
		assertEquals("habuma", device.getAlias());
		assertEquals(2, device.getTags().size());
		assertEquals("tag1", device.getTags().get(0));
		assertEquals("tag2", device.getTags().get(1));
		assertEquals(4, device.getBadge());
		assertEquals(1331862066000L, device.getLastRegistration().getTime());
		mockServer.verify();
	}
	
	@Test
	public void removeDevice() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_tokens/" + DEVICE_TOKEN))
			.andExpect(method(DELETE))
			.andRespond(withResponse("", responseHeaders, HttpStatus.NO_CONTENT, "No Content"));
		urbanAirship.registrationOperations().removeiOSDevice(DEVICE_TOKEN);
		mockServer.verify();
	}
	
	// Android
	@Test
	public void registerAndroidDevice_apidOnly() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/apids/apid1"))
			.andExpect(method(PUT))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.registrationOperations().registerAndroidDevice(AndroidDeviceConfig.builder("apid1").build());
		mockServer.verify();
	}

	@Test
	public void registerAndroidDevice_completeConfig() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/apids/apid1"))
			.andExpect(method(PUT))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/registration-aliasandtags"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.registrationOperations().registerAndroidDevice(AndroidDeviceConfig.builder("apid1").alias("alias1").tags("tag1", "tag2").build());
		mockServer.verify();
	}
	
	// Blackberry
	@Test
	public void registerBlackberryDevice_apidOnly() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_pins/pin1"))
			.andExpect(method(PUT))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.registrationOperations().registerBlackberryDevice(BlackberryDeviceConfig.builder("pin1").build());
		mockServer.verify();
	}

	@Test
	public void registerBlackberryDevice_completeConfig() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/device_pins/pin1"))
			.andExpect(method(PUT))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/registration-aliasandtags"))))
			.andRespond(withResponse("", responseHeaders, HttpStatus.CREATED, "Created"));
		urbanAirship.registrationOperations().registerBlackberryDevice(BlackberryDeviceConfig.builder("pin1").alias("alias1").tags("tag1", "tag2").build());
		mockServer.verify();
	}

}
