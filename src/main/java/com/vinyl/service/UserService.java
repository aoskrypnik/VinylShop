package com.vinyl.service;

import com.vinyl.exception.LoginExistException;
import com.vinyl.model.UserCredentials;

public interface UserService {

	void save (UserCredentials userCredentials) throws LoginExistException;

	UserCredentials findByLogin (String login);

}
