package org.edupoll.market.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChatMessage {
	private int id;
	private String chatRoomId;
	private String talkerId;
	private String content;
	private Date sentAt;
	private Date checkedAt;
}
