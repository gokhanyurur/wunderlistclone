package com.gokyur.entity;

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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="Lists")
public class Lists {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(length=75, nullable=false, name="listName")
	private String listName;
	
	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(nullable=false, name="userId")
	private Users owner;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="list", cascade=CascadeType.ALL)
	private List<Tasks> tasks;
		
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="sharedWith", cascade=CascadeType.ALL)
	private List<SharedLists> sharedUsers;
		
	public Lists() {
		
	}

	public Lists(String listName) {
		this.listName = listName;
	}
	
	public Lists(String listName, Users owner) {
		this.listName = listName;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public Users getOwner() {
		return owner;
	}

	public void setOwner(Users owner) {
		this.owner = owner;
	}

	public List<Tasks> getTasks() {
		return tasks;
	}

	public void setTasks(List<Tasks> tasks) {
		this.tasks = tasks;
	}


	public List<SharedLists> getSharedUsers() {
		return sharedUsers;
	}

	public void setSharedUsers(List<SharedLists> sharedUsers) {
		this.sharedUsers = sharedUsers;
	}
	
	
	
}
