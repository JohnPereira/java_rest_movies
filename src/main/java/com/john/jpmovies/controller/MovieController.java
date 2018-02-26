package com.john.jpmovies.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;
import com.john.jpmovies.model.Movie;
import com.john.jpmovies.service.MovieService;

@Controller
@RequestMapping(value="/v1")
public class MovieController {

	@Autowired
	private MovieService _movieService;
	
	//GET
	@RequestMapping(value="/movie", method=RequestMethod.GET,headers="Accept=application/json")
	private ResponseEntity<List<Movie>> getMovies(@RequestParam(value="name", required=false) String name){
		List<Movie> movieList = new ArrayList<>();
		if(name == null || name.isEmpty()) {
			movieList = _movieService.read();
			return new ResponseEntity<List<Movie>>(movieList,HttpStatus.OK);
		}
		else {
			Movie movie = _movieService.readByName(name);
			if(movie == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			movieList.add(movie);
			return new ResponseEntity<List<Movie>>(movieList,HttpStatus.OK);
		}
	}
}
