package org.edupoll.market.controller;

import org.edupoll.market.model.KakaoOAuthToken;
import org.edupoll.market.model.KakaoUserInfo;
import org.edupoll.market.model.NaverOAuthToken;
import org.edupoll.market.model.NaverUserInfo;
import org.edupoll.market.service.KakaoAPIService;
import org.edupoll.market.service.NaverAPIService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignController {

	private final KakaoAPIService kakaoAPIService;
	private final NaverAPIService naverAPIService;
	
	@GetMapping("/signin")
	public String showSignin() {
		return "menu/signin";
	}

	@GetMapping("/callback/kakao")
	public String acceptKakaoCode(@RequestParam String code) {

		KakaoOAuthToken oAuthToken = kakaoAPIService.getOAuthToken(code);
		System.out.println(oAuthToken.getAccessToken());
		
		KakaoUserInfo kakaoUserInfo = kakaoAPIService.getUserInfo(oAuthToken.getAccessToken());
		System.out.println(kakaoUserInfo);
		
		return null;
	}
	
	@GetMapping("/callback/naver")
	public String acceptNaverCode(@RequestParam String code, @RequestParam String state) {

		NaverOAuthToken oAuthToken = naverAPIService.getOAuthToken(code, state);
		
		NaverUserInfo naverUserInfo = naverAPIService.getUserInfo(oAuthToken.getAccessToken());
		
		return null;
	}
}
