package com.vinyl.controller.authorization.response;

public class JwtAuthenticationResponse {
	private String accessToken;
	private String tokenType;
	private String authority;

	public JwtAuthenticationResponse(String accessToken, String tokenType, String authority) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.authority = authority;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
