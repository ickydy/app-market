package org.edupoll.market.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdateProfile {
	private String nickname;
	private MultipartFile profileImage;
}
