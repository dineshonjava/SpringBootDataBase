/**
 * 
 */
package com.dineshonjava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dineshonjava.model.User;
import com.dineshonjava.service.UserService;

/**
 * @author Dinesh.Rajput
 *
 */
@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/")
    User home(User user) {
		user = userService.create(user);
        return user;
    }
	
	@RequestMapping("/users")
    List<User> findAllUsers() {
		List<User> users = userService.findAll();
        return users;
    }
}
