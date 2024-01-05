package org.edupoll.market.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.edupoll.market.repository.AccountDao;
import org.edupoll.market.repository.ChatDao;
import org.edupoll.market.repository.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatDao chatDao;
	private final ProductDao productDao;
	private final AccountDao accountDao;

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

	// 문의하기를 눌렀을때 중간 처리
	@GetMapping("/link")
	public String linkToChatRoom(@RequestParam int productId, @SessionAttribute Account logonAccount, Model model) {

		Product product = productDao.findById(productId);
		if (product.getAccountId().equals(logonAccount.getId())) {
			return "redirect:/chat/list";
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

	// 채팅목록 보여주기
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
				int size = messages.size();
				recentMessage = messages.get(size - 1).getContent();
			}
			Account buyerAccount = accountDao.findById(room.getBuyerId());
			ChatRoomComplex complex = ChatRoomComplex.builder() //
					.chatRoom(room) //
					.product(product) //
					.buyerAccount(buyerAccount) //
					.unchecked(numOfUnchecked) //
					.recentMessage(recentMessage) //
					.build();

			complexs.add(complex);
		}
		model.addAttribute("complexs", complexs);

		return "chat/list";
	}

	// 메시지 등록 처리
	@PostMapping("/room/{roomId}/message")
	@ResponseBody
	public String proceedAddChatMessage(@SessionAttribute Account logonAccount, @PathVariable String roomId,
			@RequestParam String content) {
		ChatMessage message = ChatMessage.builder().chatRoomId(roomId).talkerId(logonAccount.getId()).content(content)
				.build();

		chatDao.saveMessage(message);

		Gson gson = new Gson();
		Map<String, Object> response = new HashMap<>();
		response.put("result", "success");

		return gson.toJson(response);
	}

	// 특정 시점 이후의 메시지 목록을 전송
	@GetMapping(path = "/room/{roomId}/latest", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String proceedFindLatesMessages(@PathVariable String roomId, @RequestParam int lastMessageId,
			@SessionAttribute Account logonAccount) {

		// 특정 시점 이후의 메시지 목록 가져오기
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("chatRoomId", roomId);
		criteria.put("lastMessageId", lastMessageId);
		List<ChatMessage> messages = chatDao.findAfterMessagesByRoomId(criteria);
		DateFormat dateFormat = new SimpleDateFormat("a hh:mm");
		for (ChatMessage message : messages) {
			message.setStrSentAt(dateFormat.format(message.getSentAt()));
		}

		// 안읽은 메시지 읽음처리
		criteria.clear();
		criteria.put("chatRoomId", roomId);
		criteria.put("logonAccountId", logonAccount.getId());
		chatDao.updateCheckedAt(criteria);

		// 응답 보내주기
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", "success");
		response.put("messages", messages);
		Gson gson = new Gson();

		return gson.toJson(response);
	}
}
