package com.vinyl.controller.authorization;

import com.vinyl.controller.authorization.response.ApiResponse;
import com.vinyl.controller.authorization.response.JwtAuthenticationResponse;
import com.vinyl.exception.LoginExistException;
import com.vinyl.model.UserCredentials;
import com.vinyl.security.JwtTokenProvider;
import com.vinyl.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final String LOGIN_IS_ALREADY_TAKEN = "Login is already taken!";
	private static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";

	@Resource
	private AuthenticationManager authenticationManager;
	@Resource
	private UserService userService;
	@Resource
	private JwtTokenProvider tokenProvider;

	@PostMapping("/sign-in")
	public ResponseEntity<?> authenticateUser(@RequestBody UserCredentials credentials) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						credentials.getLogin(),
						credentials.getPassword()
				)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> registerUser(@RequestBody UserCredentials credentials) {
		userService.prepareForSaving(credentials);

		try {
			userService.save(credentials);
		} catch (LoginExistException e) {
			return new ResponseEntity(new ApiResponse(false, LOGIN_IS_ALREADY_TAKEN), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new ApiResponse(true, USER_REGISTERED_SUCCESSFULLY), HttpStatus.CREATED);
	}
}
