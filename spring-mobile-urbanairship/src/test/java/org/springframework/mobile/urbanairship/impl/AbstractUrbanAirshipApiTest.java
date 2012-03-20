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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mobile.urbanairship.impl.UrbanAirshipTemplate;
import org.springframework.test.web.client.MockRestServiceServer;

public class AbstractUrbanAirshipApiTest {
	protected UrbanAirshipTemplate urbanAirship;
	protected MockRestServiceServer mockServer;
	protected MockRestServiceServer masterKeyMockServer;
	protected HttpHeaders responseHeaders;

	@Before
	public void setup() {
		urbanAirship = createUrbanAirshipTemplate();
		mockServer = MockRestServiceServer.createServer(urbanAirship.getRestTemplate());
		masterKeyMockServer = MockRestServiceServer.createServer(urbanAirship.getMasterKeyRestTemplate());
		responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
	}

	protected UrbanAirshipTemplate createUrbanAirshipTemplate() {
		return new UrbanAirshipTemplate("applicationKey", "applicationSecret", "masterSecret");
	}

	protected Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json", getClass());
	}

	protected String compactedJson(String jsonFilename) {
		return readCompactedJsonResource(jsonResource(jsonFilename));
	}
	
	protected String readCompactedJsonResource(Resource resource) {
		StringBuilder resourceText = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			while (reader.ready()) {
				resourceText.append(reader.readLine().trim().replace("\n", ""));
			}
		} catch (IOException e) {
		}		
		return resourceText.toString();
	}

}
