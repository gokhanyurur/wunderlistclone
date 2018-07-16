package com.gokyur.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="roles")
public class Roles {
  
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
    private int id;
    
	@OneToOne
    @JoinColumn(name="userId")	
    private Users roledUser; 
 
    @Column(name="role")
    private String role;
     
    public Roles() {
    	
    }

    public Roles(String role) {
		this.role = role;
	}
    
	public Roles(Users roledUser, String role) {
		this.roledUser = roledUser;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonIgnore
	public Users getUser() {
		return roledUser;
	}

	@JsonIgnore
	public void setUser(Users roledUser) {
		this.roledUser = roledUser;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
   
}
