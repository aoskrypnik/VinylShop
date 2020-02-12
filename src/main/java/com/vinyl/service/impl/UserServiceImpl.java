package com.vinyl.service.impl;

import com.vinyl.dao.UserDao;
import com.vinyl.dto.SalesmanUsrDto;
import com.vinyl.dto.UsrDto;
import com.vinyl.exception.LoginExistException;
import com.vinyl.model.UserCredentials;
import com.vinyl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

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
	public void saveSalesmanCreds(SalesmanUsrDto salesmanUsrDto) {
		salesmanUsrDto.setPwd(passwordEncoder.encode(salesmanUsrDto.getPwd()));
		userDao.saveSalesmanCreds(salesmanUsrDto);
	}

	@Override
	public UserCredentials findByLogin(String login) {
		return userDao.findByLogin(login);
	}

	@Override
	public Integer findSalesmanTabNumByLogin(String login) {
		return userDao.findSalesmanTabNumByLogin(login);
	}

	@Override
	public String getCurrentlyLoggedInUserLogin() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public Boolean usrDtoCredsEqualUserCredFromDb(UsrDto usrDto, UserCredentials userCredentials) {
		if (isNull(userCredentials)) {
			return FALSE;
		}
		if (usrDto.getLogin().equals(userCredentials.getLogin())
				&& passwordEncoder.matches(usrDto.getOldPassword(), userCredentials.getPassword())) {
			return TRUE;
		}
		return FALSE;
	}

	@Override
	public void changePassword(UsrDto usrDto) {
		usrDto.setNewPassword(passwordEncoder.encode(usrDto.getNewPassword()));
		userDao.changePassword(usrDto);
	}

	@Override
	public boolean userLoginMatchesCurrentlyLoggedInUser(UserCredentials userCredentials) {
		return userCredentials.getLogin().equals(getCurrentlyLoggedInUserLogin());
	}

	@Override
	public String getCurrentUserAuthority(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.findFirst()
				.orElse(EMPTY);
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
