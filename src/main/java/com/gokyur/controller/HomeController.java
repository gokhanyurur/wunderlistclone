package com.gokyur.controller;




import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.gokyur.entity.Notifications;
//import com.gokyur.service.ListService;
//import com.gokyur.service.RoleService;
import com.gokyur.service.NotificationService;
import com.gokyur.service.UserService;


@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
//	@Autowired
//	private ListService listService;
//	
//	@Autowired
//    private RoleService roleService;
	
	@Autowired
	private NotificationService notificationService;
	
	@RequestMapping(value = "/seeAllNotifications", method = RequestMethod.GET)
	public @ResponseBody  List<Notifications> seeAllNotifications(HttpServletRequest req, HttpServletResponse resp) {
		List<Notifications> userNotifications = userService.getUser(req.getUserPrincipal().getName()).getNotifications();
		for(Notifications notif: userNotifications) {
			if(!notif.isViewed()) {
				notif.setViewed(true);
				notificationService.saveNotification(notif);
			}
		}
		return userNotifications;
	}
	
	@RequestMapping(value = "/getUnviewedNotifications", method = RequestMethod.GET)
	public @ResponseBody  List<Notifications> getUnviewedNotifications(HttpServletRequest req, HttpServletResponse resp) {
		List<Notifications> userNotifications = userService.getUser(req.getUserPrincipal().getName()).getNotifications();
		List<Notifications> unViewed = new ArrayList<Notifications>();
		
		for(Notifications notif: userNotifications) {
			if(!notif.isViewed()) {
				unViewed.add(notif);
			}
		}
		
		return unViewed;
	}
		
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public String adminPage(Model theModel) {
		theModel.addAttribute("title", "Spring Security Custom Login Form");
		theModel.addAttribute("message", "This is protected page!");
		return "admin";
	}

	

}
