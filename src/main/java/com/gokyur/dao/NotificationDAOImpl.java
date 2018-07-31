package com.gokyur.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gokyur.entity.NotificationTypes;
import com.gokyur.entity.Notifications;

@Repository
@Transactional
public class NotificationDAOImpl implements NotificationDAO {

	@Autowired
	public SessionFactory sessionFactory;
	
	public void saveNotification(Notifications notification) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(notification);
	}

	public List<Notifications> getAllNotifications(int userId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery=currentSession.createQuery("FROM Notifications WHERE userid=:uid");
		theQuery.setParameter("uid", userId);
		
		@SuppressWarnings("unchecked")
		List<Notifications> tempList = theQuery.list();
		
		return tempList;
	}

	public boolean isNotificationsConfigured() {
		boolean configured = false;
		boolean reminderTypeOK = false;
		boolean infoTypeOK = false;
		
		Session currentSession = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<NotificationTypes> allNotificationTypes =(List<NotificationTypes>) currentSession.createQuery("from NotificationTypes").list();
		
		for(NotificationTypes notifTypes:allNotificationTypes) {
			if(notifTypes.getType().equals("REMINDER")) {
				reminderTypeOK=true;
			}
			if(notifTypes.getType().equals("INFO")) {
				infoTypeOK=true;
			}
		}
		if(reminderTypeOK && infoTypeOK) {
			configured = true;
		}
		
		return configured;	
	}

	public void saveNotificationType(NotificationTypes type) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(type);
	}

	public NotificationTypes getType(String type) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query theQuery=currentSession.createQuery("from NotificationTypes where type=:notifyType");
		theQuery.setParameter("notifyType", type);
		NotificationTypes tempType = (NotificationTypes) theQuery.uniqueResult();
		return tempType;
	}
	

}
