package com.vinyl.service.impl;

import com.vinyl.dao.UserDao;
import com.vinyl.exception.LoginExistException;
import com.vinyl.model.UserCredentials;
import com.vinyl.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService {

	private static final String LOGIN_EXIST_EXCEPTION_MESSAGE = "There is an account with that login: ";

	@Resource
	private UserDao userDao;
	@Resource
	private PasswordEncoder passwordEncoder;

	@Override
	public void save(UserCredentials credentials) throws LoginExistException {
		if (nonNull(findByLogin(credentials.getLogin()))) {
			throw new LoginExistException(LOGIN_EXIST_EXCEPTION_MESSAGE);
		}
		userDao.save(credentials);
	}

	@Override
	public UserCredentials findByLogin(String login) {
		return userDao.findByLogin(login);
	}

	@Override
	public UserCredentials prepareForSaving(UserCredentials credentials) {
		credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
		credentials.setDirector(false);
		return credentials;
	}

}
