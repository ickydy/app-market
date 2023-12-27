package org.edupoll.market.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NaverUserInfo {
	private long id;
	@SerializedName("properties")
	private KakaoProfile profile;
}
