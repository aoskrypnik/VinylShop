package com.vinyl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Check {
	private Integer checkNum;
	private Timestamp dateTime;
	private Integer salesmanTabNum;
	private Integer customerNum;
	private Short checkDiscount;
	private Integer overallSum;
	private Integer sumWithDiscount;
	private List<String> productBarcodes;

	public Check(Timestamp dateTime, Integer salesmanTabNum, Integer customerNum, List<String> productBarcodes) {
		this.dateTime = dateTime;
		this.salesmanTabNum = salesmanTabNum;
		this.customerNum = customerNum;
		this.productBarcodes = productBarcodes;
	}
}
