package com.john.jpmovies.dao;

import java.util.List;

import com.john.jpmovies.model.Movie;;

public interface IMovieDAO {
	Movie readById(Long id);
	Movie readByName(String name);
	List<Movie> read();
	boolean createMovie(Movie movie);
	boolean deleteMovie(Long id);
	boolean updateMovie(Movie movie);
}
