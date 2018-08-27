package com.gokyur.service;

import java.util.List;

import com.gokyur.entity.Comments;
import com.gokyur.entity.Lists;
import com.gokyur.entity.SharedLists;
import com.gokyur.entity.SubTasks;
import com.gokyur.entity.Tasks;

public interface ListService {

	public void addTask(Tasks theTask);
	public void removeTask(Tasks tempTask);
	public Tasks getTask(int id);
	public List<Tasks> getTasksByList(int id);
	
	public void createList(Lists theList);
	public void removeList(Lists theList);
	public Lists getList(int id);
	
	public void addSubTask(SubTasks theSubTask);
	public void removeSubTask(SubTasks theSubTask);
	public SubTasks getSubTask(int id);

	
	public void addComment(Comments theComment);
	public void removeComment(Comments theComment);
	public Comments getComment(int id);
	
	public void shareList(SharedLists theSharedList);
	public void removeSharedList(SharedLists sharedList);
	public SharedLists getSharedList(int lid, int uid);
	
	
	public List<Comments> getAllCommentsOf(int taskId);





	
	
	
}
