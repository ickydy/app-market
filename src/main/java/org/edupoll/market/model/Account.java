package org.edupoll.market.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Account {
	private String id;
	private String nickname;
	private String platform;
	private String accessToken;
	private String profileImageUrl;
	private String address;
	private Double latitude;
	private Double longitude;
}
