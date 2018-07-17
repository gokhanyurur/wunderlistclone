package com.gokyur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokyur.dao.RoleDAO;
import com.gokyur.entity.Roles;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;
	
	@Transactional
	public Roles findByName(String name) {
		return roleDAO.findByName(name);
	}

	@Transactional
	public void saveRole(Roles role) {
		roleDAO.saveRole(role);
	}

	@Transactional
	public boolean isRolesConfigured() {
		return roleDAO.isRolesConfigured();
	}

}
