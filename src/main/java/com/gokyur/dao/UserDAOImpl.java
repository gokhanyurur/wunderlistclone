package com.gokyur.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Session;

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

	
}
