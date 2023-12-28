package org.edupoll.market.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NaverOAuthToken {
	@SerializedName("access_token")
	private String accessToken;
	@SerializedName("token_type")
	private String tokenType;
	@SerializedName("refresh_token")
	private String refreshToken;
	@SerializedName("expires_in")
	private int expiresIn;
}
