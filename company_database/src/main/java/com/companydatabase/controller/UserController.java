package com.companydatabase.controller;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.companydatabase.exception.ResourceNotFoundException;
import com.companydatabase.request.UserRequest;
import com.companydatabase.response.UserResponse;
import com.companydatabase.service.UserService;





@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Value("${value.from.file}")
	private String valueFromFile;
	
	
	@GetMapping("/value")
    public String getAllValue() {
        return valueFromFile;
    }
	   @GetMapping
	    public List<UserResponse> getAllUsers() {
		   logger.info("Getting all users");
	        return userService.getAllUsers();
	    }
	 
	   @GetMapping("/{userId}")
		public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) throws ResourceNotFoundException {
		   logger.info("Getting user by id: {}", userId);
		    Optional<UserResponse> optionalUser = userService.getUsersById(userId);
		    if (optionalUser.isPresent()) {
		        return ResponseEntity.ok(optionalUser.get());
		    } else {
		        return ResponseEntity.notFound().build();
		    }
		}
	   
		@PutMapping("/update/{userId}")
		public String updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
			logger.info("Updating user with id: {}", userId);
			return userService.updateUser(userId, userRequest);
		}
		
		@PostMapping("/post")
		public UserResponse addUser(@RequestBody UserRequest userRequest) throws ResourceNotFoundException {
			logger.info("Adding new user");
			return userService.createUser(userRequest);
		}
		
		@DeleteMapping("/delete/{userId}")
		public String deleteUser(@PathVariable Long userId) throws ResourceNotFoundException {
			logger.info("Deleting user with id: {}", userId);
			return userService.deleteUser(userId);
		}
		
		@GetMapping("/user/{companyId}")
		public ResponseEntity<List<UserResponse>> getUsersByCompanyId(@PathVariable Long companyId) throws ResourceNotFoundException {
			logger.info("Getting users by company id: {}", companyId);
			List<UserResponse> users = userService.getUsersByCompanyId(companyId);
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(users, HttpStatus.OK);
			}
		}
}
