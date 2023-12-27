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
	private String grant_type;
	@SerializedName("token_type")
	private String client_id;
	@SerializedName("refresh_token")
	private String client_secret;
	@SerializedName("expires_in")
	private int expiresIn;
	private String scope;
	@SerializedName("refresh_token_expires_in")
	private int refreshTokenExpiresIn;
}
