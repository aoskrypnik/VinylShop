package com.vinyl.service;

import com.vinyl.exception.LoginExistException;
import com.vinyl.model.UserCredentials;

public interface UserService {

	void save(UserCredentials userCredsDetails) throws LoginExistException;

	UserCredentials findByLogin(String login);

	UserCredentials prepareForSaving(UserCredentials userCredsDetails);

	Integer findSalesmanTabNumByLogin(String login);

	String getCurrentUserLogin();
}
