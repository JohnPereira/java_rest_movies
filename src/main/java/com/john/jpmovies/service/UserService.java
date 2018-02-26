package com.john.jpmovies.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.jpmovies.dao.IUserDAO;
import com.john.jpmovies.model.User;

//capa de negocio
@Service("userService")
@Transactional
public class UserService implements IUserService {

	@Autowired
	private IUserDAO _userDao;
	
	public User readById(Long id) {
		// TODO Auto-generated method stub
		return _userDao.readById(id);
	}

	public User readByUsername(String username) {
		// TODO Auto-generated method stub
		return _userDao.readByUsername(username);
	}

	public List<User> read() {
		// TODO Auto-generated method stub
		return _userDao.read();
	}

	public boolean createUser(User user) {
		// TODO Auto-generated method stub
		return _userDao.createUser(user);
	}

	public boolean deleteUser(Long id) {
		// TODO Auto-generated method stub
		return _userDao.deleteUser(id);
	}

	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return _userDao.updateUser(user);
	}

}
