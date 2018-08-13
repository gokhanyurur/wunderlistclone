package com.gokyur.controller;

import java.text.ParseException;
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
import com.gokyur.entity.SharedLists;
import com.gokyur.entity.Users;
import com.gokyur.service.NotificationService;
import com.gokyur.service.UserService;
import com.gokyur.utilities.GokyurUtilities;


@Controller
public class NotificationController {

	@Autowired
	private UserService userService;
		
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
	public @ResponseBody  List<Notifications> getUnviewedNotifications(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		List<Notifications> allNotifications = notificationService.getAllNotifications();
		List<Notifications> unViewed = new ArrayList<Notifications>();
		
		Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
		List<SharedLists> sharedList = userService.getAllSharedLists();
		
		for(Notifications notif: allNotifications) {
			if((!notif.isViewed() && notif.isNotified()) || (!notif.isViewed() && !notif.isNotified() && GokyurUtilities.checkTime(notif.getLastdate()) > 0)) {
				
				if(notif.getUser().getId() == loginedUser.getId()) {
					if(!notif.isNotified()) {
						notif.setNotified(true);
						
						//LOCAL
						notif.setNotifiedat(GokyurUtilities.getNow());		
						//SERVER
//						notif.setNotifiedat(GokyurUtilities.convertLocalDateTimeToServer(GokyurUtilities.getNow().toString()));
						
						notificationService.saveNotification(notif);
					}
					
					unViewed.add(notif);
				}
				for(SharedLists tempSharedL:sharedList) {
					if(notif.getTask().getList().getId() == tempSharedL.getSharedList() && loginedUser.getId() == tempSharedL.getSharedWith().getId()) {
						if(!notif.isNotified()) {
							notif.setNotified(true);
							
							//LOCAL
							notif.setNotifiedat(GokyurUtilities.getNow());		
							
							//SERVER
//							notif.setNotifiedat(GokyurUtilities.convertLocalDateTimeToServer(GokyurUtilities.getNow().toString()));
							
							notificationService.saveNotification(notif);
						}
						unViewed.add(notif);
					}
				}
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
