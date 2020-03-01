package com.vinyl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Artist {
	private String artistAlias;
	private Boolean isArtistActive;
	private String artistCountryCode;
	private String artistName;
	private Date artistBirthDate;
	private Date artistDeathDate;
	private List<String> currentBandAliases;
	private List<String> previousBandAliases;
	private List<String> trackCatalogNums;
	private List<String> featuringTrackCatalogNums;
}
