package com.gokyur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gokyur.entity.Users;
import com.gokyur.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/register")
	public String indexPage(Model theModel) {
		Users theUser = new Users();
		theModel.addAttribute("user",theUser);
		return "register";
	}
	
	@RequestMapping(value="/registerUser", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") Users theUser,Model theModel) {		
		userService.saveUser(theUser);
		System.out.println(theUser.toString()+ "is registered.");
		return "redirect:/";
		
	}
}
