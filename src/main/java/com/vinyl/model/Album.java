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
	private String albumCatalogNum;
	private Integer releaseYear;
	private List<String> albumGenres;
	private String albumName;
	private Boolean variousArtists;
	private List<String> trackCatalogNums;
	private List<String> releaseBarcodes;
}
