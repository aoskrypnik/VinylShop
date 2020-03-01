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
	private List<String> wheres;
	private List<String> likes;
	private List<String> betweens;
	private List<String> joins;
	private String sort;
	private String order;
	private Integer limit;
	private Integer offset;
}
