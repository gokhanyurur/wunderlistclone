package com.gokyur.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gokyur.entity.Lists;
import com.gokyur.entity.Roles;
import com.gokyur.entity.SubTasks;
import com.gokyur.entity.Tasks;
import com.gokyur.entity.Users;
import com.gokyur.service.ListService;
import com.gokyur.service.RoleService;
import com.gokyur.service.UserService;
import com.gokyur.utilities.GokyurUtilities;


@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ListService listService;
	
	@Autowired
    private RoleService roleService;
		
	@RequestMapping("/register")
	public String indexPage(Model theModel) {
		Users theUser = new Users();
		theModel.addAttribute("user",theUser);
		return "register";
	}
	
	@RequestMapping(value="/registerUser", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") Users theUser,Model theModel) {
		String roleAdmin = "ROLE_ADMIN";
        String roleUser = "ROLE_USER";
		if(!roleService.isRolesConfigured()) {

	        roleService.saveRole(new Roles(roleAdmin));
	        roleService.saveRole(new Roles(roleUser));
		}

        Roles userRole = roleService.findByName(roleUser);
        String encodedPass = GokyurUtilities.MD5(theUser.getPassword());
        theUser.setPassword(encodedPass);
        theUser.setRole(userRole);
		userService.saveUser(theUser);
        //roleService.saveRole(userRole);
		return "redirect:/login";
		
	}
	
	@RequestMapping(value="/lists**", method = RequestMethod.GET)
	public String listsPage(Model theModel, HttpServletRequest req) {
		return "lists";
	}
	
	@RequestMapping(value = "/getLists", method = RequestMethod.GET)
	public @ResponseBody  List<Lists> getListsList(HttpServletRequest req, HttpServletResponse resp) {
		List<Lists> userLists = userService.getUser(req.getUserPrincipal().getName()).getLists();
	
		/*return Map<String, Object> type
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		boolean status = true;
		map.put("status", status);
		map.put("message", "Basarili");
		map.put("data", name);
		
		for(Lists list:userLists) {
			map.put(String.valueOf(list.getId()), list.getListName());
		}*/
			
		return userLists;
	}
	
	@RequestMapping(value = "/getTasksList", method = RequestMethod.GET)
	public @ResponseBody  List<Tasks> getTasksList(@RequestParam("listId") int id, HttpServletRequest req, HttpServletResponse resp) {
		List<Tasks> listTasks = listService.getList(id).getTasks();
		return listTasks;
	}
	
	@RequestMapping(value="/removeTask", method=RequestMethod.POST)
	public @ResponseBody void removeTask(@RequestParam("taskId") int id, HttpServletRequest req, HttpServletResponse resp) {
		Tasks tempTask = listService.getTask(id);
		listService.removeTask(tempTask);
	}
	
	@RequestMapping(value="/starTheTask", method=RequestMethod.POST)
	public @ResponseBody void starTask(@RequestParam("taskId") int id, HttpServletRequest req, HttpServletResponse resp) {
		Tasks tempTask = listService.getTask(id);
		if(!tempTask.isStared()) {
			tempTask.setStared(true);
		}else {
			tempTask.setStared(false);
		}
		listService.addTask(tempTask);
	}
	
	@RequestMapping(value="getTaskDetails", method=RequestMethod.POST)
	public @ResponseBody Tasks getTaskDetails(@RequestParam("taskId") int id, HttpServletRequest req, HttpServletResponse resp){
		Tasks tempTask = listService.getTask(id);
		return tempTask;
	}
		
	@RequestMapping(value="/createList", method=RequestMethod.POST)
	public @ResponseBody void createList(@RequestParam("listname") String listname, Model theModel, HttpServletRequest req) {
		Users theUser = userService.getUser(req.getUserPrincipal().getName());
		Lists theList = new Lists(listname);
		theList.setOwner(theUser);
		listService.createList(theList);
	}
		
	@RequestMapping(value="/addTask", method=RequestMethod.POST)
	public @ResponseBody void addTask(@RequestParam("listId") int listId,
									  @RequestParam("taskName") String taskName,
									  Model theModel, HttpServletRequest req) {
		Lists theList = listService.getList(listId);
		Tasks theTask = new Tasks(taskName);
		theTask.setList(theList);
		listService.addTask(theTask);
	}
	
	@RequestMapping(value="/updateTask", method=RequestMethod.POST)
	public @ResponseBody void updateTask(@RequestParam("taskId") int taskId,
									  @RequestParam("taskName") String taskName,
									  Model theModel, HttpServletRequest req) {
		Tasks theTask = listService.getTask(taskId);
		theTask.setTask(taskName);
		listService.addTask(theTask);
	}
	
	@RequestMapping(value="getSubTasks", method=RequestMethod.POST)
	public @ResponseBody List<SubTasks> getSubTasks(@RequestParam("taskId") int id, HttpServletRequest req, HttpServletResponse resp){
		List<SubTasks> tempSubTasks = listService.getTask(id).getSubTasks();
		return tempSubTasks;
	}
	
	@RequestMapping(value="removeSubTask", method=RequestMethod.POST)
	public @ResponseBody void removeSubTask(@RequestParam("subTaskId") int id, HttpServletRequest req, HttpServletResponse resp) {
		SubTasks tempSubTask = listService.getSubTask(id);
		listService.removeSubTask(tempSubTask);
	}
	
	@RequestMapping(value="/addSubTask", method=RequestMethod.POST)
	public @ResponseBody void addSubTask(@RequestParam("taskId") int taskId,
											  @RequestParam("subTaskName") String subTaskName,
											  Model theModel, HttpServletRequest req) {
		Tasks theTask = listService.getTask(taskId);
		SubTasks theSubTask = new SubTasks(subTaskName);
		theSubTask.setBelongsToTask(theTask);
		listService.addSubTask(theSubTask);
	}
	
	@RequestMapping(value="/saveTaskNotes", method=RequestMethod.POST)
	public @ResponseBody void saveNotesOfTask(@RequestParam("taskId") int taskId, @RequestParam("taskNotes") String taskNotes, HttpServletRequest req, HttpServletResponse resp) {
		Tasks theTask = listService.getTask(taskId);
		theTask.setNotes(taskNotes);
		listService.addTask(theTask);
	}
	
	
	
	/*@RequestMapping(value="/taskCommentProcess", method=RequestMethod.POST)
	public String addComment(@ModelAttribute("theComment") Comments theComment, Model theModel) {
		Tasks theTask = listService.getTask(1);
		theComment.setTask(theTask);
		listService.addComment(theComment);
		return "redirect:/addSubTask";
	}*/
		
	/*@RequestMapping(value="/shareList", method=RequestMethod.POST)
	public String shareListProcess(@ModelAttribute("theSharedList") SharedLists theSharedList,
									Model theModel) {
		Users tempUser = userService.getUser(theSharedList.getSharedUser_ID());
		Lists tempList = listService.getList(theSharedList.getSharedList());
		SharedLists sharedList = new SharedLists(tempList.getId(), tempUser);
		listService.shareList(sharedList);
		return "redirect:/";
	}*/
	
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public String adminPage(Model theModel) {
		theModel.addAttribute("title", "Spring Security Custom Login Form");
		theModel.addAttribute("message", "This is protected page!");
		return "admin";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout,
		Model theModel) {

		if (error != null) {
			theModel.addAttribute("error", "Invalid username and password!");
		}

		if (logout != null) {
			theModel.addAttribute("msg", "You've been logged out successfully.");
		}

		return "login";

	}

}
