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
package org.springframework.mobile.urbanairship;

public class InventoryItem {
	
	private final String name;
	
	private final String url;
	
	private final String iconUrl;
	
	private final String previewUrl;
	
	private final String downloadUrl;
	
	private final String currentRevision;
	
	private final String productId;
	
	private final boolean free;
	
	private final int fileSize;
	
	private final String description;

	private InventoryItem(String name, String url, String iconUrl, String previewUrl, String downloadUrl, String currentRevision, String productId, boolean free, int fileSize, String description) {
		this.name = name;
		this.url = url;
		this.iconUrl = iconUrl;
		this.previewUrl = previewUrl;
		this.downloadUrl = downloadUrl;
		this.currentRevision = currentRevision;
		this.productId = productId;
		this.free = free;
		this.fileSize = fileSize;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public String getCurrentRevision() {
		return currentRevision;
	}

	public String getProductId() {
		return productId;
	}

	public boolean isFree() {
		return free;
	}

	public int getFileSize() {
		return fileSize;
	}

	public String getDescription() {
		return description;
	}

}
