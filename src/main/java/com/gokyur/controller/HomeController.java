package com.gokyur.controller;

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
		SharedLists sharedList = new SharedLists();
		theModel.addAttribute("theSharedList", sharedList);
		return "shareList";
	}
	
	@RequestMapping(value="/shareListProcess", method=RequestMethod.POST)
	public String shareListProcess(@ModelAttribute("theSharedList") SharedLists theSharedList, Model theModel) {
		Users sharedWith = userService.getUser(theSharedList.getSharedWith().getId());
		theSharedList.setSharedWith(sharedWith);
		listService.shareList(theSharedList);
		return "redirect:/";
	}
}
