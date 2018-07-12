package com.gokyur.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokyur.dao.UserDAO;
import com.gokyur.entity.SharedLists;
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
	
	@Transactional
	public Users getUser(String username) {
		return userDAO.getUser(username);
	}

	@Transactional
	public List<Users> getAllUsers() {
		return userDAO.getAllUsers();
	}

	@Transactional
	public List<SharedLists> getSharedLists(int listId) {
		return userDAO.getSharedLists(listId);
	}

	@Transactional
	public List<SharedLists> getAllSharedLists() {
		return userDAO.getAllSharedLists();
	}

}
