package com.gokyur.controller;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gokyur.email.TLSEmail;
import com.gokyur.entity.Lists;
import com.gokyur.entity.Roles;
import com.gokyur.entity.SharedLists;
import com.gokyur.entity.Users;
import com.gokyur.service.ListService;
//import com.gokyur.service.NotificationService;
import com.gokyur.service.RoleService;
import com.gokyur.service.UserService;
import com.gokyur.utilities.GokyurUtilities;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ListService listService;
	
	@Autowired
    private RoleService roleService;
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
	@RequestMapping("/register")
	public String registerPage(Model theModel,
			@RequestParam(value = "error", required = false) String error) {
		Users theUser = new Users();
		theModel.addAttribute("user",theUser);
		
		if(error != null) {
			theModel.addAttribute("error", "Register failed! Please try again.");
		}
		
		return "register";
	}
	
	@RequestMapping(value="/registerUser", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") Users theUser,Model theModel) throws ParseException {
		String roleAdmin = "ROLE_ADMIN";
        String roleUser = "ROLE_USER";
        
		if(!roleService.isRolesConfigured()) {
	        roleService.saveRole(new Roles(roleAdmin));
	        roleService.saveRole(new Roles(roleUser));
		}
        
        Users checkUser = userService.getUser(theUser.getUsername());
        if(checkUser != null) {
        	System.out.println("Username is already exist!");
        }
        if(!theUser.getPassword().equals(theUser.getPasswordConf())) {
        	System.out.println("Passwords do not match!");
        }
        if(!theUser.getEmail().equals(theUser.getEmailConf())) {
        	System.out.println("Emails do not match!");
        }          
		if(theUser.getPassword().equals(theUser.getPasswordConf()) && theUser.getEmail().equals(theUser.getEmailConf()) && checkUser == null) {
			Roles userRole = roleService.findByName(roleUser);
	        String encodedPass = GokyurUtilities.MD5(theUser.getPassword());
	        theUser.setPassword(encodedPass);
	        theUser.setRole(userRole);
	        
	        //LOCAL
	        theUser.setCreatedAt(GokyurUtilities.getNow());
	        
	        //SERVER
//	        theUser.setCreatedAt(GokyurUtilities.convertLocalDateTimeToServer(GokyurUtilities.getNow().toString()));
	        	        
	        userService.saveUser(theUser);
	        
	        Users dbUser = userService.getUser(theUser.getUsername());
	        
	        String userUniqueHash = GokyurUtilities.MD5(String.valueOf(dbUser.getId())) + GokyurUtilities.MD5(dbUser.getUsername());
	        
	        String activationLink = "http://localhost:8080/wunderlistclone/activation/"+userUniqueHash;
	        String emailContent = "Welcome to Wunderlistclone! This application is made only for educational purpose. Here is your activation link: "+activationLink;
	        emailContent += "Wunderlistclone'a hoþgeldiniz! Bu web uygulamasý yalnýzca eðitimsel amaç ile yapýlmýþtýr. Aktivasyon linkiniz: "+activationLink;
	        
	        
			TLSEmail.sendEmail(dbUser.getEmail(), "Activate your account!/Hesabýnýzý etkinleþtirin!", emailContent);
			
			return "redirect:/login?activationsent";
		}
		
		return "redirect:/register?error";
		
	}
	
	@RequestMapping(value="/resetpassword")
	public String resetpassword(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "sent", required = false) String sent,
			Model theModel) {
		theModel.addAttribute("user", new Users());
		
		if(error != null) {
			theModel.addAttribute("error", "There is no such an email.");
		}
		
		if(sent != null) {
			theModel.addAttribute("msg", "Your new password is sent to your email.");
		}
		
		return "resetpassword";
	}
	
	@RequestMapping(value="/resetUserPassword")
	public String resetUserPassword(
			@ModelAttribute("user") Users theUser,
			Model theModel) {
		
		Users tempUser = userService.getUserByEmail(theUser.getEmail());
		if(tempUser != null) {
			String newPassword = randomString(8);
			tempUser.setPassword(GokyurUtilities.MD5(newPassword));
			userService.saveUser(tempUser);
			
	        String emailContent = "You have requested to reset your password. Your new password is: "+newPassword;
	        emailContent += "\n\nÞifrenizi sýfýrlamayý talep ettiniz. Yeni þifreniz:"+newPassword;
			TLSEmail.sendEmail(tempUser.getEmail(), "Reset your password!/Þifrenizi sýfýrlayýn", emailContent);
			
			return "redirect:/resetpassword?sent";
		}else {
			return "redirect:/resetpassword?error";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout,
		@RequestParam(value = "activationsent", required = false) String activationsent,
		@RequestParam(value = "useractivated", required = false) String useractivated,
		Model theModel) {

		if (error != null) {
			theModel.addAttribute("error", "Login failed. Please check credentials and be sure you activated your account.");
		}

		if (logout != null) {
			theModel.addAttribute("msg", "You've been logged out successfully.");
		}
		
		if (activationsent != null) {
			theModel.addAttribute("msg", "Activation link has been sent to your email.");
		}
		
		if (useractivated != null) {
			theModel.addAttribute("msg", "User activated. Now you can login.");
		}

		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//		if (!(auth instanceof AnonymousAuthenticationToken)) {
//
//		    /* The user is logged in :) */
//		    return "redirect:/lists";
//		}else {
			return "login";	
//		}


	}
	
	@RequestMapping("activation/{userHash}")
	public String activeUser(@PathVariable(value="userHash") String userHash) {
		
		List<Users> allUsers = userService.getAllUsers();
		
		for(Users theUser: allUsers) {
			String tempHash = GokyurUtilities.MD5(String.valueOf(theUser.getId())) + GokyurUtilities.MD5(theUser.getUsername());
			//System.out.println("user temp hash: "+tempHash);
			if(tempHash.equals(userHash) && !theUser.isActive()) {
				theUser.setActive(true);
				userService.saveUser(theUser);
				System.out.println("User activated.");
				return "redirect:/login?useractivated";
			}
		}

		return "redirect:/login";
	}
	
	@RequestMapping(value = "/isLoginedUserOwnerOf", method = RequestMethod.GET)
	public @ResponseBody  boolean isLoginedUserOwnerOf(@RequestParam("listId") int id, HttpServletRequest req, HttpServletResponse resp) {
		Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
		List<Lists> loginedUserList = loginedUser.getLists();
		for(Lists list: loginedUserList) {
			if(list.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/getOwnerOfTheList", method = RequestMethod.POST)
	public @ResponseBody  String getOwnerOfTheList(@RequestParam("listId") int id, HttpServletRequest req, HttpServletResponse resp) {
		List<Users> allUsers = userService.getAllUsers();
		for(Users user: allUsers) {
			for(Lists list: user.getLists()) {
				if(list.getId() == id) {
					return user.getUsername();
				}
			}
		}
		return "$#unknown";
	}
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.POST)
	public @ResponseBody  List<Users> getAllUsers(@RequestParam("listId") int id, HttpServletRequest req, HttpServletResponse resp) {
		Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
		List<Users> listUsers = userService.getAllUsers();

		List<SharedLists> sharedLists = userService.getAllSharedLists();
		
		//Remove the owner of the list from users list
		for (Iterator<Users> iter = listUsers.iterator(); iter.hasNext(); ) {
		   Users element = iter.next();
		   if (element.getId() == loginedUser.getId()) {
		      iter.remove();
		   }
		   
		   //Check and remove from user list if already shared
		   for(SharedLists sharedList: sharedLists) {
			   if(sharedList.getSharedList() == id && sharedList.getSharedWith().getId() == element.getId()) {
				   iter.remove();
			   }
		   }
		}
		
		return listUsers;
	}
	
	@RequestMapping(value = "/getSharedUsers", method = RequestMethod.POST)
	public @ResponseBody List<Users> getSharedUsers(@RequestParam("listId") int id, HttpServletRequest req, HttpServletResponse resp){
		List<Users> tempUsers = new ArrayList<Users>();
		List<SharedLists> sharedLists = userService.getAllSharedLists();
		for(SharedLists sharedList: sharedLists) {
			if(sharedList.getSharedList() == id) {
				tempUsers.add(userService.getUser(sharedList.getSharedWith().getId()));
			}
		}
		return tempUsers;
	}
	
	@RequestMapping(value="/removeSharedUser", method=RequestMethod.POST)
	public @ResponseBody void removeSharedUser(@RequestParam("listId") int id, @RequestParam("userName") String userName, HttpServletRequest req, HttpServletResponse resp) {
		SharedLists sharedList = listService.getSharedList(id,userService.getUser(userName).getId());
		listService.removeSharedList(sharedList);
	}
	
	@RequestMapping(value="/getUserDetails", method=RequestMethod.POST)
	public @ResponseBody Users getUserDetails(HttpServletRequest req, HttpServletResponse resp) {
		Users tempUser = userService.getUser(req.getUserPrincipal().getName());
		return tempUser;
	}
	
	@RequestMapping(value="/saveNewEmail", method=RequestMethod.POST)
	public @ResponseBody boolean saveNewEmail(@RequestParam("newEmail") String newEmail, @RequestParam("userPass") String userPass, HttpServletRequest req, HttpServletResponse resp) {
		boolean changed = false;
		Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
		if(GokyurUtilities.MD5(userPass).equals(loginedUser.getPassword())) {
			loginedUser.setEmail(newEmail);
			userService.saveUser(loginedUser);
			changed = true;
		}
		return changed;
	}
	
	@RequestMapping(value="/saveNewPassword", method=RequestMethod.POST)
	public @ResponseBody boolean saveNewPassword(@RequestParam("currentPass") String currentPass, @RequestParam("newPass") String newPass, @RequestParam("newPassConf") String newPassConf, HttpServletRequest req, HttpServletResponse resp) {
		boolean changed = false;
		Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
		if(GokyurUtilities.MD5(currentPass).equals(loginedUser.getPassword())) {
			if(newPass.equals(newPassConf)) {
				String newPassMD5 = GokyurUtilities.MD5(newPass);			
				loginedUser.setPassword(newPassMD5);
				userService.saveUser(loginedUser);
				changed = true;
			}
		}
		return changed;
	}
	
	@RequestMapping(value="/changeFullname", method=RequestMethod.POST)
	public @ResponseBody boolean changeFullname(@RequestParam("newfullname") String fullname, HttpServletRequest req, HttpServletResponse resp) {
		boolean changed = false;
		Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
		try {
			loginedUser.setFullname(fullname);
			userService.saveUser(loginedUser);
			changed = true;
		}catch(Exception e) {
			changed = false;
		}
		return changed;
	}
	
//	@RequestMapping(value="/changeUsername", method=RequestMethod.POST)
//	public @ResponseBody boolean changeUsername(@RequestParam("newusername") String username, HttpServletRequest req, HttpServletResponse resp) {
//		boolean changed = false;
//		Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
//		try {
//			loginedUser.setUsername(username);
//			userService.saveUser(loginedUser);
//			changed = true;
//		}catch(Exception e) {
//			changed = false;
//		}
//		return changed;
//	}
}
