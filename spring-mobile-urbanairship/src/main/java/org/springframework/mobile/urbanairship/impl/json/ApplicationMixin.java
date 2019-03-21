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
package org.springframework.mobile.urbanairship.impl.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.mobile.urbanairship.Application.PushServiceStatus;
import org.springframework.mobile.urbanairship.Application.PushStatus;
import org.springframework.mobile.urbanairship.Application.StoreKitMode;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class ApplicationMixin {

	@JsonProperty("name")
	String name;
	
	@JsonProperty("key")
	String key;
	
	@JsonProperty("secret")
	String secret;
	
	@JsonProperty("master_secret")
	String masterSecret;
	
	@JsonProperty("push_service_status")
	@JsonDeserialize(using=PushServiceStatusDeserializer.class)
	PushServiceStatus pushServiceStatus;
	
	@JsonProperty("push_status")
	@JsonDeserialize(using=PushStatusDeserializer.class)
	PushStatus pushStatus;
	
	@JsonProperty("storekit_mode")
	@JsonDeserialize(using=StoreKitModeDeserializer.class)
	StoreKitMode storeKitMode;
	
	@JsonProperty("app_url")
	String url;
	
	@JsonProperty("android_package_name")
	String androidPackageName;
	
	@JsonProperty("c2dm_auth_token")
	String c2dmAuthToken;
	
	@JsonProperty("blackberry_username")
	String blackberryUsername;
	
	@JsonProperty("blackberry_password")
	String blackberryPassword;
	
	@JsonProperty("blackberry_pushurl")
	String blackberryPushUrl;
	
	@JsonProperty("richpush_enabled")
	boolean richPushEnabled;
	
	private static class PushServiceStatusDeserializer extends JsonDeserializer<PushServiceStatus> {
		@Override
		public PushServiceStatus deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return PushServiceStatus.valueOf(jp.getText().toUpperCase());
		}
	}

	private static class PushStatusDeserializer extends JsonDeserializer<PushStatus> {
		@Override
		public PushStatus deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return PushStatus.valueOf(jp.getText().toUpperCase());
		}
	}

	private static class StoreKitModeDeserializer extends JsonDeserializer<StoreKitMode> {
		@Override
		public StoreKitMode deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return StoreKitMode.valueOf(jp.getText().toUpperCase());
		}
	}

}
