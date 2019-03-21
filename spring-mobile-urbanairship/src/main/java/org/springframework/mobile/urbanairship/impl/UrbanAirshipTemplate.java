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

import java.util.ArrayList;
import java.util.List;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mobile.urbanairship.FeedOperations;
import org.springframework.mobile.urbanairship.InAppPurchaseOperations;
import org.springframework.mobile.urbanairship.PartnerOperations;
import org.springframework.mobile.urbanairship.PushOperations;
import org.springframework.mobile.urbanairship.RegistrationOperations;
import org.springframework.mobile.urbanairship.RichPushOperations;
import org.springframework.mobile.urbanairship.TagOperations;
import org.springframework.mobile.urbanairship.UrbanAirship;
import org.springframework.mobile.urbanairship.UserOperations;
import org.springframework.mobile.urbanairship.impl.json.UrbanAirshipModule;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

public class UrbanAirshipTemplate implements UrbanAirship {

	private FeedOperations feedOperations;
	
	private InAppPurchaseOperations inAppPurchaseOperations;
	
	private PartnerOperations partnerOperations;
	
	private PushOperations pushOperations;
	
	private RegistrationOperations registrationOperations;
	
	private RichPushOperations richPushOperations;
	
	private TagOperations tagOperations;
	
	private UserOperations userOperations;

	private RestTemplate restTemplate;
	
	private RestTemplate masterKeyRestTemplate;

	private ObjectMapper objectMapper;

	public UrbanAirshipTemplate(String applicationKey, String applicationSecret, String masterSecret) {
		this.restTemplate = createRestTemplate(applicationKey, applicationSecret);
		this.masterKeyRestTemplate = createRestTemplate(applicationKey, masterSecret);
		this.restTemplate.setMessageConverters(getMessageConverters());
		this.masterKeyRestTemplate.setMessageConverters(getMessageConverters());
		initSubApis();
	}

	public FeedOperations feedOperations() {
		return feedOperations;
	}
	
	public InAppPurchaseOperations inAppPurchaseOperations() {
		return inAppPurchaseOperations;
	}
	
	public PartnerOperations partnerOperations() {
		return partnerOperations;
	}
	
	public PushOperations pushOperations() {
		return pushOperations;
	}
	
	public RegistrationOperations registrationOperations() {
		return registrationOperations;
	}
	
	public RichPushOperations richPushOperations() {
		return richPushOperations;
	}
	
	public RestOperations restOperations() {
		return getRestTemplate();
	}
	
	public TagOperations tagOperations() {
		return tagOperations;
	}
	
	public UserOperations userOperations() {
		return userOperations;
	}

	public RestOperations masterKeyRestOperations() {
		return getMasterKeyRestTemplate();
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public RestTemplate getMasterKeyRestTemplate() {
		return masterKeyRestTemplate;
	}

	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(getJsonMessageConverter());
		return messageConverters;
	}

	protected MappingJacksonHttpMessageConverter getJsonMessageConverter() {
		MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		objectMapper = new ObjectMapper();				
		objectMapper.registerModule(new UrbanAirshipModule());
		converter.setObjectMapper(objectMapper);		
		return converter;
	}

	private RestTemplate createRestTemplate(String key, String secret) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(key, secret));
		httpClient.setCredentialsProvider(credentialsProvider);
		requestFactory.setHttpClient(httpClient);
		return new RestTemplate(requestFactory);
	}	

	private void initSubApis() {
		feedOperations = new FeedTemplate(masterKeyRestTemplate);
		inAppPurchaseOperations = new InAppPurchaseTemplate(restOperations());
		partnerOperations = new PartnerTemplate(restOperations());
		pushOperations = new PushTemplate(masterKeyRestOperations());
		registrationOperations = new RegistrationTemplate(restOperations());
		richPushOperations = new RichPushTemplate(masterKeyRestTemplate);
		tagOperations = new TagTemplate(restOperations());
		userOperations = new UserTemplate(masterKeyRestTemplate);
	}
	
	static final String URBANAIRSHIP_API_URL = "https://go.urbanairship.com/api/";
	
}
