package com.john.jpmovies.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.john.jpmovies.model.Genre;
import com.john.jpmovies.model.Movie;
import com.john.jpmovies.model.User;

@Repository
@Transactional
public class GenreDAO extends AbstractSession implements IGenreDAO {

	@Override
	public Genre readById(Long id) {
		try {
			return (Genre)getSession().get(Genre.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Genre readByName(String name) {
		try {
			return (Genre)getSession().createQuery("from Genre where name = :name")
					.setParameter("name", name).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Genre> read() {
		try {
			return getSession().createQuery("from Genre").list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean createGenre(Genre genre) {
		try {
			getSession().persist(genre);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteGenre(Long id) {
		boolean respuesta = false;
		try {
			Genre genre = (Genre) this.readById(id);
			if (genre!=null) {
				getSession().delete(genre);
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
	

	@Override
	public boolean updateGenre(Genre genre) {
		try {
			getSession().update(genre);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
