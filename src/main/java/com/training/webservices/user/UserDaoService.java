package com.training.webservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<User>();
	
	private static int userCount=105;
	
	static {
		users.add(new User(101,"Adam",new Date()));
		users.add(new User(102,"Aejaz",new Date()));
		users.add(new User(103,"Tahir",new Date()));
		users.add(new User(104,"Mani",new Date()));
		users.add(new User(105,"Roshan",new Date()));
	}
	
	
	//retrive the users
	public List<User> findAll(){
		return users;
	}
	
	
	//Save user
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++userCount);
		}
		users.add(user);
		return user;
	}
	
	//find single user
	public User findOne(int id) {
		for( User user:users) {
			if(user.getId()==id) {
				return user;
			}
		}
		return null;
	}
	
	//delete user
		public User delete(int id) {
			Iterator<User> iterator = users.iterator();
			while( iterator.hasNext()) {
				User user = iterator.next();
				if(user.getId()==id) {
					iterator.remove();
					return user;
				}
			}
			return null;
		}
		
		
}
