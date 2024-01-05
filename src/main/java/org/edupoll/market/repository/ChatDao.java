package org.edupoll.market.repository;

import java.util.List;
import java.util.Map;

import org.edupoll.market.model.ChatMessage;
import org.edupoll.market.model.ChatRoom;

public interface ChatDao {
	public int createRoom(ChatRoom chatRoom);

	public ChatRoom findRoomByProductIdAndBuyerId(Map<String, Object> criteria);
	// productId, buyerId

	public int saveMessage(ChatMessage chatMessage);

	public ChatRoom findRoomByRoomId(String id);

	public List<ChatRoom> findRoomByAccountId(String logonAccountId);

	public List<ChatMessage> findMessagesByRoomId(String id);
	
	public List<ChatMessage> findAfterMessagesByRoomId(Map<String, Object> criteria);

	public int countUncheckedMessages(Map<String, Object> criteria);
	// chatRoomId, logonAccountId

	public int updateCheckedAt(Map<String, Object> criteria);
	// chatRoomId, logonAccountId
}
