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

import org.springframework.mobile.urbanairship.Application.PushServiceStatus;

public class ApplicationData {

	private final String name;
	
	private final PushServiceStatus pushServiceStatus;
	
	private final String certificate;
	
	private final String certificatePassword;
	
	public ApplicationData(String name, PushServiceStatus pushServiceStatus, String certificate, String certificatePassword) {
		this.name = name;
		this.pushServiceStatus = pushServiceStatus;
		this.certificate = certificate;
		this.certificatePassword = certificatePassword;
	}
	
	public String getName() {
		return name;
	}

	public PushServiceStatus getPushServiceStatus() {
		return pushServiceStatus;
	}

	public String getCertificate() {
		return certificate;
	}

	public String getCertificatePassword() {
		return certificatePassword;
	}
	
}
