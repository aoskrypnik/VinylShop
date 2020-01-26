package com.vinyl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistBandMembershipDto {
	private String artistAlias;
	private Date startMembership;
	private Date endMembership;
}
