package com.john.jpmovies.service;

import java.util.List;

import com.john.jpmovies.model.Movie;

public interface IMovieService {
	Movie readById(Long id);
	Movie readByName(String name);
	List<Movie> read();
	boolean createMovie(Movie movie);
	boolean deleteMovie(Long id);
	boolean updateMovie(Movie movie);
}
