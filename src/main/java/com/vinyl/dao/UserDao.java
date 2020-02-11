package com.vinyl.dao;

import com.vinyl.dto.UsrDto;
import com.vinyl.model.UserCredentials;

public interface UserDao {

	void save(UserCredentials userCredsDetails);

	UserCredentials findByLogin(String login);

	Integer findSalesmanTabNumByLogin(String login);

	void changePassword(UsrDto usrDto);
}
