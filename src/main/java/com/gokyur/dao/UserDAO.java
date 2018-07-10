package com.gokyur.dao;

import java.util.List;

import com.gokyur.entity.Users;

public interface UserDAO {
	public void saveUser(Users theUser);

	public Users getUser(int id);

	public List<Users> getAllUsers();


}
