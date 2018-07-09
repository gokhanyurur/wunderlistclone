package com.gokyur.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="users")
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(length=50, name="fullname")
	private String fullname;
	
	@Column(length=30, name="username")
	private String username;
	
	@Column(length=25, name="password")
	private String password;
		
	@Column(length=30, name="email")
	private String email;
			
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	protected Date createdAt;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
	private List<Lists> lists;
		
	public Users() {
		
	}

	public Users(String username, String password, String email, Date createdAt) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<Lists> getLists() {
		return lists;
	}

	public void setLists(List<Lists> lists) {
		this.lists = lists;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", fullname=" + fullname + ", username=" + username + ", password=" + password
				+ ", email=" + email + ", createdAt=" + createdAt + ", lists=" + lists + "]";
	}
	
	
				
}