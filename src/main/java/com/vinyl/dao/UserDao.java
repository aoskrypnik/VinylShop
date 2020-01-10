package com.vinyl.dao;

import com.vinyl.model.UserCredentials;

public interface UserDao {

	void save (UserCredentials userCredentials);

	UserCredentials findByLogin (String login);

}
