package com.gokyur.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gokyur.entity.Comments;
import com.gokyur.entity.Lists;
import com.gokyur.entity.SharedLists;
import com.gokyur.entity.SubTasks;
import com.gokyur.entity.Tasks;

@Repository
@Transactional
public class ListDAOImpl implements ListDAO{

	@Autowired
	public SessionFactory sessionFactory;
		
	public Lists getList(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Lists tempList = (Lists) currentSession.get(Lists.class, id);
			
		return tempList;
	}

	public void addTask(Tasks theTask) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theTask);
	}
	
	public void createList(Lists theList) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theList);
	}

	public Tasks getTask(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Tasks tempTask = (Tasks) currentSession.get(Tasks.class, id);
		return tempTask;
	}

	public void addSubTask(SubTasks theSubTask) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theSubTask);
	}

	public SubTasks getSubTask(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		SubTasks tempSubTask = (SubTasks) currentSession.get(SubTasks.class, id);
		return tempSubTask;
	}

	public void addComment(Comments theComment) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theComment);
	}

	public void shareList(SharedLists theSharedList) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theSharedList);
	}

	public void removeSubTask(SubTasks theSubTask) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.delete(theSubTask);
	}

	public void removeTask(Tasks tempTask) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.delete(tempTask);
	}

	public List<Comments> getAllCommentsOf(int taskId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery=currentSession.createQuery("FROM Comments WHERE taskId=:taskid");
		theQuery.setParameter("taskid", taskId);
		
		@SuppressWarnings("unchecked")
		List<Comments> tempList = theQuery.list();
		
		return tempList;
	}

	public void removeList(Lists theList) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.delete(theList);
	}

	
	public void removeSharedList(SharedLists sharedList) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.delete(sharedList);
	}

	public SharedLists getSharedList(int lid, int uid) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery=currentSession.createQuery("FROM SharedLists WHERE sharedlistid=:listid AND shareduserid=:userid");
		theQuery.setParameter("listid", lid);
		theQuery.setParameter("userid", uid);
		
		SharedLists tempSharedList = (SharedLists) theQuery.uniqueResult();
		
		return tempSharedList;
	}

	public void removeComment(Comments theComment) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.delete(theComment);
	}

	public Comments getComment(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery=currentSession.createQuery("FROM Comments WHERE id=:commentid");
		theQuery.setParameter("commentid", id);
		
		Comments tempComment = (Comments) theQuery.uniqueResult();
		
		return tempComment;
	}

	public List<Tasks> getTasksByList(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery=currentSession.createQuery("FROM Tasks WHERE listid=:lid ORDER BY id");
		theQuery.setParameter("lid", id);
		
		@SuppressWarnings("unchecked")
		List<Tasks> tempList = theQuery.list();
		
		return tempList;
	}

}
