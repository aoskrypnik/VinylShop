package com.vinyl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Track {
	private String trackCatalogNum;
	private String trackName;
	private Integer duration;
	private List<String> composerIds;
	private List<String> albumIds;
	private List<String> artistIds;
	private List<String> bandIds;
	private List<String> featuringArtistIds;
	private List<String> featuringBandIds;
	private List<String> languages;
}
