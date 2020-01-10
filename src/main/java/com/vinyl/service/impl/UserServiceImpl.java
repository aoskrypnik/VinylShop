package com.vinyl.service.impl;

import com.vinyl.dao.UserDao;
import com.vinyl.exception.LoginExistException;
import com.vinyl.model.UserCredentials;
import org.springframework.stereotype.Service;
import com.vinyl.service.UserService;

import javax.annotation.Resource;

import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService {

	private static final String LOGIN_EXIST_EXCEPTION_MESSAGE = "There is an account with that login: ";

	@Resource
	private UserDao userDao;

	@Override
	public void save(UserCredentials userCredentials) throws LoginExistException {
		if (loginExist(userCredentials.getLogin())) {
			throw new LoginExistException(LOGIN_EXIST_EXCEPTION_MESSAGE);
		}
		userDao.save(userCredentials);
	}

	@Override
	public UserCredentials findByLogin(String login) {
		return userDao.findByLogin(login);
	}

	private boolean loginExist(String login) {
		UserCredentials credentials = findByLogin(login);
		return nonNull(credentials);
	}

}
