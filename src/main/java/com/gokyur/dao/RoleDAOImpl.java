package com.gokyur.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gokyur.entity.Roles;

@Repository
@Transactional
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

	public boolean isRolesConfigured() {
		boolean configured = false;
		boolean userRoleOK = false;
		boolean adminRoleOK = false;
		Session currentSession = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Roles> allRoles =(List<Roles>) currentSession.createQuery("from Roles").list();
		for(Roles role:allRoles) {
			if(role.getRole().equals("ROLE_USER")) {
				userRoleOK=true;
			}
			if(role.getRole().equals("ROLE_ADMIN")) {
				adminRoleOK=true;
			}
		}
		if(userRoleOK && adminRoleOK) {
			configured = true;
		}
		
		return configured;	
	}

}
