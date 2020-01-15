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
public class Release {
	private String barcode;
	private String albumCatalogNum;
	private String countryCode;
	private Date releaseDate;
	private Integer recordSize;
	private Integer recordSpeed;
	private Integer copiesCount;
	private Boolean isRepress;
	private String label;
	private List<Record> records;
}
