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

public class RecoveryResponse {

	private final String recoveryStatusUrl;
	
	private final String recoveryStatusId;
	
	public RecoveryResponse(String recoveryStatusUrl) {
		this.recoveryStatusUrl = recoveryStatusUrl;
		if (recoveryStatusUrl.startsWith("https://go.urbanairship.com/api/user/recover/")) {
			// "https://go.urbanairship.com/api/user/recover/1232134124/"
			String[] split = recoveryStatusUrl.split("/");
			recoveryStatusId = split[6];
		} else {
			recoveryStatusId = null; // shouldn't happen unless Urban Airship changes the URL format
		}
	}
	
	public String getRecoveryStatusUrl() {
		return recoveryStatusUrl;
	}
	
	public String getRecoveryStatusId() {
		return recoveryStatusId;
	}
	
}
