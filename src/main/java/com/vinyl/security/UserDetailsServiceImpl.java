package com.vinyl.security;

import com.vinyl.model.UserCredentials;
import com.vinyl.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Objects.isNull;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		UserCredentials foundCreds = userService.findByLogin(login);
		if (isNull(foundCreds)) {
			throw new UsernameNotFoundException(login);
		}
		return new UserCredsDetails(foundCreds.getLogin(), foundCreds.getPassword(), foundCreds.isDirector());
	}
}
