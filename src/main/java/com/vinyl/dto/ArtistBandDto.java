package com.vinyl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ArtistBandDto {
	private String participationArtistAlias;
	private String participationBandAlias;
	private Date joinDate;
	private Date exitDate;
}
