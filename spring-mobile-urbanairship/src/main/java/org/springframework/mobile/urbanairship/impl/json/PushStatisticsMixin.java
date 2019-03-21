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

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class PushStatisticsMixin {

	@JsonCreator
	PushStatisticsMixin(
			@JsonProperty("start") @JsonDeserialize(using=DateDeserializer.class) Date start,
			@JsonProperty("android_messages") int androidMessages,
			@JsonProperty("bb_messages") int blackberryMessages,
			@JsonProperty("c2dm_messages") int c2dmMessages,
			@JsonProperty("messages") int messages) {}
	
}
