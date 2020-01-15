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
public class Album {
	private String catalogNum;
	private Integer releaseYear;
	private String genre;
	private String name;
	private Boolean variousArtists;
	private List<Track> tracks;
}
