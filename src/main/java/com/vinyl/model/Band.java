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
public class Band {
	private String bandAlias;
	private Boolean isBandActive;
	private String bandCountryCode;
	private Date startYear;
	private Date endYear;
	private List<String> currentArtistAliases;
	private List<String> previousArtistAliases;
	private List<String> trackCatalogNums;
	private List<String> featuringTracksCatalogNums;
}
