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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.mobile.urbanairship.ContentReceipt;
import org.springframework.mobile.urbanairship.InvalidContentReceiptException;
import org.springframework.mobile.urbanairship.InventoryItem;
import org.springframework.mobile.urbanairship.Product;

public class InAppPurchaseTemplateTest extends AbstractUrbanAirshipApiTest {

	@Test
	public void getInventory() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/app/content"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("data/iap-inventory"), responseHeaders));
		List<InventoryItem> items = urbanAirship.inAppPurchaseOperations().getInventory();
		assertEquals("Content", items.get(0).getName());
		assertEquals("http://testserver/api/app/content/1234/", items.get(0).getUrl());
		assertEquals("http://com.urbanairship.preview.s3.amazonaws.com/content/<app_key>/1234/icon.jpg", items.get(0).getIconUrl());
		assertEquals("http://com.urbanairship.preview.s3.amazonaws.com/content/<app_key>/1234/preview.jpg", items.get(0).getPreviewUrl());
		assertEquals("https://go.urbanairship.com/api/app/content/1234/download", items.get(0).getDownloadUrl());
		assertEquals("1", items.get(0).getCurrentRevision());
		assertEquals("1234", items.get(0).getProductId());
		assertFalse(items.get(0).isFree());
		assertEquals(49137, items.get(0).getFileSize());
		assertEquals("Description", items.get(0).getDescription());
		assertEquals("Some Content", items.get(1).getName());
		assertEquals("http://testserver/api/app/content/1234/", items.get(1).getUrl());
		assertEquals("http://com.urbanairship.preview.s3.amazonaws.com/content/<app_key>/1235/icon.jpg", items.get(1).getIconUrl());
		assertEquals("http://com.urbanairship.preview.s3.amazonaws.com/content/<app_key>/1235/preview.jpg", items.get(1).getPreviewUrl());
		assertEquals("https://go.urbanairship.com/api/app/content/1235/download", items.get(1).getDownloadUrl());
		assertEquals("3", items.get(1).getCurrentRevision());
		assertEquals("1235", items.get(1).getProductId());
		assertTrue(items.get(1).isFree());
		assertEquals(58392, items.get(1).getFileSize());
		assertEquals("Some description", items.get(1).getDescription());
		mockServer.verify();
	}
	
	@Test
	public void getDownloadUrl_freeContent() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/app/content/12345/download"))
			.andExpect(method(POST))
			.andRespond(withResponse(jsonResource("data/iap-temporary-url"), responseHeaders));
		String downloadUrl = urbanAirship.inAppPurchaseOperations().getDownloadUrl("12345");
		assertEquals("http://somesite/content/download/12345", downloadUrl);
		mockServer.verify();
	}

	@Test
	public void getDownloadUrl_paidContent() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/app/content/12345/download"))
			.andExpect(method(POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/iap-download-paid"))))
			.andRespond(withResponse(jsonResource("data/iap-temporary-url"), responseHeaders));
		String downloadUrl = urbanAirship.inAppPurchaseOperations().getDownloadUrl("12345", new ContentReceipt("receipt_string", "udid1", "1.0"));
		assertEquals("http://somesite/content/download/12345", downloadUrl);
		mockServer.verify();
	}

	@Test(expected = InvalidContentReceiptException.class)
	public void getDownloadUrl_paidContent_notvalidated() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/app/content/12345/download"))
			.andExpect(method(POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/iap-download-paid"))))
			.andRespond(withResponse(jsonResource("data/iap-notvalidated"), responseHeaders));
		urbanAirship.inAppPurchaseOperations().getDownloadUrl("12345", new ContentReceipt("receipt_string", "udid1", "1.0"));
		mockServer.verify();
	}
	
	@Test
	public void checkForUpdates() {
		mockServer.expect(requestTo("https://go.urbanairship.com/api/app/updates"))
			.andExpect(method(POST))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/iap-check-for-updates"))))
			.andRespond(withResponse(jsonResource("data/iap-update-response"), responseHeaders));
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("1234", 3));
		products.add(new Product("FOOBAR", 1));
		List<Product> updates = urbanAirship.inAppPurchaseOperations().checkForUpdates(products);
		assertEquals(1, updates.size());
		assertEquals("1234", updates.get(0).getProductId());
		assertEquals(4, updates.get(0).getCurrentRevision());
		assertEquals("https://go.urbanairship.com/api/app/content/1234/download", updates.get(0).getDownloadUrl());
		mockServer.verify();
	}

}
