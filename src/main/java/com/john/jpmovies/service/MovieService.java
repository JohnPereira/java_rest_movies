package com.john.jpmovies.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.jpmovies.dao.IMovieDAO;
import com.john.jpmovies.model.Movie;

@Service("movieService")
@Transactional
public class MovieService implements IMovieService {

	@Autowired
	private IMovieDAO _movieDao;
	
	public Movie readById(Long id) {
		// TODO Auto-generated method stub
		return _movieDao.readById(id);
	}

	public Movie readByName(String name) {
		// TODO Auto-generated method stub
		return _movieDao.readByName(name);
	}

	public List<Movie> read() {
		// TODO Auto-generated method stub
		return _movieDao.read();
	}

	public boolean createMovie(Movie movie) {
		// TODO Auto-generated method stub
		return _movieDao.createMovie(movie);
	}

	public boolean deleteMovie(Long id) {
		// TODO Auto-generated method stub
		return _movieDao.deleteMovie(id);
	}

	public boolean updateMovie(Movie movie) {
		// TODO Auto-generated method stub
		return _movieDao.updateMovie(movie);
	}

}
