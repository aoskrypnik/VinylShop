package com.vinyl.controller;

import com.vinyl.exception.LoginExistException;
import com.vinyl.model.UserCredentials;
import com.vinyl.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@RestController
public class AuthorizationController {

	private static final String SIGN_UP_PAGE = "signup";
	private static final String SIGN_IN_PAGE = "signin";

	private static final String REDIRECT_SIGN_IN = "redirect:/signin";
	private static final String REDIRECT_MAIN = "redirect:/";

	@Resource
	private UserService userService;
	@Resource
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	//Just for test, will be deleted
	@GetMapping("/")
	public UserCredentials keks() {
		return new UserCredentials("keks", "psw");
	}

	@GetMapping("/sign-up")
	public String showSignUpForm() {
		return SIGN_UP_PAGE;
	}

	@GetMapping("/sign-in")
	public String showSignInForm() {
		return SIGN_IN_PAGE;
	}

	@PostMapping("/sign-up")
	public String executeUserSignUp(@RequestBody UserCredentials userCredentials) {
		encodeUserPassword(userCredentials);

		try {
			userService.save(userCredentials);
		} catch (LoginExistException e) {
			return SIGN_UP_PAGE;
		}

		return REDIRECT_SIGN_IN;
	}

	@PostMapping("/sign-in")
	public String executeUserSignIn(@RequestBody UserCredentials enteredUserCredentials) {
		UserCredentials foundUserCredentials = findUserCredentialsByLogin(enteredUserCredentials);
		if (isNull(foundUserCredentials) || passwordsAreDifferent(enteredUserCredentials, foundUserCredentials)) {
			return SIGN_IN_PAGE;
		}
		return REDIRECT_MAIN;
	}

	private UserCredentials findUserCredentialsByLogin(UserCredentials enteredUserCredentials) {
		return userService.findByLogin(enteredUserCredentials.getLogin());
	}

	private boolean passwordsAreDifferent(UserCredentials enteredCreds, UserCredentials foundCreds) {
		return isFalse(enteredCreds.getPassword().equals(foundCreds.getPassword()));
	}

	private void encodeUserPassword(UserCredentials userCredentials) {
		String encodedPassword = bCryptPasswordEncoder.encode(userCredentials.getPassword());
		userCredentials.setPassword(encodedPassword);
	}

}
