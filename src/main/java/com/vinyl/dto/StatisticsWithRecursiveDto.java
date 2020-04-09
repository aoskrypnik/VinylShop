package com.vinyl.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsWithRecursiveDto {
	private Integer checkNum;
	private Integer income;
	private Integer avgCheck;
	private Integer proceeds;
}