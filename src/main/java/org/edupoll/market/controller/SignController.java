package org.edupoll.market.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.edupoll.market.model.Account;
import org.edupoll.market.model.KakaoOAuthToken;
import org.edupoll.market.model.KakaoUserInfo;
import org.edupoll.market.model.NaverOAuthToken;
import org.edupoll.market.model.NaverUserInfo;
import org.edupoll.market.repository.AccountDao;
import org.edupoll.market.service.KakaoAPIService;
import org.edupoll.market.service.NaverAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignController {

	@Value("${kakao.client.id}")
	String kakaoClientId;
	@Value("${kakao.redirect.uri}")
	String kakaoRedirectUri;
	@Value("${naver.client.id}")
	String naverClientId;
	@Value("${naver.client.secret}")
	String naverClientSecret;
	@Value("${naver.redirect.uri}")
	String naverRedirectUri;

	private final ServletContext application;
	private final AccountDao accountDao;
	private final KakaoAPIService kakaoAPIService;
	private final NaverAPIService naverAPIService;

	@GetMapping("/signin")
	public String showSignin(Model model) {

		String kakaoLoginLink = "https://kauth.kakao.com/oauth/authorize?" + "client_id=${client_id}&response_type=code"
				+ "&redirect_uri=${redirect_uri}";

		kakaoLoginLink = kakaoLoginLink.replace("${client_id}", kakaoClientId);
		kakaoLoginLink = kakaoLoginLink.replace("${redirect_uri}", kakaoRedirectUri);
		kakaoLoginLink = kakaoLoginLink.replace("${contextPath}", application.getContextPath());

		String naverLoginLink = "https://nid.naver.com/oauth2.0/authorize?" + "response_type=code" + "&client_id="
				+ naverClientId + "&state=state&redirect_uri=" + naverRedirectUri;

		model.addAttribute("naverLoginLink", naverLoginLink);
		model.addAttribute("kakaoLoginLink", kakaoLoginLink);

		return "menu/signin";
	}

	@GetMapping("/callback/kakao")
	public String acceptKakaoCode(@RequestParam String code, HttpSession session) {

		KakaoOAuthToken oAuthToken = kakaoAPIService.getOAuthToken(code);

		KakaoUserInfo kakaoUserInfo = kakaoAPIService.getUserInfo(oAuthToken.getAccessToken());

		String id = "k" + kakaoUserInfo.getId();
		Account account = accountDao.findById(id);
		if (account == null) {
			Account one = Account.builder().id(id).nickname(kakaoUserInfo.getProfile().getNickname()).platform("kakao")
					.accessToken(oAuthToken.getAccessToken())
					.profileImageUrl(kakaoUserInfo.getProfile().getProfileImage()).build();
			accountDao.insert(one);

			session.setAttribute("logonAccount", one);

		} else {
			account.setAccessToken(oAuthToken.getAccessToken());
//			account.setNickname(kakaoUserInfo.getProfile().getNickname());
//			account.setProfileImageUrl(kakaoUserInfo.getProfile().getProfileImage());
			accountDao.update(account);

			session.setAttribute("logonAccount", account);
		}

		return "redirect:/";
	}

	@GetMapping("/callback/naver")
	public String acceptNaverCode(@RequestParam String code, @RequestParam String state, HttpSession session) {

		NaverOAuthToken oAuthToken = naverAPIService.getOAuthToken(code, state);

		NaverUserInfo naverUserInfo = naverAPIService.getUserInfo(oAuthToken.getAccessToken());

		String id = "n" + naverUserInfo.getProfile().getId();
		Account account = accountDao.findById(id);
		if (account == null) {
			Account one = Account.builder().id(id).nickname(naverUserInfo.getProfile().getName()).platform("naver")
					.accessToken(oAuthToken.getAccessToken())
					.profileImageUrl(naverUserInfo.getProfile().getProfileImage()).build();
			accountDao.insert(one);

			session.setAttribute("logonAccount", one);
		} else {
			account.setAccessToken(oAuthToken.getAccessToken());
			accountDao.update(account);

			session.setAttribute("logonAccount", account);
		}
		return "redirect:/";
	}

	@GetMapping("/signout")
	public String proceedSignout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
