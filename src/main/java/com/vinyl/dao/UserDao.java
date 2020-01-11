package com.vinyl.dao;

import com.vinyl.model.UserCredentials;

public interface UserDao {

	void save (UserCredentials userCredsDetails);

	UserCredentials findByLogin (String login);

}
