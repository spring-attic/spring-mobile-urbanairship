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

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.RequestMatchers.*;
import static org.springframework.test.web.client.ResponseCreators.*;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.urbanairship.Broadcast;
import org.springframework.mobile.urbanairship.Notification;
import org.springframework.mobile.urbanairship.PushResponse;
import org.springframework.mobile.urbanairship.PushStatistics;

public class PushTemplateTest extends AbstractUrbanAirshipApiTest {

	private static final String DEVICE_TOKEN = "FE66489F304DC75B8D6E8200DFF8A456E8DAEACEC428B427E9518741C92C6660";

	@Test
	public void pushNotification_withOneDeviceToken() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-onetoken"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").deviceTokens(DEVICE_TOKEN).build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withManyDeviceTokens() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-manytokens"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").deviceTokens("token1","token2","token3").build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withOneApid() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-oneapid"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").apids("apid1").build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withManyApids() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-manyapids"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").apids("apid1","apid2","apid3").build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withOneDevicePin() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-onedevicepin"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").devicePins("pin1").build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withManyDevicePins() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-manydevicepins"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").devicePins("pin1","pin2","pin3").build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withAliases() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-withaliases"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").aliases("alias1","alias2","alias3").build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withTags() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-withtags"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").tags("tag1","tag2","tag3").build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withExcludeTokens() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-withexcludes"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").tags("tag1").excludeTokens("token1","token2","token3").build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withSound() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-withsound"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").deviceTokens(DEVICE_TOKEN).sound("cat.caf").build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withBadgeCount() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-withbadgecount"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").deviceTokens(DEVICE_TOKEN).badge(10).build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withBadgeAuto() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-withbadgeauto"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").deviceTokens(DEVICE_TOKEN).autoBadge().build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withBadgeIncrement() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-withbadgeincrement"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").deviceTokens(DEVICE_TOKEN).badgeIncrement(5).build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withBadgeDecrement() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-withbadgedecrement"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").deviceTokens(DEVICE_TOKEN).badgeDecrement(3).build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotification_withExtraForAndroid() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-withextra"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		Map<String, String> extra = new LinkedHashMap<String, String>();
		extra.put("key1", "value1");
		extra.put("key2", "value2");
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").apids("apid1").extra(extra).build());
		assertNull(response);
		masterKeyMockServer.verify();
	}
	
	@Test
	public void pushNotification_withScheduleFor() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-withschedulefor"))))
			.andExpect(method(POST))
			.andRespond(withResponse(jsonResource("data/schedulefor-response"), responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse pushResponse = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").tags("tag1").scheduleFor("2010-07-27 22:48:00","2010-07-28 22:48:00").build());
		assertEquals(2, pushResponse.getScheduledNotifications().size());
		assertEquals("https://go.urbanairship.com/api/push/scheduled/XX", pushResponse.getScheduledNotifications().get(0));
		assertEquals("https://go.urbanairship.com/api/push/scheduled/XY", pushResponse.getScheduledNotifications().get(1));
		masterKeyMockServer.verify();
	}
	
	@Test
	public void pushNotification_withMixedDeviceTypes() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-mixeddevices"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		PushResponse response = urbanAirship.pushOperations().pushNotification(Notification.builder("Hey!").deviceTokens("token1","token2","token3").apids("apid1", "apid2", "apid3").devicePins("pin1", "pin2", "pin3").build());
		assertNull(response);
		masterKeyMockServer.verify();
	}

	@Test
	public void pushNotifications() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/batch"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-notifications"))))
			.andExpect(method(POST))
			.andRespond(withResponse(jsonResource("data/schedulefor-response"), responseHeaders, HttpStatus.OK, "Okay"));
		Notification notification1 = Notification.builder("Hey!").deviceTokens("token1", "token2").build();
		Notification notification2 = Notification.builder("Yo!").aliases("alias1", "alias2").build();
		urbanAirship.pushOperations().pushNotifications(Arrays.asList(notification1, notification2));
	}
	
	@Test
	public void sendBroadcast() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/broadcast/"))
			.andExpect(header("Content-Type", "application/json"))
			.andExpect(body(readCompactedJsonResource(jsonResource("data/push-broadcast"))))
			.andExpect(method(POST))
			.andRespond(withResponse("", responseHeaders, HttpStatus.OK, "Okay"));
		urbanAirship.pushOperations().sendBroadcast(Broadcast.builder("Hey!").build());
	}
	
	@Test
	@Ignore("Temporarily ignoring time-sensitive tests")
	public void getStatistics() {
		masterKeyMockServer.expect(requestTo("https://go.urbanairship.com/api/push/stats/?start=2012-04-04+00:00&end=2012-04-06+06:00"))
			.andExpect(method(GET))
			.andRespond(withResponse(jsonResource("data/statistics"), responseHeaders));
		List<PushStatistics> statistics = urbanAirship.pushOperations().getStatistics(new Date(1333515600000L), new Date(1333710000000L));
		assertEquals(6, statistics.size());
    // assertStatistics(statistics.get(0), 1331359200000L, 0, 0, 0, 0);
    // assertStatistics(statistics.get(1), 1331362800000L, 1, 2, 3, 6);
    // assertStatistics(statistics.get(2), 1331366400000L, 2, 4, 6, 12);
    // assertStatistics(statistics.get(3), 1331370000000L, 3, 6, 9, 18);
    // assertStatistics(statistics.get(4), 1331373600000L, 4, 8, 12, 24);
    // assertStatistics(statistics.get(5), 1331377200000L, 5, 10, 15, 30);
		masterKeyMockServer.verify();
	}
	
	private void assertStatistics(PushStatistics stats, long time, int androidMessages, int bbMessages, int c2dmMessages, int messages) {
		assertEquals(time, stats.getStart().getTime());
		assertEquals(bbMessages, stats.getBlackberryMessages());
		assertEquals(androidMessages, stats.getAndroidMessages());
		assertEquals(c2dmMessages, stats.getC2dmMessages());
		assertEquals(messages, stats.getMessages());
	}

}
