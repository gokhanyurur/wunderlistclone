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

import com.gokyur.entity.Lists;
import com.gokyur.entity.NotificationTypes;
import com.gokyur.entity.Notifications;
import com.gokyur.entity.Tasks;
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
					checkTheTasks(user.getLists());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			private void checkTheTasks(List<Lists> lists) throws ParseException {
				for(Lists thelist: lists) {
					for(Tasks theTask: thelist.getTasks()) {
						if(theTask.getLastdate() != null) {
							if(checkTime(theTask.getLastdate()) >=-30000 && checkTime(theTask.getLastdate()) <=0) {
								Users user = userService.getUser(session.getUserPrincipal().getName());
								NotificationTypes type = notificationService.getType("REMINDER");
								Notifications notif = new Notifications(user, theTask.getTask(), type);
								notif.setNotifiedat(GokyurUtilities.getNow());
								notificationService.saveNotification(notif);
								message(theTask.getTask());
							}
						}
					}
				}
			}

			private long checkTime(Date lastdate) throws ParseException {
				
//				String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
//		        Date nowDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(timeStamp);

				Date nowDate = GokyurUtilities.getNow();
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date lastd = lastdate;
				
				DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				Date date = inputFormat.parse(lastd.toString());

				// Format date into output format
				DateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String outputString = outputFormat.format(date);
				
				lastd = format.parse(outputString);
				
				long difference = nowDate.getTime() - lastd.getTime(); 
				
				//System.out.println("Time difference is "+difference);
				
				return difference;
			}
			
		}, 0, 30*(1000*1));
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