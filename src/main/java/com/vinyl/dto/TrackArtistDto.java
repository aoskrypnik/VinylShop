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
public class TrackPerformerDto {
	private String trackCatalogNum;
	private String performerAlias;
	private Boolean isFeaturing;
	private Boolean isArtist;
}
