package com.gokyur.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gokyur.entity.Lists;
import com.gokyur.entity.SharedLists;
import com.gokyur.entity.Users;
import com.gokyur.service.ListService;
//import com.gokyur.service.NotificationService;
//import com.gokyur.service.RoleService;
import com.gokyur.service.UserService;

@Controller
public class ListController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ListService listService;
	
	@RequestMapping(value="/lists**", method = RequestMethod.GET)
	public String listsPage(Model theModel, HttpServletRequest req) {
		return "lists";
	}
	
	@RequestMapping(value="/createList", method=RequestMethod.POST)
	public @ResponseBody void createList(@RequestParam("listname") String listname, Model theModel, HttpServletRequest req) throws ParseException {
		Users theUser = userService.getUser(req.getUserPrincipal().getName());
		Lists theList = new Lists(listname);
		theList.setOwner(theUser);
		
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        theList.setCreatedat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(timeStamp));
        
		listService.createList(theList);
	}
	
	@RequestMapping(value="/removeList", method=RequestMethod.POST)
	public @ResponseBody void removeList(@RequestParam("listId") int listId, Model theModel, HttpServletRequest req) {
		Lists theList = listService.getList(listId);
		listService.removeList(theList);
	}
	
	@RequestMapping(value = "/getLists", method = RequestMethod.GET)
	public @ResponseBody  List<Lists> getListsList(HttpServletRequest req, HttpServletResponse resp) {
		List<Lists> userLists = userService.getUser(req.getUserPrincipal().getName()).getLists();			
		return userLists;
	}
		
	@RequestMapping(value = "/getSharedLists", method = RequestMethod.GET)
	public @ResponseBody  List<Lists> getSharedLists(HttpServletRequest req, HttpServletResponse resp) {
		Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
		List<SharedLists> allSharedLists = userService.getAllSharedLists();
		List<Lists> sharedLists = new ArrayList<Lists>();
		for(SharedLists sharedList: allSharedLists) {
			if(sharedList.getSharedWith().getId() == loginedUser.getId()) {
				Lists tempList = listService.getList(sharedList.getSharedList());
				tempList.setOwner(null);
				sharedLists.add(tempList);
			}
		}
		return sharedLists;
	}
	
	@RequestMapping(value = "/editListName", method = RequestMethod.POST)
	public @ResponseBody void editListName(@RequestParam("listId") int id, @RequestParam("listName") String name, HttpServletRequest req, HttpServletResponse resp) {
		Lists theList = listService.getList(id);
		theList.setListName(name);
		listService.createList(theList);
	}
	
	@RequestMapping(value="/shareList", method=RequestMethod.POST)
	public @ResponseBody void shareList(@RequestParam("listId") int id, @RequestParam("userIdList") String[] userIdList, HttpServletRequest req, HttpServletResponse resp) {
		Lists theList = listService.getList(id);
		System.out.print(theList.getListName()+" will be shared with: ");
		for(String u_id: userIdList) {
			Users theUser = userService.getUser(Integer.parseInt(u_id.replaceAll("\\D+","")));
			SharedLists sharedList = new SharedLists(theList.getId(), theUser);
			listService.shareList(sharedList);
		}
	}
	
}
