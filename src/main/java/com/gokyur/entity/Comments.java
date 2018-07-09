package com.gokyur.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="comments")
public class Comments {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@OneToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(nullable=false, name="taskId")
	private Tasks commentedForTask;
	
	@Column(length=250, nullable=false, name="comment")
	private String comment;

	public Comments() {
		
	}

	public Comments(Tasks commentedForTask, String comment) {
		this.commentedForTask = commentedForTask;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Tasks getTask() {
		return commentedForTask;
	}

	public void setTask(Tasks commentedForTask) {
		this.commentedForTask = commentedForTask;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	

}
