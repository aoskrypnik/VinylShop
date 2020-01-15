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
	private String alias;
	private Boolean isActive;
	private String countryCode;
	private Date startYear;
	private Date endYear;
	private List<String> artistAliases;
	private List<String> formerArtistAliases;
	private List<String> trackCatalogNums;
	private List<String> featuringTracksCatalogNums;
}
