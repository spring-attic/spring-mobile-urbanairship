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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class InventoryItemMixin {

	InventoryItemMixin(
		@JsonProperty("name") String name, 
		@JsonProperty("url") String url, 
		@JsonProperty("icon_url") String iconUrl, 
		@JsonProperty("preview_url") String previewUrl, 
		@JsonProperty("download_url") String downloadUrl, 
		@JsonProperty("current_revision") String currentRevision, 
		@JsonProperty("product_id") String productId, 
		@JsonProperty("free") boolean free, 
		@JsonProperty("file_size") int fileSize, 
		@JsonProperty("description") String description) {}
	
}
