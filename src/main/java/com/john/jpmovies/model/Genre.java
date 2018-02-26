package com.john.jpmovies.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="genre")
public class Genre {

	@Id
	@Column(name="id_genre")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idGenre;
	
	@Column(name="name")
	private String name;
	
	//padre
	@OneToMany(mappedBy="genre") //mappedBy:campo de la otra clase que hace referencia a esta (private Genre genre) y se le da el nombre descrito arriba [@Table(name="genre")]
	@JsonIgnore //ESTO EVITA QUE CUANDO HACEMOS UN SELECT A ESTA CLASE (ORM) SE CAIGA SI NO TIENE "MOVIES" ASOCIADAS
	private Set<Movie> movies;

	public Genre(Long idGenre, String name, Set<Movie> movies) {
		super();
		this.idGenre = idGenre;
		this.name = name;
		this.movies = movies;
	}

	public Genre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdGenre() {
		return idGenre;
	}

	public void setIdGenre(Long idGenre) {
		this.idGenre = idGenre;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	
	
}
