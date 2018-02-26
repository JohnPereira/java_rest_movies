package com.john.jpmovies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.john.jpmovies.service.GenreService;
import com.john.jpmovies.model.Genre;
import java.util.*;
@Controller
@RequestMapping(value="/v1")
public class GenreController {

	@Autowired
	private GenreService _genreService;
	
	//GET
	@RequestMapping(value="/genre", method=RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<List<Genre>> getGenres(){
		List<Genre> genreList = new ArrayList<>();
		genreList = _genreService.read();
		//return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<List<Genre>>(genreList,HttpStatus.OK);
	}
}
