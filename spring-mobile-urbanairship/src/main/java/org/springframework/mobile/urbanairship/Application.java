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

public class Application {

	private String name;
	
	private String key;
	
	private String secret;
	
	private String masterSecret;
	
	private PushServiceStatus pushServiceStatus;
	
	private PushStatus pushStatus;
	
	private StoreKitMode storeKitMode;
	
	private String url;
	
	private String androidPackageName;
	
	private String c2dmAuthToken;
	
	private String blackberryUsername;
	
	private String blackberryPassword;
	
	private String blackberryPushUrl;
	
	private boolean richPushEnabled;
	
	private Application() {}
	
	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}

	public String getSecret() {
		return secret;
	}

	public String getMasterSecret() {
		return masterSecret;
	}

	public PushServiceStatus getPushServiceStatus() {
		return pushServiceStatus;
	}

	public PushStatus getPushStatus() {
		return pushStatus;
	}

	public StoreKitMode getStoreKitMode() {
		return storeKitMode;
	}

	public String getUrl() {
		return url;
	}

	public String getAndroidPackageName() {
		return androidPackageName;
	}

	public String getC2dmAuthToken() {
		return c2dmAuthToken;
	}

	public String getBlackberryUsername() {
		return blackberryUsername;
	}

	public String getBlackberryPassword() {
		return blackberryPassword;
	}

	public String getBlackberryPushUrl() {
		return blackberryPushUrl;
	}

	public boolean isRichPushEnabled() {
		return richPushEnabled;
	}
	
	public static enum PushServiceStatus {
		DEVELOPMENT, PRODUCTION, DISABLED,
	}
	
	public static enum StoreKitMode {
		DEVELOPMENT, PRODUCTION, DISABLED
	}

	public static enum PushStatus {
		NONE, PENDING, READY, ERROR, PWERROR, CONNECTION_FAILED, APPLE_REJECTED
	}
}
