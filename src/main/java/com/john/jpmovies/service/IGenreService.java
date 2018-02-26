package com.john.jpmovies.service;

import java.util.List;

import com.john.jpmovies.model.Genre;

public interface IGenreService {
	Genre readById(Long id);
	Genre readByName(String name);
	List<Genre> read();
	boolean createGenre(Genre genre);
	boolean deleteGenre(Long id);
	boolean updateGenre(Genre genre);
}
