package com.vinyl.dao.impl;

import com.vinyl.dao.UserDao;
import com.vinyl.model.UserCredentials;
import com.vinyl.utils.QuerySupplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Slf4j
public class UserDaoImpl implements UserDao, RowMapper<UserCredentials> {

	private static final String EMPTY_RESULT_DATA_EXCEPTION_MESSAGE = "No user found with that login: ";

	@Value("${sql.create.user.query.path}")
	private String createUserQueryPath;
	@Value("${sql.find.user.credentials.by.login.query.path}")
	private String findByLoginQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public void save(UserCredentials credentials) {
		String createUserQuery = QuerySupplier.getQuery(createUserQueryPath);

		String login = credentials.getLogin();
		String password = credentials.getPassword();

		jdbcTemplate.update(createUserQuery, login, password);
	}

	@Override
	public UserCredentials findByLogin(String login) {
		String findByLoginQuery = QuerySupplier.getQuery(findByLoginQueryPath);
		UserCredentials foundCredentials = null;
		try {
			foundCredentials = jdbcTemplate.queryForObject(findByLoginQuery, new Object[]{login}, this);
		} catch (EmptyResultDataAccessException exception) {
			log.debug(EMPTY_RESULT_DATA_EXCEPTION_MESSAGE + login);
		}
		return foundCredentials;
	}

	@Override
	public UserCredentials mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		UserCredentials credentials = new UserCredentials();
		credentials.setLogin(resultSet.getString("login"));
		credentials.setPassword(resultSet.getString("password"));
		credentials.setDirector(resultSet.getBoolean("isDirector"));
		return credentials;
	}

}

