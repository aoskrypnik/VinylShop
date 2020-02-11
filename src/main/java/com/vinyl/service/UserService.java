package com.vinyl.service;

import com.vinyl.dto.UsrDto;
import com.vinyl.exception.LoginExistException;
import com.vinyl.model.UserCredentials;
import org.springframework.security.core.Authentication;

public interface UserService {

	void save(UserCredentials userCredsDetails) throws LoginExistException;

	UserCredentials findByLogin(String login);

	UserCredentials prepareForSaving(UserCredentials userCredsDetails);

	Integer findSalesmanTabNumByLogin(String login);

	String getCurrentlyLoggedInUserLogin();

	Boolean usrDtoCredsEqualUserCredFromDb(UsrDto usrDto, UserCredentials userCredentials);

	void changePassword(UsrDto usrDto);

	boolean userLoginMatchesCurrentlyLoggedInUser(UserCredentials userCredentials);

	String getCurrentUserAuthoritity(Authentication authentication);
}
