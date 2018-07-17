package com.gokyur.entity;

import javax.persistence.*;



@Entity
@Table(name="roles")
public class Roles {
  
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private int id;
     
    @Column(name="rolename")
    private String role;
     
    public Roles() {
    	
    }

    public Roles(String role) {
		this.role = role;
	}
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
   
}
