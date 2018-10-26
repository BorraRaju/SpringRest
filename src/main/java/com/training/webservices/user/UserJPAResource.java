package com.training.webservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAResource {

	@Autowired
	private UserRepository service;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		
		return service.findAll();
	}
	
	@GetMapping("/jpa/users/id/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = service.findById(id);
		if(!user.isPresent())
			throw new UserNotFounDException("id - "+ id);
		
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		// /user/{id} savedUser.getId()
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/id/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	
	@DeleteMapping("/jpa/users/id/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}
	
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllUsersPosts(@PathVariable int id){
		
		Optional<User> userOptional = service.findById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFounDException("-id :" +id);
		}
		
		return userOptional.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		
		Optional<User> userOptional = service.findById(id);
		if(!userOptional.isPresent()) {
			throw new UserNotFounDException("-id :" +id);
		}
		
		User user = userOptional.get();
		post.setUser(user);
		
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/id/{id}")
				.buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
}
