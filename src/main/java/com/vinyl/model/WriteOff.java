package com.vinyl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WriteOff {
	private Integer writeOffNum;
	private String offRecordBarcode;
	private Integer salesmanNum;
	private Date writeOffDate;
	private Integer fee;
	private String reason;
}
