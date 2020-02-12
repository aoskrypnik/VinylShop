package com.vinyl.controller.authorization;

import com.vinyl.controller.authorization.response.ApiResponse;
import com.vinyl.controller.authorization.response.JwtAuthenticationResponse;
import com.vinyl.dto.SalesmanUsrDto;
import com.vinyl.model.UserCredentials;
import com.vinyl.security.JwtTokenProvider;
import com.vinyl.service.SalesmanService;
import com.vinyl.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private static final String LOGIN_IS_ALREADY_TAKEN = "Login is already taken!";
	private static final String SALESMAN_DOESNT_EXIST = "Salesman with such tabNum doesn't exist: ";
	private static final String USER_ALREADY_REGISTERED = "User with such tubNum is already registered: ";
	private static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";
	private static final String USER_LOGGED_OUT_SUCCESSFULLY = "User logged out successfully";
	private static final String PASSWORDS_ARE_DIFFERENT_MESSAGE = "Passwords are different";
	private static final String JWT_TOKEN_TYPE_BEARER = "Bearer";

	@Resource
	private AuthenticationManager authenticationManager;
	@Resource
	private UserService userService;
	@Resource
	private SalesmanService salesmanService;
	@Resource
	private JwtTokenProvider tokenProvider;

	@PostMapping("/sign-in")
	@ResponseBody
	public ResponseEntity<?> authenticateUser(@RequestBody UserCredentials credentials) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						credentials.getLogin(),
						credentials.getPassword()
				)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String currentUSerAuthority = userService.getCurrentUserAuthority(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, JWT_TOKEN_TYPE_BEARER, currentUSerAuthority));
	}

	@PostMapping("/sign-up")
	@ResponseBody
	public ResponseEntity<?> registerSalesman(@RequestBody SalesmanUsrDto salesmanUsrDto) {
		int salesmanTabNum = salesmanUsrDto.getTabNum();

		if (nonNull(userService.findByLogin(salesmanUsrDto.getLogin()))) {
			return new ResponseEntity<>(new ApiResponse(false, LOGIN_IS_ALREADY_TAKEN), HttpStatus.CONFLICT);
		}
		if (isNull(salesmanService.getSalesmanByTabNum(salesmanTabNum))) {
			return new ResponseEntity<>(new ApiResponse(false,
					SALESMAN_DOESNT_EXIST + salesmanTabNum), HttpStatus.BAD_REQUEST);
		}
		if (nonNull(salesmanService.getSalesmanByTabNum(salesmanTabNum).getSalesmanLogin())) {
			return new ResponseEntity<>(new ApiResponse(false,
					USER_ALREADY_REGISTERED + salesmanTabNum), HttpStatus.CONFLICT);
		}
		if (isFalse(salesmanUsrDto.getPwd().equals(salesmanUsrDto.getPwd2()))) {
			return new ResponseEntity<>(new ApiResponse(false, PASSWORDS_ARE_DIFFERENT_MESSAGE), HttpStatus.CONFLICT);
		}
		userService.saveSalesmanCreds(salesmanUsrDto);
		return new ResponseEntity<>(new ApiResponse(true, USER_REGISTERED_SUCCESSFULLY), HttpStatus.CREATED);
	}

	@GetMapping(value = "/logout")
	@ResponseBody
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (nonNull(auth)) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return new ResponseEntity<>(new ApiResponse(true, USER_LOGGED_OUT_SUCCESSFULLY), HttpStatus.NO_CONTENT);
	}

}
