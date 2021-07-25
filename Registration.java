package com.saini.restcontroller;

import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saini.entity.UserEntity;
import com.saini.response.RegistrationResponse;
import com.saini.service.UserService;

@RestController
public class RegistrationController {

	@Autowired
	private UserService userservice;

	@GetMapping(value = "/getCountry", produces = { "application/json" })
	Map<Integer, String> getCountryName() {
		Map<Integer, String> countries = userservice.findCountries();
		return countries;
	}

	@GetMapping(value = "/getState/{countryKey}", produces = { "application/json", })
	Map<Integer, String> getStateName(@PathVariable("countryKey") Integer countryId) {
		Map<Integer, String> states = userservice.findStates(countryId);
		return states;
	}

	@GetMapping(value = "/getCity/{stateKey}", produces = { "application/json" })
	Map<Integer, String> getCityName(@PathVariable("stateKey") Integer stateId) {
		Map<Integer, String> cities = userservice.findCities(stateId);
		return cities;
	}

	@GetMapping(value = "/checkEmail/{email}")
	String isUniqueEmail(@PathVariable("email") String email) {

		boolean uniqueEmail = userservice.isUniqueEmail(email);
		if (uniqueEmail)
			return "Email is valid";

		return "DUPLICATE_EMAIl";
	}

	@PostMapping( value = "/getUserData", consumes = { "application/json" })
	ResponseEntity<RegistrationResponse> getUserData(@RequestBody UserEntity user) {
		String msg;
		
		ResponseEntity<RegistrationResponse> response;
		boolean saveUser = userservice.saveUser(user);
		if (saveUser) {
			
			RegistrationResponse rs=new RegistrationResponse();
			rs.setStatus(201);
			rs.setMsg("User Registration successfull");
			 response=new ResponseEntity<RegistrationResponse>(rs, HttpStatus.CREATED);
		}
		else
		{
		RegistrationResponse rs=new RegistrationResponse();
		rs.setStatus(400);
		rs.setMsg("User Registration Failure");
		response=new ResponseEntity<RegistrationResponse>(rs, HttpStatus.BAD_REQUEST);
		}
		
			
		return response;
		
	
	}

}
