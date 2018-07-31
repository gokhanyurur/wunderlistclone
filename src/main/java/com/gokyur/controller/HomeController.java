package com.gokyur.controller;



import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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

import com.gokyur.entity.Comments;
import com.gokyur.entity.Lists;
import com.gokyur.entity.Notifications;
import com.gokyur.entity.Roles;
import com.gokyur.entity.SharedLists;
import com.gokyur.entity.SubTasks;
import com.gokyur.entity.Tasks;
import com.gokyur.entity.Users;
import com.gokyur.service.ListService;
import com.gokyur.service.NotificationService;
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
	
	@Autowired
	private NotificationService notificationService;
		
	@RequestMapping("/register")
	public String registerPage(Model theModel) {
		Users theUser = new Users();
		theModel.addAttribute("user",theUser);
		return "register";
	}
	
	@RequestMapping(value="/ws", method = RequestMethod.GET)
	public String wsPage(Model theModel, HttpServletRequest req) {
		return "ws";
	}
	
	@RequestMapping(value="/registerUser", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") Users theUser,Model theModel) throws ParseException {
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
        
//        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
//        theUser.setCreatedAt(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(timeStamp));
        theUser.setCreatedAt(GokyurUtilities.getNow());
        
		userService.saveUser(theUser);
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
	
	@RequestMapping(value = "/getTasksList", method = RequestMethod.GET)
	public @ResponseBody  List<Tasks> getTasksList(@RequestParam("listId") int id, HttpServletRequest req, HttpServletResponse resp) {
		List<Tasks> allTasks = listService.getList(id).getTasks();
		/*List<Tasks> tempTasks = new ArrayList<Tasks>();
		for(Tasks theTask: allTasks) {
			if(!theTask.isCompleted()) {
				tempTasks.add(theTask);
			}
		}
		return tempTasks;*/
		return allTasks;
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
	
	@RequestMapping(value = "/editListName", method = RequestMethod.POST)
	public @ResponseBody void editListName(@RequestParam("listId") int id, @RequestParam("listName") String name, HttpServletRequest req, HttpServletResponse resp) {
		Lists theList = listService.getList(id);
		theList.setListName(name);
		listService.createList(theList);
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
	
	@RequestMapping(value="/removeTask", method=RequestMethod.POST)
	public @ResponseBody void removeTask(@RequestParam("taskId") int id, HttpServletRequest req, HttpServletResponse resp) {
		Tasks tempTask = listService.getTask(id);
		listService.removeTask(tempTask);
	}
	
	@RequestMapping(value="/removeComment", method=RequestMethod.POST)
	public @ResponseBody void removeComment(@RequestParam("commentId") int cid, HttpServletRequest req, HttpServletResponse resp) {
		Comments theComment = listService.getComment(cid);
		listService.removeComment(theComment);
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
		
	@RequestMapping(value="/addTask", method=RequestMethod.POST)
	public @ResponseBody void addTask(@RequestParam("listId") int listId,
									  @RequestParam("taskName") String taskName,
									  Model theModel, HttpServletRequest req) throws ParseException {
		Lists theList = listService.getList(listId);
		Tasks theTask = new Tasks(taskName);
		theTask.setList(theList);
		
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        theTask.setCreatedat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(timeStamp));
		
		listService.addTask(theTask);
	}
	
	@RequestMapping(value="/completeTask", method=RequestMethod.POST)
	public @ResponseBody void completeTask(@RequestParam("taskId") int id, HttpServletRequest req, HttpServletResponse resp) {
		Tasks theTask = listService.getTask(id);
		if(theTask.isCompleted()) {
			theTask.setCompleted(false);
		}else {
			theTask.setCompleted(true);
		}
		listService.addTask(theTask);

	}
	
	@RequestMapping(value="/completeSubTask", method=RequestMethod.POST)
	public @ResponseBody void completeSubTask(@RequestParam("subTaskId") int sid, HttpServletRequest req, HttpServletResponse resp) {
		SubTasks theSubTask = listService.getSubTask(sid);
		if(theSubTask.isCompleted()) {
			theSubTask.setCompleted(false);
		}else {
			theSubTask.setCompleted(true);
		}
		listService.addSubTask(theSubTask);

	}
	
	@RequestMapping(value="/isTaskCompleted", method=RequestMethod.POST)
	public @ResponseBody boolean isTaskCompleted(@RequestParam("taskId") int id, HttpServletRequest req, HttpServletResponse resp) {
		Tasks theTask = listService.getTask(id);
		
		return theTask.isCompleted();

	}
	
	@RequestMapping(value="/isSubTaskCompleted", method=RequestMethod.POST)
	public @ResponseBody boolean isSubTaskCompleted(@RequestParam("subTaskId") int sid, HttpServletRequest req, HttpServletResponse resp) {
		SubTasks theSubTask = listService.getSubTask(sid);
		
		return theSubTask.isCompleted();

	}
	
	
	@RequestMapping(value="/isLastDateOfTaskSet", method=RequestMethod.POST)
	public @ResponseBody boolean isLastDateOfTaskSet(@RequestParam("taskId") int tid, HttpServletRequest req, HttpServletResponse resp) {
		Tasks theTask = listService.getTask(tid);
		
		if(theTask.getLastdate() == null) {
			return false;
		}else {
			return true;
		}

	}
	
	@RequestMapping(value="/updateTask", method=RequestMethod.POST)
	public @ResponseBody void updateTask(@RequestParam("taskId") int taskId,
									  @RequestParam("taskName") String taskName,
									  Model theModel, HttpServletRequest req) {
		Tasks theTask = listService.getTask(taskId);
		theTask.setTask(taskName);
		listService.addTask(theTask);
	}
	
	@RequestMapping(value="/setTimeForTask", method=RequestMethod.POST)
	public @ResponseBody void setTimeForTask(@RequestParam("lastDate") String dateString, @RequestParam("taskId") int tid, HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		dateString+=":00";
		Tasks theTask = listService.getTask(tid);
		Date tempDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(dateString);
		theTask.setLastdate(tempDate);
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
	
	@RequestMapping(value="/writeComment", method=RequestMethod.POST)
	public @ResponseBody List<Comments> writeComment(@RequestParam("taskId") int taskId,
											  @RequestParam("commentContent") String commentContent,
											  Model theModel, HttpServletRequest req) throws ParseException {
		Users theUser = userService.getUser(req.getUserPrincipal().getName());
		Tasks theTask = listService.getTask(taskId);
		Comments comment = new Comments();
		comment.setComment(commentContent);
		comment.setTask(theTask);
		comment.setWrittenBy(theUser.getUsername());
		
//		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
//		comment.setCommentedat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(timeStamp));
		comment.setCommentedat(GokyurUtilities.getNow());
		
		listService.addComment(comment);
		
		return listService.getAllCommentsOf(taskId);
	}
	
	@RequestMapping(value="getAllComments", method=RequestMethod.POST)
	public @ResponseBody List<Comments> getAllComments(@RequestParam("taskId") int id, HttpServletRequest req, HttpServletResponse resp) throws IOException{
		List<Comments> tempComments = listService.getTask(id).getComments();
		return tempComments;
	}
	
	@RequestMapping(value="/saveTaskNotes", method=RequestMethod.POST)
	public @ResponseBody void saveNotesOfTask(@RequestParam("taskId") int taskId, @RequestParam("taskNotes") String taskNotes, HttpServletRequest req, HttpServletResponse resp) {
		Tasks theTask = listService.getTask(taskId);
		theTask.setNotes(taskNotes);
		listService.addTask(theTask);
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
	
	@RequestMapping(value="/removeSharedUser", method=RequestMethod.POST)
	public @ResponseBody void removeSharedUser(@RequestParam("listId") int id, @RequestParam("userName") String userName, HttpServletRequest req, HttpServletResponse resp) {
		SharedLists sharedList = listService.getSharedList(id,userService.getUser(userName).getId());
		listService.removeSharedList(sharedList);
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
