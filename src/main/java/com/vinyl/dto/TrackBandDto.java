package com.vinyl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackBandDto {
	private String trackCatalogNum;
	private String bandAlias;
	private Boolean isFeaturing;
}
