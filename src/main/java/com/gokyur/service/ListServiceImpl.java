package com.gokyur.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokyur.dao.ListDAO;
import com.gokyur.entity.Comments;
import com.gokyur.entity.Lists;
import com.gokyur.entity.SharedLists;
import com.gokyur.entity.SubTasks;
import com.gokyur.entity.Tasks;

@Service
public class ListServiceImpl implements ListService{

	@Autowired
	private ListDAO listDAO;
	
	@Transactional
	public Lists getList(int id) {
		return listDAO.getList(id);
	}

	@Transactional
	public void addTask(Tasks theTask) {
		listDAO.addTask(theTask);
	}

	@Transactional
	public Tasks getTask(int id) {
		return listDAO.getTask(id);
	}

	@Transactional
	public void createList(Lists theList) {
		listDAO.createList(theList);
	}

	@Transactional
	public void addSubTask(SubTasks theSubTask) {
		listDAO.addSubTask(theSubTask);
	}

	@Transactional
	public SubTasks getSubTask(int id) {
		return listDAO.getSubTask(id);
	}

	@Transactional
	public void addComment(Comments theComment) {
		listDAO.addComment(theComment);
	}

	@Transactional
	public void shareList(SharedLists theSharedList) {
		listDAO.shareList(theSharedList);
	}

}