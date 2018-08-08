package com.gokyur.dao;

import java.util.List;

import com.gokyur.entity.NotificationTypes;
import com.gokyur.entity.Notifications;

public interface NotificationDAO {

	public void saveNotification(Notifications notification);

	public List<Notifications> getAllNotifications(int userId);

	public boolean isNotificationsConfigured();

	public void saveNotificationType(NotificationTypes type);

	public NotificationTypes getType(String type);

	public List<Notifications> getAllNotifications();

}
