package org.edupoll.market.model;

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
public class ChatRoomComplex {
	private ChatRoom chatRoom;
	private Product product;
	private int unchecked;
	private String recentMessage;
}
