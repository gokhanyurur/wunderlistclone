package com.gokyur.websocket;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.gokyur.entity.NotificationTypes;
import com.gokyur.entity.Notifications;
import com.gokyur.entity.Users;
import com.gokyur.service.NotificationService;
import com.gokyur.service.UserService;
import com.gokyur.utilities.GokyurUtilities;


@ServerEndpoint(value = "/ws", configurator = SpringConfigurator.class)
public class WebSocketServer {

	private Session session;
	
	@Autowired
	NotificationService notificationService;

	@Autowired
	UserService userService;
	
	@OnOpen
	public void connect(final Session session) {
		this.session = session;
	
		//System.out.println(session.getId());
		if(!notificationService.isNotificationsConfigured()) {
			notificationService.saveNotificationType(new NotificationTypes("REMINDER"));
			notificationService.saveNotificationType(new NotificationTypes("INFO"));
		}
		
		Timer timer = new Timer(); 
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					Users user = userService.getUser(session.getUserPrincipal().getName());
					List<Notifications> allNotifs = user.getNotifications();
					checkTheNotifications(allNotifs);
				} catch (ParseException e) {
					//e.printStackTrace();
				}
			}

//			private void checkTheTasks(List<Lists> lists) throws ParseException {
//				for(Lists thelist: lists) {
//					for(Tasks theTask: thelist.getTasks()) {
//						if(theTask.getLastdate() != null) {
//							if(checkTime(theTask.getLastdate()) ==0) {
//								Users user = userService.getUser(session.getUserPrincipal().getName());
//								NotificationTypes type = notificationService.getType("REMINDER");
//								Notifications notif = new Notifications(user, theTask.getTask(), type);
//								notif.setNotifiedat(GokyurUtilities.getNow());
//								notificationService.saveNotification(notif);
//								message(theTask.getTask());
//							}
//						}
//					}
//				}
//			}
			
			private void checkTheNotifications(List<Notifications> notifs) throws ParseException {
				for(Notifications tempNotif: notifs) {
					if(GokyurUtilities.checkTime(tempNotif.getLastdate()) == 0 && !tempNotif.isNotified()) {
						
						//SERVER
//						tempNotif.setNotifiedat(GokyurUtilities.convertLocalDateTimeToServer(GokyurUtilities.getNow().toString()));
						
						//LOCAL
						tempNotif.setNotifiedat(GokyurUtilities.getNow());
						
						tempNotif.setNotified(true);
						notificationService.saveNotification(tempNotif);
						message(tempNotif.getTask().getTask());
					}
				}
			}

			
			
		}, 0, 1*(1000*1));
	}

	@OnClose
	public void close() {
		this.session = null;
	}
	
	@OnMessage
	public void message(String message) {
		System.out.println("Message = "+message);		
		this.session.getAsyncRemote().sendText(message);
	}
	
}