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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OutgoingMessage {

	private List<String> users;
	
	private List<String> aliases;

	private List<String> tags;

	private String message;
	
	private String title;
	
	private String contentType;
	
	private Map<String, String> extra;
	
	private Push push;
	
	private OutgoingMessage(String message) {
		this.message = message;
	}
	
	public List<String> getUsers() {
		return users;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public List<String> getTags() {
		return tags;
	}

	public String getMessage() {
		return message;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public Map<String, String> getExtra() {
		return extra;
	}
	
	public Push getPush() {
		return push;
	}
	
	public boolean hasRecipients() {
		return users != null || aliases != null || tags != null;
	}

	public static OutgoingMessageBuilder builder(String message) {
		return new OutgoingMessageBuilder(message);
	}

	public static class Push {
		private Aps aps;
		
		public Push(Aps aps) {
			this.aps = aps;
		}
		
		public Aps getAps() {
			return aps;
		}
	}
	
	public static class OutgoingMessageBuilder {
		private List<String> users;
		private List<String> aliases;		
		private List<String> tags;		
		private String message;
		private String title;
		private String contentType;		
		private Map<String, String> extra;
		private Push push;
		
		public OutgoingMessageBuilder(String message) {
			this.message = message;
			this.users = new ArrayList<String>();
			this.aliases = new ArrayList<String>();
			this.tags = new ArrayList<String>();
		}
		
		public OutgoingMessageBuilder users(String user, String... moreUsers) {
			this.users.add(user);
			this.users.addAll(Arrays.asList(moreUsers));
			return this;
		}
		
		public OutgoingMessageBuilder aliases(String alias, String... moreAliases) {
			this.aliases.add(alias);
			this.aliases.addAll(Arrays.asList(moreAliases));
			return this;
		}

		public OutgoingMessageBuilder tags(String tag, String... moreTags) {
			this.tags.add(tag);
			this.tags.addAll(Arrays.asList(moreTags));
			return this;
		}
		
		public OutgoingMessageBuilder title(String title) {
			this.title = title;
			return this;
		}

		public OutgoingMessageBuilder contentType(String contentType) {
			this.contentType = contentType;
			return this;
		}
		
		public OutgoingMessageBuilder extra(Map<String, String> extra) {
			this.extra = extra;
			return this;
		}		
		
		public OutgoingMessageBuilder notify(String alert, String sound, String badge) {
			Aps aps = new Aps(alert);
			aps.setSound(sound);
			aps.setBadge(badge);
			this.push = new Push(aps);
			return this;
		}

		public OutgoingMessage build() {
			OutgoingMessage m = new OutgoingMessage(message);			
			if (!users.isEmpty()) {
				m.users = users;
			}			
			if (!aliases.isEmpty()) {
				m.aliases = aliases;
			}
			if (!tags.isEmpty()) {
				m.tags = tags;
			}
			m.title = title;
			m.contentType = contentType;
			m.extra = extra;
			m.push = push;
			return m;
		}
	}
	
}
