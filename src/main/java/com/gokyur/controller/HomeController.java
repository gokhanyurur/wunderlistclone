package com.gokyur.controller;



import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gokyur.entity.Comments;
import com.gokyur.entity.Lists;
import com.gokyur.entity.Roles;
import com.gokyur.entity.SharedLists;
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
		
		//String roleAdmin = "ROLE_ADMIN";
        String roleUser = "ROLE_USER";
        Roles userRole = new Roles(theUser,roleUser);
        String encodedPass = GokyurUtilities.MD5(theUser.getPassword());
        theUser.setPassword(encodedPass);
        theUser.setRole(userRole);
		userService.saveUser(theUser);
        roleService.saveRole(userRole);
		return "redirect:/login";
		
	}
	
	@RequestMapping(value="/lists**", method = RequestMethod.GET)
	public String listsPage(Model theModel, HttpServletRequest req) {
		//Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
		
		//List<Lists> userLists = loginedUser.getLists();
		
		/*String username = req.getUserPrincipal().getName();
		theModel.addAttribute("username", username);*/
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
	public @ResponseBody  List<Tasks> test(@RequestParam("listId") int id,HttpServletRequest req, HttpServletResponse resp) {
		List<Tasks> listTasks = listService.getList(id).getTasks();
	
		return listTasks;
	}
	
	@RequestMapping("/createList")
	public String createListPage(Model theModel) {
		Lists theList = new Lists();
		theModel.addAttribute("theList",theList);
		return "createList";
	}
	
	/*@RequestMapping(value="/createListProcess", method=RequestMethod.POST)
	public String createList(@ModelAttribute("theList") Lists theList, Model theModel, HttpServletRequest req) {
		Users theUser = userService.getUser(req.getUserPrincipal().getName());
		theList.setOwner(theUser);
		listService.createList(theList);
		return "redirect:/addTask";
	}*/
	
	@RequestMapping(value="/createListProcess", method=RequestMethod.POST)
	public @ResponseBody void createList(@RequestParam("listname") String listname, Model theModel, HttpServletRequest req) {
		Users theUser = userService.getUser(req.getUserPrincipal().getName());
		Lists theList = new Lists(listname);
		theList.setOwner(theUser);
		listService.createList(theList);
	}
	
	@RequestMapping("/addTask")
	public String addTaskPage(Model theModel) {
		Tasks theTask = new Tasks();
		theModel.addAttribute("theTask",theTask);
		return "addTask";
	}
	
	@RequestMapping(value="/addTaskProcess", method=RequestMethod.POST)
	public @ResponseBody void addTask(@RequestParam("listId") int listId, @RequestParam("taskName") String taskName,
						  Model theModel, HttpServletRequest req) {
		Lists theList = listService.getList(listId);
		Tasks theTask = new Tasks(taskName);
		theTask.setList(theList);
		listService.addTask(theTask);
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
	public String shareListPage(Model theModel, HttpServletRequest req,Authentication authentication) {
		theModel.addAttribute("allLists", userService.getUser(req.getUserPrincipal().getName()).getLists());
		theModel.addAttribute("allUsers", userService.getAllUsers());
		theModel.addAttribute("theSharedList",new SharedLists());
		
		/*
		 * For testing ---------------------
		 */
			Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
			
			System.out.println("Role of user: "+loginedUser.getRoles().getRole());
			
			System.out.println("Logined User: "+loginedUser.getUsername());
			System.out.println("\tLists:");
			for(Lists list:loginedUser.getLists()) {
				System.out.println("\t"+list.getListName());
				System.out.println("\t\tTasks:");
				for(Tasks task:list.getTasks()) {
					System.out.println("\t\t"+task.getTask());
					System.out.println("\t\t\tComments of this task:");
					for(Comments comment:task.getComments()) {
						System.out.println("\t\t\t"+comment.getComment());
					}
					System.out.println("\t\t\t\tSubtasks:");
					for(SubTasks subtask: task.getSubTasks()) {
						System.out.println("\t\t\t\t"+subtask.getSubTask());
					}
				}
				System.out.print("This list shared with: ");
				List<SharedLists> sharedList = userService.getAllSharedLists();
				for(SharedLists sl: sharedList) {
					if(sl.getSharedList() == list.getId()) {
						System.out.println(sl.getSharedWith().getUsername());
					}
				}
			}
			
		/*
		 * For Testing ---------------------
		 */
		
		return "shareList";
	}

	@RequestMapping(value="/shareListProcess", method=RequestMethod.POST)
	public String shareListProcess(@ModelAttribute("theSharedList") SharedLists theSharedList,
									Model theModel) {
		Users tempUser = userService.getUser(theSharedList.getSharedUser_ID());
		Lists tempList = listService.getList(theSharedList.getSharedList());
		SharedLists sharedList = new SharedLists(tempList.getId(), tempUser);
		listService.shareList(sharedList);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/welcome**", method = RequestMethod.GET)
	public String welcomePage(Model theModel) {

		theModel.addAttribute("title", "Spring Security Custom Login Form");
		theModel.addAttribute("message", "This is welcome page!");
		
		return "hello";

	}

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
