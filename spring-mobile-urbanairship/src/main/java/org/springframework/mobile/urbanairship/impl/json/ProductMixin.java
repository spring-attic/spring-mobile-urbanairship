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

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonPropertyOrder({"product_id", "current_revision"})
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
abstract class ProductMixin {

	@JsonProperty("product_id")
	private String productId;
	
	@JsonProperty("current_revision")
	private int currentRevision;

	@JsonProperty("download_url")
	private String downloadUrl;
	
	@JsonCreator
	ProductMixin(
		@JsonProperty("product_id") String productId,
		@JsonProperty("current_revision") int currentRevision) {}
	
}
