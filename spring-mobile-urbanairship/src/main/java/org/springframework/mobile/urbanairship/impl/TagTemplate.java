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

import java.util.List;

import org.springframework.mobile.urbanairship.TagOperations;
import org.springframework.web.client.RestOperations;

public class TagTemplate implements TagOperations {

	private final RestOperations restOperations;

	public TagTemplate(RestOperations restOperations) {
		this.restOperations = restOperations;
	}
	
	public List<String> getTags() {
		return restOperations.getForObject(URBANAIRSHIP_API_URL + "tags", TagList.class).getTags();
	}

	public List<String> getTags(String deviceToken) {
		return restOperations.getForObject(URBANAIRSHIP_API_URL + "device_tokens/" + deviceToken + "/tags", TagList.class).getTags();
	}

	public void createTag(String tagName) {
		restOperations.put(URBANAIRSHIP_API_URL + "tags/" + tagName, "");
	}
	
	public void createTag(String deviceToken, String tagName) {
		restOperations.put(URBANAIRSHIP_API_URL + "device_tokens/" + deviceToken + "/tags/" + tagName, "");
	}
	
	public void modifyTagTokens(String tagName, List<String> tokensToAdd, List<String> tokensToRemove) {
		ModifyTags modifyTags = new ModifyTags();
		modifyTags.setDeviceTokens(tokensToAdd, tokensToRemove);
		restOperations.postForObject(URBANAIRSHIP_API_URL + "tags/" + tagName, modifyTags, String.class);
	}
	
	public void modifyTagPins(String tagName, List<String> pinsToAdd, List<String> pinsToRemove) {
		ModifyTags modifyTags = new ModifyTags();
		modifyTags.setDevicePins(pinsToAdd, pinsToRemove);
		restOperations.postForObject(URBANAIRSHIP_API_URL + "tags/" + tagName, modifyTags, String.class);
	}
	
	public void modifyTagAppIds(String tagName, List<String> idsToAdd, List<String> idsToRemove) {
		ModifyTags modifyTags = new ModifyTags();
		modifyTags.setAppIds(idsToAdd, idsToRemove);
		restOperations.postForObject(URBANAIRSHIP_API_URL + "tags/" + tagName, modifyTags, String.class);
	}
	
	public void removeTags() {
		restOperations.put(URBANAIRSHIP_API_URL + "tags", "");
	}
	
	public void removeTag(String deviceToken, String tagName) {
		restOperations.delete(URBANAIRSHIP_API_URL + "device_tokens/" + deviceToken + "/tags/" + tagName);
	}
	
	private static class TagList {
		private List<String> tags;
		
		public List<String> getTags() {
			return tags;
		}
	}
	
	public static class ModifyTags {
		private AddRemove deviceTokens;
		private AddRemove devicePins;
		private AddRemove appIds;
		
		private ModifyTags() {}

		public void setDeviceTokens(List<String> tokensToAdd, List<String> tokensToRemove) {
			if (deviceTokens == null) {
				deviceTokens = new AddRemove();
				if (tokensToAdd != null && !tokensToAdd.isEmpty()) {
					deviceTokens.add = tokensToAdd;
				}
				if (tokensToRemove != null && !tokensToRemove.isEmpty()) {
					deviceTokens.remove = tokensToRemove;
				}				
			}
		}

		public void setDevicePins(List<String> pinsToAdd, List<String> pinsToRemove) {
			if (devicePins == null) {
				devicePins = new AddRemove();
				if (pinsToAdd != null && !pinsToAdd.isEmpty()) {
					devicePins.add = pinsToAdd;
				}
				if (pinsToRemove != null && !pinsToRemove.isEmpty()) {
					devicePins.remove = pinsToRemove;
				}				
			}
		}

		public void setAppIds(List<String> appIdsToAdd, List<String> appIdsToRemove) {
			if (appIds == null) {
				appIds = new AddRemove();
				if (appIdsToAdd != null && !appIdsToAdd.isEmpty()) {
					appIds.add = appIdsToAdd;
				}
				if (appIdsToRemove != null && !appIdsToRemove.isEmpty()) {
					appIds.remove = appIdsToRemove;
				}				
			}
		}
	}
	
	@SuppressWarnings("unused")
	public static class AddRemove {
		private List<String> add;
		private List<String> remove;
	}
}
