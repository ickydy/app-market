package org.edupoll.market.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdateProfile {
	private String nickname;
	private String profileImageUrl;
}
