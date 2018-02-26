package com.john.jpmovies.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.jpmovies.dao.IGenreDAO;
import com.john.jpmovies.model.Genre;

@Service("genreService")
@Transactional
public class GenreService implements IGenreService {

	
	@Autowired
	private IGenreDAO _genreDao;
	
	public Genre readById(Long id) {
		// TODO Auto-generated method stub
		return _genreDao.readById(id);
	}

	public Genre readByName(String name) {
		// TODO Auto-generated method stub
		return _genreDao.readByName(name);
	}

	public List<Genre> read() {
		// TODO Auto-generated method stub
		return _genreDao.read();
	}

	public boolean createGenre(Genre genre) {
		// TODO Auto-generated method stub
		return _genreDao.createGenre(genre);
	}

	public boolean deleteGenre(Long id) {
		// TODO Auto-generated method stub
		return _genreDao.deleteGenre(id);
	}

	public boolean updateGenre(Genre genre) {
		// TODO Auto-generated method stub
		return _genreDao.updateGenre(genre);
	}

}
