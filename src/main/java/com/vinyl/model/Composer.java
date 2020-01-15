package com.vinyl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Composer {

	private String name;
	private String country;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date activityStart;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date activityEnd;

}
