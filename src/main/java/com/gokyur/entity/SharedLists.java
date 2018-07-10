package com.gokyur.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SharedList")
public class SharedLists {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
		
	
	//@JoinColumn(nullable=false, name="sharedListId")
	@Column(name="sharedListId")
	private int sharedList;
	
	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(nullable=false ,name="sharedUserId")
	private Users sharedWith;
		
	public SharedLists() {
		
	}

	public SharedLists(int sharedList, Users sharedWith) {
		this.sharedList = sharedList;
		this.sharedWith = sharedWith;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSharedList() {
		return sharedList;
	}

	public void setSharedListId(int sharedList) {
		this.sharedList = sharedList;
	}

	public Users getSharedWith() {
		return sharedWith;
	}

	public void setSharedWith(Users sharedWith) {
		this.sharedWith = sharedWith;
	}

	public void setSharedList(int sharedList) {
		this.sharedList = sharedList;
	}

	
	
}
