package com.gokyur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokyur.dao.UserDAO;
import com.gokyur.entity.Users;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;
	
	@Transactional
	public void saveUser(Users theUser) {
		userDAO.saveUser(theUser);
		
	}

	@Transactional
	public Users getUser(int id) {
		return userDAO.getUser(id);
	}

	

}
