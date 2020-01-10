package com.vinyl.service.impl;

import com.vinyl.dao.UserDao;
import com.vinyl.model.UserCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		UserCredentials applicationUser = userDao.findByLogin(login);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(login);
		}
		return new User(applicationUser.getLogin(), applicationUser.getPassword(), emptyList());
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
