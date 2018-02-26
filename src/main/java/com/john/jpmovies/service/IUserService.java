package com.john.jpmovies.service;

import java.util.List;

import com.john.jpmovies.model.User;

public interface IUserService {
	User readById(Long id);
	User readByUsername(String username);
	List<User> read();
	boolean createUser(User user);
	boolean deleteUser(Long id);
	boolean updateUser(User user);
}
