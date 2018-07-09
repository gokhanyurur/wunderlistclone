package com.gokyur.service;

import com.gokyur.entity.Users;

public interface UserService{

	public void saveUser(Users theUser);
	
	public Users getUser(int id);


}
