package com.john.jpmovies.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.john.jpmovies.model.Movie;
import com.john.jpmovies.model.User;

@Repository
@Transactional
public class MovieDAO extends AbstractSession implements IMovieDAO {

	@Override
	public Movie readById(Long id) {
		try {
			return (Movie)getSession().get(Movie.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Movie readByName(String name) {
		try {
			return (Movie)getSession().createQuery("from Movie where name = :name")
					.setParameter("name", name).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Movie> read() {
		try {
			return getSession().createQuery("from Movie").list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean createMovie(Movie movie) {
		try {
			getSession().persist(movie);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteMovie(Long id) {
		boolean respuesta = false;
		try {
			Movie movie = (Movie) this.readById(id);
			if (movie!=null) {
				getSession().delete(movie);
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
	public boolean updateMovie(Movie movie) {
		try {
			getSession().update(movie);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
