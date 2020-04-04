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
public class Customer {
	private Integer customerNum;
	private String customerName;
	private String customerEmail;
	private Short customerDiscount;
	private Integer sumOfAllPurchases;
	private List<String> customerPhoneNumbers;
}
