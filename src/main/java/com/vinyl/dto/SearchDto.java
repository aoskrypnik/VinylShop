package com.vinyl.dto;

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
public class SearchDto {
	private List<String> whereParams;
	private List<String> likeParams;
	private List<String> betweenParams;
	private List<String> joins;
	private String sorting;
	private String order;
	private Integer limit;
	private Integer offset;
}
