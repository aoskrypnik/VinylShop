package com.vinyl.service;

import com.vinyl.dto.SalesmanUsrDto;
import com.vinyl.dto.UsrDto;
import com.vinyl.exception.LoginExistException;
import com.vinyl.model.UserCredentials;
import org.springframework.security.core.Authentication;

public interface UserService {

	void save(UserCredentials userCredsDetails) throws LoginExistException;

	void saveSalesmanCreds(SalesmanUsrDto salesmanUsrDto);

	UserCredentials findByLogin(String login);

	Integer findSalesmanTabNumByLogin(String login);

	String getCurrentlyLoggedInUserLogin();

	Boolean usrDtoCredsEqualUserCredFromDb(UsrDto usrDto, UserCredentials userCredentials);

	void changePassword(UsrDto usrDto);

	boolean userLoginMatchesCurrentlyLoggedInUser(UserCredentials userCredentials);

	String getCurrentUserAuthority(Authentication authentication);
}
