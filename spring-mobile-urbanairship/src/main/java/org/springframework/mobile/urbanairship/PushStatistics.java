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

import java.util.Date;

public class PushStatistics {

	private final Date start;
	
	private final int androidMessages;
	
	private final int blackberryMessages;
	
	private final int c2dmMessages;
	
	private final int messages;
	
	public PushStatistics(Date start, int androidMessages, int blackberryMessages, int c2dmMessages, int messages) {
		this.start = start;
		this.blackberryMessages = blackberryMessages;
		this.androidMessages = androidMessages;
		this.c2dmMessages = c2dmMessages;
		this.messages = messages;		
	}
	
	public Date getStart() {
		return start;
	}

	public int getAndroidMessages() {
		return androidMessages;
	}

	public int getBlackberryMessages() {
		return blackberryMessages;
	}

	public int getC2dmMessages() {
		return c2dmMessages;
	}

	public int getMessages() {
		return messages;
	}
	
}
