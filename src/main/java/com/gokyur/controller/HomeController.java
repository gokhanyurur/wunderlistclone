package com.gokyur.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gokyur.entity.Comments;
import com.gokyur.entity.Lists;
import com.gokyur.entity.SharedLists;
import com.gokyur.entity.SubTasks;
import com.gokyur.entity.Tasks;
import com.gokyur.entity.Users;
import com.gokyur.service.ListService;
import com.gokyur.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ListService listService;
	
	@RequestMapping("/register")
	public String indexPage(Model theModel) {
		Users theUser = new Users();
		theModel.addAttribute("user",theUser);
		return "register";
	}
	
	@RequestMapping(value="/registerUser", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") Users theUser,Model theModel) {		
		userService.saveUser(theUser);
		return "redirect:/createList";
		
	}
	
	@RequestMapping("/createList")
	public String createListPage(Model theModel) {
		Lists theList = new Lists();
		theModel.addAttribute("theList",theList);
		return "createList";
	}
	
	@RequestMapping(value="/createListProcess", method=RequestMethod.POST)
	public String createList(@ModelAttribute("theList") Lists theList, Model theModel) {
		Users theUser = userService.getUser(1);
		theList.setOwner(theUser);
		listService.createList(theList);
		return "redirect:/addTask";
	}
	
	@RequestMapping("/addTask")
	public String addTaskPage(Model theModel) {
		Tasks theTask = new Tasks();
		theModel.addAttribute("theTask",theTask);
		return "addTask";
	}
	
	@RequestMapping(value="/addTaskProcess", method=RequestMethod.POST)
	public String addTask(@ModelAttribute("theTask") Tasks theTask, Model theModel) {
		Lists theList = listService.getList(1);
		theTask.setList(theList);
		listService.addTask(theTask);
		return "redirect:/taskComment";
	}
	
	@RequestMapping("/taskComment")
	public String taskComment(Model theModel) {
		Comments theComment = new Comments();
		theModel.addAttribute("theComment",theComment);
		return "taskComment";
	}
	
	@RequestMapping(value="/taskCommentProcess", method=RequestMethod.POST)
	public String addComment(@ModelAttribute("theComment") Comments theComment, Model theModel) {
		Tasks theTask = listService.getTask(1);
		theComment.setTask(theTask);
		listService.addComment(theComment);
		return "redirect:/addSubTask";
	}
	
	@RequestMapping("/addSubTask")
	public String addSubTaskPage(Model theModel) {
		SubTasks theSubTask = new SubTasks();
		theModel.addAttribute("theSubTask", theSubTask);
		return "addSubTask";
	}
	
	
	@RequestMapping(value="/addSubTaskProcess", method=RequestMethod.POST)
	public String addSubTaskProcess(@ModelAttribute("theSubTask") SubTasks theSubTask, Model theModel) {
		Tasks theTask = listService.getTask(1);
		theSubTask.setBelongsToTask(theTask);
		listService.addSubTask(theSubTask);
		return "redirect:/";
	}
	
	@RequestMapping("/shareList")
	public String shareListPage(Model theModel) {
		theModel.addAttribute("allLists", userService.getUser(1).getLists());
		theModel.addAttribute("allUsers", userService.getAllUsers());
		theModel.addAttribute("theSharedList",new SharedLists());
		for(Lists list:userService.getUser(1).getLists()) {
			System.out.println(list.getListName());
		}
		for(Users user:userService.getAllUsers()) {
			System.out.println(user.getUsername()+" - "+user.getEmail());
		}
		return "shareList";
	}

	
	@RequestMapping(value="/shareListProcess", method=RequestMethod.POST)
	public String shareListProcess(@ModelAttribute("theSharedList") SharedLists theSharedList,
									Model theModel) {
		Users theUser = userService.getUser(theSharedList.getSharedWith().getId());
		Lists sharedList = listService.getList(theSharedList.getSharedList());
		theSharedList.setSharedListId(sharedList.getId());
		theSharedList.setSharedWith(theUser);
		listService.shareList(theSharedList);
		return "redirect:/";
	}
//	@RequestMapping(value="/shareListProcess", method=RequestMethod.POST)
//	public String shareListProcess(HttpServletRequest req, HttpServletResponse res) {
//
//		int listid = Integer.parseInt(req.getParameter("listid").trim());
//		
//		return "redirect:/";
//	}
}
