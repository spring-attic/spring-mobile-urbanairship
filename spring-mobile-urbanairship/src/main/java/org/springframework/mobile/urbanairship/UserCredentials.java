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
package org.springframework.mobile.urbanairship;

public class UserCredentials {

	private final String userUrl;
	
	private final String userId;
	
	private final String password;
	
	private UserCredentials(String userUrl, String userId, String password) {
		this.userUrl = userUrl;
		this.userId = userId;
		this.password = password;
	}
	
	public String getUserUrl() {
		return userUrl;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

}
