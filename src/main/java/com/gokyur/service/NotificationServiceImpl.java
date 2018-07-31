package com.gokyur.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokyur.dao.NotificationDAO;
import com.gokyur.entity.NotificationTypes;
import com.gokyur.entity.Notifications;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationDAO notificationDAO;
	
	@Transactional
	public void saveNotification(Notifications notification) {
		notificationDAO.saveNotification(notification);
	}

	@Transactional
	public List<Notifications> getAllNotifications(int userId) {
		return notificationDAO.getAllNotifications(userId);
	}

	@Transactional
	public boolean isNotificationsConfigured() {
		return notificationDAO.isNotificationsConfigured();
	}

	@Transactional
	public void saveNotificationType(NotificationTypes type) {
		notificationDAO.saveNotificationType(type);
	}

	@Transactional
	public NotificationTypes getType(String type) {
		return notificationDAO.getType(type);
	}

}
