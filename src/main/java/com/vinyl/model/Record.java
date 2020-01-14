package com.vinyl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Record {
	private String barcode;
	private String releaseBarcode;
	private Integer checkNum;
	private String supplierEdrpou;
	private Date purchaseDate;
	private Integer purchasePrice;
	private Integer sellPrice;
	private Boolean available;
	private String state;
	private Date stateCheckDate;
}
