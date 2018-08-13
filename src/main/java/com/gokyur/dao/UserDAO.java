package com.gokyur.dao;

import java.util.List;

import com.gokyur.entity.SharedLists;
import com.gokyur.entity.Users;

public interface UserDAO {
	public void saveUser(Users theUser);

	public Users getUser(int id);
	public Users getUser(String username);
	public Users getUserByEmail(String email);

	public List<Users> getAllUsers();
	
	public List<SharedLists> getSharedLists(int listId);
	public List<SharedLists> getAllSharedLists();


	
}
