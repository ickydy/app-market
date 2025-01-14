package org.edupoll.market.service;

import java.net.URI;

import org.edupoll.market.model.KakaoOAuthToken;
import org.edupoll.market.model.KakaoUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Service
public class KakaoAPIService {
	
	@Value("${kakao.client.id}")
	String kakaoClientId;
	@Value("${kakao.redirect.uri}")
	String kakaoRedirectUri;

	public KakaoOAuthToken getOAuthToken(String code) {
		String uri = "https://kauth.kakao.com/oauth/token";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", kakaoClientId);
		body.add("redirect_uri", kakaoRedirectUri);
		body.add("code", code);

		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(body, headers, HttpMethod.POST,
				URI.create(uri));

		RestTemplate template = new RestTemplate();

		ResponseEntity<String> response = template.exchange(request, String.class);

		Gson gson = new Gson();
		KakaoOAuthToken oAuthToken = gson.fromJson(response.getBody(), KakaoOAuthToken.class);
		
		return oAuthToken;
	}
	
	public KakaoUserInfo getUserInfo(String accessToken) {
		String uri = "https://kapi.kakao.com/v2/user/me";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(headers, HttpMethod.GET,
				URI.create(uri));

		RestTemplate template = new RestTemplate();

		ResponseEntity<String> response = template.exchange(request, String.class);

		Gson gson = new Gson();
		KakaoUserInfo kakaoUserInfo = gson.fromJson(response.getBody(), KakaoUserInfo.class);
		
		return kakaoUserInfo;
	}
}
