package com.john.jpmovies.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import com.john.jpmovies.model.User;
import com.john.jpmovies.service.UserService;
import com.john.jpmovies.util.CustomErrorType;

@Controller
@RequestMapping(value="/v1")
public class UserController {

	@Autowired
	private UserService _userService;
	
	private static final String USERS_IMAGES_URL = "images/users/";
	
	//GET
	@RequestMapping(value="/user", method=RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<List<User>> getUsers(@RequestParam(value="name", required=false) String username){
		List<User> users = new ArrayList<>();
		if(username == null) {
			users = _userService.read();
			if(users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		}
		else {
			User user = _userService.readByUsername(username);
			if(user == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			users.add(user);
			return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		}
	}
	
	//GET ID
	@RequestMapping(value="/user/{id}", method=RequestMethod.GET, headers="Accept=application/json")
	private ResponseEntity<User> getUser(@PathVariable(name="id") Long idUser){
		if(idUser == null || idUser <= 0){
			return new ResponseEntity(new CustomErrorType("Id user is required or value must be greater than 0."),HttpStatus.CONFLICT);
		}
		User user = _userService.readById(idUser);
		if(user == null) {
			return new ResponseEntity(new CustomErrorType("User doesn't exist."), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	//POST
	@RequestMapping(value="/user", method=RequestMethod.POST, headers="Accept=application/json")//para que acepte json
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder){
		if(user.getUsername().equals(null) || user.getUsername().isEmpty()) {
			//return new ResponseEntity(HttpStatus.NO_CONTENT);
			return new ResponseEntity(new CustomErrorType("username is required"), HttpStatus.CONFLICT);
		}
		
		if(_userService.readByUsername(user.getUsername())!= null){
			System.out.println("ya existe este username");
			//return new ResponseEntity(HttpStatus.NO_CONTENT);
			return new ResponseEntity(new CustomErrorType("this username already exist."), HttpStatus.NO_CONTENT);
		}
		
		_userService.createUser(user);
		User u = _userService.readByUsername(user.getUsername());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponentsBuilder.path("/v1/user/{id}").buildAndExpand(u.getIdUser()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	//PATCH
	@RequestMapping(value="/user/{id}", method=RequestMethod.PATCH, headers="Accept=application/json")
	private ResponseEntity<?> updateUser(@PathVariable("id") Long idUser, @RequestBody User user){
		if(idUser == null || idUser <= 0){
			return new ResponseEntity(new CustomErrorType("Id user is required or value must be greater than 0."),HttpStatus.CONFLICT);
		}
		
		User currentUser = _userService.readById(idUser);
		if(user == null) {
			return new ResponseEntity(new CustomErrorType("User doesn't exist."), HttpStatus.CONFLICT);
		}
		
		currentUser.setUsername(user.getUsername());
		currentUser.setPassword(user.getPassword());
		currentUser.setAvatar(user.getAvatar());
		currentUser.setActive(user.isActive());
		_userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	//DELETE
	@RequestMapping(value="/user/{id}",method=RequestMethod.DELETE, headers="Accept=application/json")
	private ResponseEntity<?> deleteUser(@PathVariable("id") Long idUser){
		if(idUser == null || idUser <= 0){
			return new ResponseEntity(new CustomErrorType("Id user is required or value must be greater than 0."),HttpStatus.CONFLICT);
		}
		
		User user = _userService.readById(idUser);
		if(user == null) {
			return new ResponseEntity(new CustomErrorType("User doesn't exist."), HttpStatus.CONFLICT);
		}
		
		_userService.deleteUser(idUser);
		
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	//UPLOAD AVATAR IMAGE
	@RequestMapping(value="/user/image", method=RequestMethod.POST, headers=("content-type=multipart/form-data"))
	private ResponseEntity<byte[]> uploadImage(@RequestParam("id") Long idUser, @RequestParam("file") MultipartFile multipartfile){
		if(idUser == null || idUser <= 0){
			return new ResponseEntity(new CustomErrorType("Id user is required or value must be greater than 0."),HttpStatus.CONFLICT);
		}
		
		User user = _userService.readById(idUser);
		if(user == null) {
			return new ResponseEntity(new CustomErrorType("User doesn't exist."), HttpStatus.CONFLICT);
		}
		
		if(multipartfile.isEmpty()) {
			return new ResponseEntity(new CustomErrorType("image is required"), HttpStatus.CONFLICT);
		}
		
		System.out.println(multipartfile.getContentType());
		System.out.println(user.getAvatar());
		//si el usuario ya tiene una imagen hay que eliminarla
		if(user.getAvatar() != null){ // || !user.getAvatar().isEmpty()
			String fileName = user.getAvatar();
			//Path y Paths son de: java.nio.file	
			Path path = Paths.get(fileName);
			//File es de: java.io
			File f = path.toFile();
			//si existe la imagen en nuestro proyecto o carpeta de imagenes del proyecto, lo borra
			if(f.exists()){
				f.delete();
			}
		}
			
			 //siempre que manejemos entradas y salidas de datos al servidor, utilicemos try catch
		try{
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
			String dateName = dateFormat.format(date);
			 	
			String newFileName = String.valueOf(idUser) + "-pictureUser-" + dateName + "." + multipartfile.getContentType().split("/")[1];
			user.setAvatar(USERS_IMAGES_URL + newFileName);
			  
			byte[] bytes = multipartfile.getBytes(); 
			Path newPath = Paths.get(USERS_IMAGES_URL + newFileName); //obtener una ruta en forma de objeto
			//java.nio.Files
			Files.write(newPath,bytes); //escribe el archivo en bytes en nuestra carpeta
			_userService.updateUser(user);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
		}catch(Exception e){	  
			e.printStackTrace();
			return new ResponseEntity(new CustomErrorType("error durante la carga: "+ multipartfile.getOriginalFilename()), HttpStatus.CONFLICT);
		}	 
	}
	
	//GET IMAGE
	@RequestMapping(value="/user/{id}/image", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getUserImage(@PathVariable("id") Long idUser){
		if(idUser == null || idUser <= 0){
			return new ResponseEntity(new CustomErrorType("Id user is required or value must be greater than 0."),HttpStatus.CONFLICT);
		}
		
		User user = _userService.readById(idUser);
		if(user == null) {
			return new ResponseEntity(new CustomErrorType("User doesn't exist."), HttpStatus.CONFLICT);
		}
		
		try {
			String filename = user.getAvatar();//direccion
			Path path = Paths.get(filename);
			File f = path.toFile();
			if(!f.exists()) {
				return new ResponseEntity(new CustomErrorType("Image not found."), HttpStatus.CONFLICT);
			}
			
			byte[] image = Files.readAllBytes(path);//lee todos los bytes a traves de la ruta del archivo y los pasa a un byte[]
			
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new CustomErrorType("Error al traer la imagen"), HttpStatus.CONFLICT);
		}
		
	}
		
	
	//DELETE IMAGE
	@RequestMapping(value="/user/{id}/image", method=RequestMethod.DELETE, headers="Accept=application/json")
	public ResponseEntity<?> deleteImage(@PathVariable("id") Long idUser){
		if(idUser == null || idUser <= 0){
			return new ResponseEntity(new CustomErrorType("Id user is required or value must be greater than 0."),HttpStatus.CONFLICT);
		}
		
		User user = _userService.readById(idUser);
		if(user == null) {
			return new ResponseEntity(new CustomErrorType("User doesn't exist."), HttpStatus.CONFLICT);
		}
		
		if(user.getAvatar() == null){ // || !user.getAvatar().isEmpty()
			return new ResponseEntity(new CustomErrorType("User doesn't have image assigned."), HttpStatus.CONFLICT);
		}
		
		String fileName = user.getAvatar();
		Path path = Paths.get(fileName);
		File file = path.toFile();
		if(file.exists()) {
			file.delete();
		}
		
		user.setAvatar(null);//despues cambiar a ""
		_userService.updateUser(user);
		return new ResponseEntity<User>(user,HttpStatus.NO_CONTENT);
		
	}
	
	
	
	
}
