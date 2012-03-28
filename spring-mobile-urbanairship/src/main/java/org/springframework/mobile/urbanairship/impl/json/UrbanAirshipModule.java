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

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.mobile.urbanairship.Android;
import org.springframework.mobile.urbanairship.AndroidDevice;
import org.springframework.mobile.urbanairship.AndroidDeviceConfig;
import org.springframework.mobile.urbanairship.Application;
import org.springframework.mobile.urbanairship.ApplicationData;
import org.springframework.mobile.urbanairship.Aps;
import org.springframework.mobile.urbanairship.Blackberry;
import org.springframework.mobile.urbanairship.BlackberryDevice;
import org.springframework.mobile.urbanairship.BlackberryDeviceConfig;
import org.springframework.mobile.urbanairship.Broadcast;
import org.springframework.mobile.urbanairship.Feed;
import org.springframework.mobile.urbanairship.FeedConfig;
import org.springframework.mobile.urbanairship.FeedNotificationTemplate;
import org.springframework.mobile.urbanairship.IOSDevice;
import org.springframework.mobile.urbanairship.IOSDeviceConfig;
import org.springframework.mobile.urbanairship.Notification;
import org.springframework.mobile.urbanairship.OutgoingMessage;
import org.springframework.mobile.urbanairship.PushResponse;
import org.springframework.mobile.urbanairship.PushStatistics;
import org.springframework.mobile.urbanairship.UserConfig;
import org.springframework.mobile.urbanairship.UserCredentials;
import org.springframework.mobile.urbanairship.impl.RecoveryRequest;
import org.springframework.mobile.urbanairship.impl.TagTemplate.AddRemove;
import org.springframework.mobile.urbanairship.impl.TagTemplate.ModifyTags;

public class UrbanAirshipModule extends SimpleModule {

	public UrbanAirshipModule() {
		super("UrbanAirshipModule", new Version(1, 0, 0, null));
	}
	
	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(Android.class, AndroidMixin.class);
		context.setMixInAnnotations(AndroidDevice.class, AndroidDeviceMixin.class);
		context.setMixInAnnotations(AndroidDeviceConfig.class, AndroidDeviceConfigMixin.class);
		context.setMixInAnnotations(Application.class, ApplicationMixin.class);
		context.setMixInAnnotations(ApplicationData.class, ApplicationDataMixin.class);
		context.setMixInAnnotations(Aps.class, ApsMixin.class);
		context.setMixInAnnotations(Blackberry.class, BlackberryMixin.class);
		context.setMixInAnnotations(BlackberryDevice.class, BlackberryDeviceMixin.class);
		context.setMixInAnnotations(BlackberryDeviceConfig.class, BlackberryDeviceConfigMixin.class);
		context.setMixInAnnotations(Broadcast.class, BroadcastMixin.class);
		context.setMixInAnnotations(Feed.class, FeedMixin.class);
		context.setMixInAnnotations(FeedConfig.class, FeedConfigMixin.class);
		context.setMixInAnnotations(FeedNotificationTemplate.class, FeedNotificationTemplateMixin.class);
		context.setMixInAnnotations(IOSDevice.class, IOSDeviceMixin.class);
		context.setMixInAnnotations(IOSDeviceConfig.class, IOSDeviceConfigMixin.class);
		context.setMixInAnnotations(OutgoingMessage.class, OutgoingMessageMixin.class);
		context.setMixInAnnotations(ModifyTags.class, ModifyTagsMixin.class);
		context.setMixInAnnotations(AddRemove.class, ModifyTagsMixin.AddRemoveMixin.class);
		context.setMixInAnnotations(Notification.class, NotificationMixin.class);
		context.setMixInAnnotations(PushResponse.class, PushResponseMixin.class);
		context.setMixInAnnotations(PushStatistics.class, PushStatisticsMixin.class);
		context.setMixInAnnotations(RecoveryRequest.class, RecoveryRequestMixin.class);
		context.setMixInAnnotations(RecoveryResponse.class, RecoveryResponseMixin.class);
		context.setMixInAnnotations(UserConfig.class, UserConfigMixin.class);
		context.setMixInAnnotations(UserCredentials.class, UserCredentialsMixin.class);
	}
	
}
