package com.gokyur.dao;

import com.gokyur.entity.Roles;

public interface RoleDAO {

	public Roles findByName(String name);

	public void saveRole(Roles role);

}
