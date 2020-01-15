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

	private String catalogNum;
	private String name;
	private Integer duration;
	private List<Composer> composers;
	private List<Artist> artists;
	private List<Band> bands;
	private List<Artist> featuring_artists;
	private List<Band> featuring_bands;

}
