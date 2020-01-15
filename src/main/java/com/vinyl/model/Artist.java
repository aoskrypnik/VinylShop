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
	private String alias;
	private Boolean isActive;
	private String countryCode;
	private String name;
	private Date birthDate;
	private Date deathDate;
	private List<Band> currentBands;
	private List<Band> previousBands;
	private List<Track> tracks;
	private List<Track> featuringTracks;
}
