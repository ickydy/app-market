package org.edupoll.market.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.edupoll.market.model.Account;
import org.edupoll.market.model.ChatMessage;
import org.edupoll.market.model.ChatRoom;
import org.edupoll.market.model.ChatRoomComplex;
import org.edupoll.market.model.Product;
import org.edupoll.market.repository.ChatDao;
import org.edupoll.market.repository.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatDao chatDao;
	private final ProductDao productDao;

	// 특정 채팅방에 입장을 처리할 매핑
	@GetMapping("/room/{id}")
	public String showChatRoom(@PathVariable String id, @SessionAttribute Account logonAccount, Model model) {
		ChatRoom chatRoom = chatDao.findRoomByRoomId(id);
		if (chatRoom == null || (!logonAccount.getId().equals(chatRoom.getSellerId())
				&& !logonAccount.getId().equals(chatRoom.getBuyerId()))) {
			return "chat/error";
		}

		Product product = productDao.findById(chatRoom.getProductId());

		List<ChatMessage> messages = chatDao.findMessagesByRoomId(id);
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("chatRoomId", id);
		criteria.put("logonAccountId", logonAccount.getId());
		chatDao.updateCheckedAt(criteria);

		model.addAttribute("chatRoom", chatRoom);
		model.addAttribute("product", product);
		model.addAttribute("chatMessages", messages);

		return "chat/room";
	}

	@GetMapping("/room")
	public String linkToChatRoom(@RequestParam int productId, @SessionAttribute Account logonAccount, Model model) {

		Product product = productDao.findById(productId);
		if (product.getAccountId().equals(logonAccount.getId())) {
			return "redirect:product/" + productId;
		}

		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("productId", productId);
		criteria.put("buyerId", logonAccount.getId());

		ChatRoom found = chatDao.findRoomByProductIdAndBuyerId(criteria);
		if (found != null) {
			return "redirect:/chat/room/" + found.getId();
		}

		String uuid = UUID.randomUUID().toString();
		String chatRoomId = uuid.split("-")[4].toUpperCase();
		ChatRoom chatRoom = ChatRoom.builder() //
				.id(chatRoomId) //
				.productId(productId) //
				.sellerId(product.getAccountId()) //
				.buyerId(logonAccount.getId()) //
				.build();

		chatDao.createRoom(chatRoom);

		return "redirect:/chat/room/" + chatRoomId;
	}

	@GetMapping("/list")
	public String showRoomList(@SessionAttribute Account logonAccount, Model model) {

		List<ChatRoomComplex> complexs = new ArrayList<>();
		List<ChatRoom> rooms = chatDao.findRoomByAccountId(logonAccount.getId());
		for (ChatRoom room : rooms) {
			Map<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("chatRoomId", room.getId());
			criteria.put("logonAccountId", logonAccount.getId());
			int numOfUnchecked = chatDao.countUncheckedMessages(criteria);
			Product product = productDao.findById(room.getProductId());
			List<ChatMessage> messages = chatDao.findMessagesByRoomId(room.getId());
			String recentMessage = null;
			if (messages == null || messages.isEmpty()) {
				recentMessage = null;
			} else {
				recentMessage = messages.get(0).getContent();
			}
			ChatRoomComplex complex = ChatRoomComplex.builder() //
					.chatRoom(room) //
					.product(product) //
					.unchecked(numOfUnchecked) //
					.recentMessage(recentMessage) //
					.build();

			complexs.add(complex);
		}
		model.addAttribute("complexs", complexs);

		return "chat/list";
	}
}
