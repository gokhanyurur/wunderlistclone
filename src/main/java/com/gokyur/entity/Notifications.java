package com.gokyur.entity;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="notifications")
public class Notifications {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(nullable=false ,name="userid")
	private Users user;
	
	@Column(nullable=false, name="notification")
	private String notification;
	
	@ManyToOne
	@JoinColumn(nullable=false, name="typeid")
	private NotificationTypes type;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
	@Column(nullable=false, name="notifiedat")
	protected Date notifiedat;
	
	@Column(name="viewed")
	private boolean viewed;
	
	public Notifications() {
		
	}

	public Notifications(Users user, String notification, NotificationTypes type) {
		this.user = user;
		this.notification = notification;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public NotificationTypes getType() {
		return type;
	}

	public void setType(NotificationTypes type) {
		this.type = type;
	}

	public Date getNotifiedat() {
		return notifiedat;
	}

	public void setNotifiedat(Date notifiedat) {
		this.notifiedat = notifiedat;
	}

	public boolean isViewed() {
		return viewed;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}
	
	
	
	
}
