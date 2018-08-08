package com.gokyur.entity;

import javax.persistence.*;

@Entity
@Table(name="notificationtypes")
public class NotificationTypes {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private int id;
     
    @Column(name="type")
    private String type;

    public NotificationTypes() {
    	
    }
    
	public NotificationTypes(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
