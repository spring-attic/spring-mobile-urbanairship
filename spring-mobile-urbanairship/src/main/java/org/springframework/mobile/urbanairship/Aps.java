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

public class Aps {

	private String alert;

	private String sound;
	
	private Object badge;

	Aps(String alert) {
		this.alert = alert;
	}
	
	public String getAlert() {
		return alert;
	}
	
	public String getSound() {
		return sound;
	}

	public Object getBadge() {
		return badge;
	}
	
	public void setBadge(Object badge) {
		this.badge = badge;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

}