package com.john.jpmovies.model;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="movie")
public class Movie {

	@Id
	@Column(name="id_movie")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idMovie;
	
	@Column(name="name")
	private String name;
	
	@Column(name="director")
	private String director;
	
	@Column(name="release_date")
	private Date releaseDate;
	
	@Column(name="image")
	private String image;
	
	//hija
	@ManyToOne(fetch=FetchType.EAGER) //cuando se consule una pelicula, con EAGER forzamos a que traiga sus Movies
	@JoinColumn(name="id_genre") //nombre del campo "foreign key" de genre
	@JsonIgnore //si lo quitamos, el select trae el genre de esta pelicula dentro del json de cada fila devuelta
	private Genre genre;

	public Movie(Long idMovie, String name, String director, Date releaseDate, String image, Genre genre) {
		super();
		this.idMovie = idMovie;
		this.name = name;
		this.director = director;
		this.releaseDate = releaseDate;
		this.image = image;
		this.genre = genre;
	}

	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(Long idMovie) {
		this.idMovie = idMovie;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	
}
