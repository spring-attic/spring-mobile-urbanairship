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

import static org.springframework.http.HttpMethod.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mobile.urbanairship.ContentReceipt;
import org.springframework.mobile.urbanairship.InAppPurchaseOperations;
import org.springframework.mobile.urbanairship.InvalidContentReceiptException;
import org.springframework.mobile.urbanairship.InventoryItem;
import org.springframework.mobile.urbanairship.Product;
import org.springframework.web.client.RestOperations;

public class InAppPurchaseTemplate implements InAppPurchaseOperations {

	private final RestOperations restOperations;

	public InAppPurchaseTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;
	}
	
	public List<InventoryItem> getInventory() {
		return restOperations.getForObject(IAP_URL + "content", Inventory.class);
	}
	
	public String getDownloadUrl(String productId) {
		DownloadUrl downloadUrl = restOperations.postForObject(IAP_URL + "content/" + productId + "/download", "", DownloadUrl.class);
		return downloadUrl.getContentUrl();
	}

	public String getDownloadUrl(String productId, ContentReceipt receipt) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ContentReceipt> requestEntity = new HttpEntity<ContentReceipt>(receipt, headers);
		DownloadUrl response = restOperations.exchange(IAP_URL + "content/" + productId + "/download", POST, requestEntity, DownloadUrl.class).getBody();
		if (response.getContentUrl() == null || response.getReceiptStatus() != null) {
			throw new InvalidContentReceiptException(response.getReceiptStatus(), response.getServerResponse());
		}
		return response.getContentUrl();
	}
	
	public List<Product> checkForUpdates(List<Product> products) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<Product>> requestEntity = new HttpEntity<List<Product>>(products, headers);
		return restOperations.exchange(IAP_URL + "updates", POST, requestEntity, ProductList.class).getBody();
	}
	
	private static final String IAP_URL = "https://go.urbanairship.com/api/app/";

	@SuppressWarnings("serial")
	private static class Inventory extends ArrayList<InventoryItem> {}

	@SuppressWarnings("serial")
	private static class ProductList extends ArrayList<Product> {}
}
