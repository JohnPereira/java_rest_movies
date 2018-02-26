package com.john.jpmovies.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.john.jpmovies.model.User;

@Repository
@Transactional
public class UserDAO extends AbstractSession implements IUserDAO {

	public User readById(Long id) {
		try {
			return (User)getSession().get(User.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public User readByUsername(String username) {
		try {
			return (User)getSession().createQuery("from User where username = :username")
					.setParameter("username", username).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<User> read() {
		try {
			return getSession().createQuery("from User").list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean createUser(User user) {
		try {
			getSession().persist(user);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteUser(Long id) {
		boolean respuesta = false;
		try {
			User user = (User) this.readById(id);
			if (user!=null) {
				getSession().delete(user);
				respuesta = true;
			}else {
				respuesta = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			respuesta = false;
		}
		return respuesta;
	}

	public boolean updateUser(User user) {
		try {
			getSession().update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
