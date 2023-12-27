package org.edupoll.market.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
	private String id;
	private String nickname;
	private String platform;
	private String accessToken;
	private String profileImageUrl;
	private String address;
	private double latitude;
	private double longitude;
}
