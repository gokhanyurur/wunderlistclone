package com.gokyur.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tasks")
public class Tasks {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(nullable=false, name="task")
	private String task;
	
	@Column(name="stared")
	private boolean stared;
	
	@Column(name="completed")
	private boolean completed;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
	@Column(nullable=false, name="createdat")
	private Date createdat;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
	@Column(name="lastdate")
	private Date lastdate;
	
	@Column(length=250, name="notes")
	private String notes;
	
	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(nullable=false, name="listId")
	@JsonIgnore
	private Lists list;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="commentedForTask", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Comments> comments;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="belongsToTask", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<SubTasks> subTasks;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="task", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Notifications> notifications;
	
	
	public Tasks() {
		
	}

	public Tasks(String task) {
		this.task = task;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public boolean isStared() {
		return stared;
	}

	public void setStared(boolean stared) {
		this.stared = stared;
	}
	
	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Lists getList() {
		return list;
	}

	public void setList(Lists list) {
		this.list = list;
	}

	public List<SubTasks> getSubTasks() {
		return subTasks;
	}

	public void setSubTasks(List<SubTasks> subTasks) {
		this.subTasks = subTasks;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public List<Notifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notifications> notifications) {
		this.notifications = notifications;
	}

	
	
}
