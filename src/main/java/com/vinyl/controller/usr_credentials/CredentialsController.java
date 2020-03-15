package com.vinyl.controller.usr_credentials;

import com.vinyl.controller.authorization.response.ApiResponse;
import com.vinyl.dto.UsrDto;
import com.vinyl.model.UserCredentials;
import com.vinyl.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@RestController("/credentials")
public class CredentialsController {

	private static final String LOGIN_OR_PASSWORD_NOT_VALID_MESSAGE = "Login or password not valid";
	private static final String PASSWORDS_ARE_DIFFERENT_MESSAGE = "Entered passwords are different";
	private static final String PASSWORD_SUCCESSFULLY_UPDATED_MESSAGE = "Password successfully updated";

	@Resource
	private UserService userService;

	@PutMapping
	public ResponseEntity<?> changeUsrPassword(@RequestBody UsrDto usrDto) {
		UserCredentials userCredentials = userService.findByLogin(usrDto.getLogin());
		if (userService.usrDtoCredsEqualUserCredFromDb(usrDto, userCredentials) &&
				userService.userLoginMatchesCurrentlyLoggedInUser(userCredentials)) {
			String enteredPassword = usrDto.getPassword();
			String enteredPassword2 = usrDto.getPassword2();
			if (isFalse(enteredPassword.equals(enteredPassword2))) {
				return new ResponseEntity<>(new ApiResponse(false, PASSWORDS_ARE_DIFFERENT_MESSAGE), HttpStatus.CONFLICT);
			}
			userService.changePassword(usrDto);
			return new ResponseEntity<>(new ApiResponse(true, PASSWORD_SUCCESSFULLY_UPDATED_MESSAGE), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse(false, LOGIN_OR_PASSWORD_NOT_VALID_MESSAGE), HttpStatus.BAD_REQUEST);
	}
}
