package com.gokyur.service;

import java.util.List;

import com.gokyur.entity.SharedLists;
import com.gokyur.entity.Users;

public interface UserService{

	public void saveUser(Users theUser);
	
	public Users getUser(int id);
	public Users getUser(String username);
	public Users getUserByEmail(String email);
	
	public List<Users> getAllUsers();
	
	public List<SharedLists> getAllSharedLists();
	public List<SharedLists> getSharedLists(int listId);






}
