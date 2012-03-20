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
package org.springframework.mobile.urbanairship.impl.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.mobile.urbanairship.Application.PushServiceStatus;

@JsonPropertyOrder({"name", "push_service_status", "certificate", "certificate_password"})
abstract class ApplicationDataMixin {

	@JsonProperty("name") String name;
	
	@JsonProperty("push_service_status") @JsonSerialize(using=PushServiceStatusSerializer.class) PushServiceStatus pushServiceStatus;
	
	@JsonProperty("certificate") String certificate;
	
	@JsonProperty("certificate_password") String certificatePassword;

	private static class PushServiceStatusSerializer extends JsonSerializer<PushServiceStatus> {
		@Override
		public void serialize(PushServiceStatus value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			jgen.writeString(value.toString().toLowerCase());
		}
	}

}
