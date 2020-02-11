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
public class UsrDto {
	private String login;
	private String oldPassword;
	private String newPassword;
	private String newPassword2;
}
