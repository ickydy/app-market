package org.edupoll.market.service;

import java.net.URI;

import org.edupoll.market.model.KakaoUserInfo;
import org.edupoll.market.model.NaverOAuthToken;
import org.edupoll.market.model.NaverUserInfo;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

public class NaverAPIService {
	public NaverOAuthToken getOAuthToken(String code, String state) {
		String uri = "https://nid.naver.com/oauth2.0/token";

		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", "lU7GLew0nobFRhFpafKh");
		body.add("client_secret", "T_mvX63AYI");
		body.add("code", code);
		body.add("state", state);

		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(body, HttpMethod.POST,
				URI.create(uri));

		RestTemplate template = new RestTemplate();

		ResponseEntity<String> response = template.exchange(request, String.class);

		Gson gson = new Gson();
		NaverOAuthToken oAuthToken = gson.fromJson(response.getBody(), NaverOAuthToken.class);

		return oAuthToken;
	}
	
	public NaverUserInfo getUserInfo(String accessToken) {
		String uri = "https://openapi.naver.com/v1/nid/me";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", "Bearer " + accessToken);

		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(headers, HttpMethod.GET,
				URI.create(uri));

		RestTemplate template = new RestTemplate();

		ResponseEntity<String> response = template.exchange(request, String.class);

		Gson gson = new Gson();
		NaverUserInfo naverUserInfo = gson.fromJson(response.getBody(), NaverUserInfo.class);
		
		return naverUserInfo;
	}
}
