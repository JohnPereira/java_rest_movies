package com.john.jpmovies.dao;

import java.util.List;

import com.john.jpmovies.model.User;

public interface IUserDAO {
	User readById(Long id);
	User readByUsername(String username);
	List<User> read();
	boolean createUser(User user);
	boolean deleteUser(Long id);
	boolean updateUser(User user);
}
