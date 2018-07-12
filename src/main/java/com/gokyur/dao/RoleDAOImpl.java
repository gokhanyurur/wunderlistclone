package com.gokyur.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gokyur.entity.Roles;

@Service
public class RoleDAOImpl implements RoleDAO {

	@Autowired
	public SessionFactory sessionFactory;
	
	public Roles findByName(String name) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery=currentSession.createQuery("from Roles where role=:roleName");
		theQuery.setParameter("roleName", name);
		Roles tempRole = (Roles) theQuery.uniqueResult();
		return tempRole;
	}

	public void saveRole(Roles role) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(role);
	}

}
