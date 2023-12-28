package org.edupoll.market.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdateAddress {
	private String address;
	private Double latitude;
	private Double longitude;
}
