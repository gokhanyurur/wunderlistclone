package com.gokyur.service;

import com.gokyur.entity.Roles;

public interface RoleService {

	public Roles findByName(String name);

	public void saveRole(Roles role);
}
