package com.gokyur.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.gokyur.entity.SharedLists;
import com.gokyur.entity.Users;

@Service
public class UserDAOImpl implements UserDAO{

	@Autowired
	public SessionFactory sessionFactory;
	
	public void saveUser(Users theUser) {	
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(theUser);
	}

	public Users getUser(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Users tempUser = (Users) currentSession.get(Users.class, id);
		return tempUser;
	}

	public List<Users> getAllUsers() {
		Session currentSession = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Users> allUsers =(List<Users>) currentSession.createQuery("from Users").list();
		return allUsers;
	}

	public List<SharedLists> getSharedLists(int listId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery=currentSession.createQuery("FROM SharedLists WHERE shareduserid=:listid");
		theQuery.setParameter("listid", listId);
		
		@SuppressWarnings("unchecked")
		List<SharedLists> tempList = theQuery.list();
		
		return tempList;
	}

	public List<SharedLists> getAllSharedLists() {
		Session currentSession = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<SharedLists> allSharedLists =(List<SharedLists>) currentSession.createQuery("from SharedLists").list();
		return allSharedLists;
	}

	
}
