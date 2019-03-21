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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mobile.urbanairship.AndroidDevice;
import org.springframework.mobile.urbanairship.AndroidDeviceConfig;
import org.springframework.mobile.urbanairship.BlackberryDevice;
import org.springframework.mobile.urbanairship.BlackberryDeviceConfig;
import org.springframework.mobile.urbanairship.IOSDevice;
import org.springframework.mobile.urbanairship.IOSDeviceConfig;
import org.springframework.mobile.urbanairship.RegistrationOperations;
import org.springframework.web.client.RestOperations;

public class RegistrationTemplate implements RegistrationOperations {

	private final RestOperations restOperations;

	public RegistrationTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;
	}
	
	public void registerIOSDevice(IOSDeviceConfig deviceConfig) {
		if (deviceConfig.isEmpty()) {
			restOperations.put(DEVICE_TOKENS_URL + deviceConfig.getDeviceToken(), "");
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<IOSDeviceConfig> requestEntity = new HttpEntity<IOSDeviceConfig>(deviceConfig, headers);
			restOperations.exchange(DEVICE_TOKENS_URL + deviceConfig.getDeviceToken(), HttpMethod.PUT, requestEntity, String.class);
		}
	}

	public void registerAndroidDevice(AndroidDeviceConfig deviceConfig) {
		if (deviceConfig.isEmpty()) {
			restOperations.put(APIDS_URL + deviceConfig.getApid(), "");
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<AndroidDeviceConfig> requestEntity = new HttpEntity<AndroidDeviceConfig>(deviceConfig, headers);
			restOperations.exchange(APIDS_URL + deviceConfig.getApid(), HttpMethod.PUT, requestEntity, String.class);
		}
	}

	public void registerBlackberryDevice(BlackberryDeviceConfig deviceConfig) {
		if (deviceConfig.isEmpty()) {
			restOperations.put(DEVICE_PINS_URL + deviceConfig.getDevicePin(), "");
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<BlackberryDeviceConfig> requestEntity = new HttpEntity<BlackberryDeviceConfig>(deviceConfig, headers);
			restOperations.exchange(DEVICE_PINS_URL + deviceConfig.getDevicePin(), HttpMethod.PUT, requestEntity, String.class);
		}
	}

	public IOSDevice getIOSDevice(String deviceToken) {
		return restOperations.getForObject(DEVICE_TOKENS_URL + deviceToken, IOSDevice.class);
	}
	
	public AndroidDevice getAndroidDevice(String apid) {
		return restOperations.getForObject(APIDS_URL + apid, AndroidDevice.class);
	}
	
	public BlackberryDevice getBlackberryDevice(String devicePin) {
		return restOperations.getForObject(DEVICE_PINS_URL + devicePin, BlackberryDevice.class);
	}
	
	public void removeIOSDevice(String deviceToken) {
		restOperations.delete(DEVICE_TOKENS_URL + deviceToken);
	}

	public void removeAndroidDevice(String apid) {
		restOperations.delete(APIDS_URL + apid);
	}

	public void removeBlackberryDevice(String devicePin) {
		restOperations.delete(DEVICE_PINS_URL + devicePin);
	}

	private static final String DEVICE_TOKENS_URL = URBANAIRSHIP_API_URL + "device_tokens/";

	private static final String APIDS_URL = URBANAIRSHIP_API_URL + "apids/";

	private static final String DEVICE_PINS_URL = URBANAIRSHIP_API_URL + "device_pins/";

}
