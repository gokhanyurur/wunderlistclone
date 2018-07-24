package com.gokyur.dao;

import java.util.List;

import com.gokyur.entity.Comments;
import com.gokyur.entity.Lists;
import com.gokyur.entity.SharedLists;
import com.gokyur.entity.SubTasks;
import com.gokyur.entity.Tasks;

public interface ListDAO {

	public void createList(Lists theList);
	public Lists getList(int id);
	public void removeList(Lists theList);
	
	public void addTask(Tasks theTask);
	public void removeTask(Tasks tempTask);
	public Tasks getTask(int id);
	
	public void addSubTask(SubTasks theSubTask);
	public SubTasks getSubTask(int id);
	public void removeSubTask(SubTasks theSubTask);
	
	public void addComment(Comments theComment);
	
	public void shareList(SharedLists theSharedList);
	
	public List<Comments> getAllCommentsOf(int taskId);



		
}
