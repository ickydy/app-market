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
public class ChatRoom {
	private String id;
	private int productId;
	private String sellerId;
	private String buyerId;
	private Date createdAt;
}
