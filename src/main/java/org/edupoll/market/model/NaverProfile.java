package org.edupoll.market.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NaverProfile {
	private String id;
	private String name;
	@SerializedName("profile_image")
	private String profileImage;
}
