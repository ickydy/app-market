package org.edupoll.market.controller;

import javax.servlet.http.HttpSession;

import org.edupoll.market.model.Account;
import org.edupoll.market.model.UpdateAddress;
import org.edupoll.market.model.UpdateProfile;
import org.edupoll.market.repository.AccountDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SettingController {

	private final AccountDao accountDao;

	@GetMapping({ "/settings", "/settings/profile" })
	public String showSettings(HttpSession session, Model model) {
		Account logonAccount = (Account) session.getAttribute("logonAccount");
		model.addAttribute("latitude", logonAccount.getLatitude());
		model.addAttribute("longitude", logonAccount.getLongitude());
		return "settings/form";
	}

	@PatchMapping("/settings/update/profile")
	public String updateProfile(@ModelAttribute UpdateProfile updateProfile, HttpSession session, Model model) {

		Account origin = (Account) session.getAttribute("logonAccount");
		Account updatedAccount = Account.builder().id(origin.getId()).nickname(updateProfile.getNickname()).build();
		accountDao.update(updatedAccount);
		session.setAttribute("logonAccount", accountDao.findById(origin.getId()));

		return "redirect:/settings";
	}

	@PatchMapping("/settings/update/address")
	public String updateAddress(@ModelAttribute UpdateAddress updateAddress, HttpSession session, Model model) {

		Account origin = (Account) session.getAttribute("logonAccount");
		Account updatedAccount = Account.builder().id(origin.getId()).address(updateAddress.getAddress())
				.latitude(updateAddress.getLatitude()).longitude(updateAddress.getLongitude()).build();
		accountDao.update(updatedAccount);
		session.setAttribute("logonAccount", accountDao.findById(origin.getId()));

		return "redirect:/settings";
	}
}
