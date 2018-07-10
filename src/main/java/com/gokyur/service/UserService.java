package com.gokyur.service;

import java.util.List;

import com.gokyur.entity.Users;

public interface UserService{

	public void saveUser(Users theUser);
	
	public Users getUser(int id);
	
	public List<Users> getAllUsers();


}
