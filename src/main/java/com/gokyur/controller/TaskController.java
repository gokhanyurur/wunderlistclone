package com.gokyur.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gokyur.entity.Comments;
import com.gokyur.entity.Lists;
import com.gokyur.entity.SharedLists;
import com.gokyur.entity.SubTasks;
import com.gokyur.entity.Tasks;
import com.gokyur.entity.Users;
import com.gokyur.service.ListService;
//import com.gokyur.service.NotificationService;
//import com.gokyur.service.RoleService;
import com.gokyur.service.UserService;
import com.gokyur.utilities.GokyurUtilities;

@Controller
public class TaskController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ListService listService;
	
//	@Autowired
//    private RoleService roleService;
//	
//	@Autowired
//	private NotificationService notificationService;

	@RequestMapping(value = "/searchTask", method = RequestMethod.POST)
	public @ResponseBody  Map<String, Object> searchTask(@RequestParam("searchText") String searchText, HttpServletRequest req, HttpServletResponse resp) {
		Users loginedUser = userService.getUser(req.getUserPrincipal().getName());
		
		List<Lists> userLists = loginedUser.getLists();
		List<Tasks> resultList = new ArrayList<Tasks>();
		
		List<String> names = new ArrayList<String>();
		List<Integer> ids = new ArrayList<Integer>();
		List<List<Tasks>> lists = new ArrayList<List<Tasks>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		for(Lists theList:userLists) {
			resultList = new ArrayList<Tasks>();
			for(Tasks theTask: theList.getTasks()) {
				if(theTask.getTask().trim().toLowerCase().contains(searchText.trim().toLowerCase())) {
					resultList.add(theTask);
				}
			}
			if(resultList.size()>0) {
				names.add(theList.getListName());
				lists.add(resultList);
				ids.add(theList.getId());
			}
		}
		
		//CHECK SHARED LISTS
		List<SharedLists> sharedLists = userService.getAllSharedLists();
		if(sharedLists != null) {
			for(SharedLists sharedList: sharedLists) {
				Lists tempList = listService.getList(sharedList.getSharedList());
				resultList = new ArrayList<Tasks>();
				if(sharedList.getSharedWith().getId() == loginedUser.getId()) {
					for(Tasks theTask: tempList.getTasks()) {
						if(theTask.getTask().trim().toLowerCase().contains(searchText.trim().toLowerCase())) {
							resultList.add(theTask);
						}
					}
					if(resultList.size()>0) {
						names.add(tempList.getListName()+" (Shared list)");
						lists.add(resultList);
						ids.add(tempList.getId());
					}
				}

			}
		}

		map.put("Names", names);
		map.put("Lists", lists);
		map.put("Ids", ids);
		return map;
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
}
