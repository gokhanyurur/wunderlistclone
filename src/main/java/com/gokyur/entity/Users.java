package com.gokyur.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="users")
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(length=50, name="fullname")
	private String fullname;
	
	@Column(length=30, nullable=false, unique=true, name="username")
	private String username;
	
	@Column(nullable=false, name="password")
	private String password;
	
	@Transient
	private String passwordConf;
		
	@Column(length=30, nullable=false, name="email")
	private String email;
	
	@Transient
	private String emailConf;
			
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
	@Column(nullable=false, name="createdat")
	protected Date createdAt;
	
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonIgnore
	private List<Lists> lists = new ArrayList<Lists>(0);
	
	@LazyCollection(LazyCollectionOption.FALSE) //True
	@OneToMany(mappedBy="sharedWith", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<SharedLists> sharedUsers;
			
	@ManyToOne
	@JoinColumn(nullable=false, name="role_id")
	private Roles role;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Notifications> notifications;
			
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
	
	public List<SharedLists> getSharedUsers() {
		return sharedUsers;
	}

	public void setSharedUsers(List<SharedLists> sharedUsers) {
		this.sharedUsers = sharedUsers;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public List<Notifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notifications> notifications) {
		this.notifications = notifications;
	}

	public String getPasswordConf() {
		return passwordConf;
	}

	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}

	public String getEmailConf() {
		return emailConf;
	}

	public void setEmailConf(String emailConf) {
		this.emailConf = emailConf;
	}

	
				
}