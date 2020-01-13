package com.vinyl.service.impl;

import com.vinyl.dao.UserDao;
import com.vinyl.exception.LoginExistException;
import com.vinyl.model.UserCredentials;
import com.vinyl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private static final String DIRECTOR_LOGIN = "director";
	private static final String BASIC_PASSWORD = "123";
	private static final String LOGIN_EXIST_EXCEPTION_MESSAGE = "There is an account with that login: ";
	private static final String ADDING_DIRECTOR_LOG_MESSAGE = "Director with basic password added successfully";
	private static final String ADDING_EXTRA_DIRECTOR_EXCEPTION_MESSAGE = "Trying to add one more director";

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

	@PostConstruct
	public void addDirectorWithBasicPassword() {
		UserCredentials user = new UserCredentials(DIRECTOR_LOGIN, passwordEncoder.encode(BASIC_PASSWORD), true);
		try {
			save(user);
			log.info(ADDING_DIRECTOR_LOG_MESSAGE);
		} catch (LoginExistException e) {
			log.info(ADDING_EXTRA_DIRECTOR_EXCEPTION_MESSAGE);
		}
	}

}
