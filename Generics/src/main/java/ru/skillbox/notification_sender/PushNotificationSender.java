package ru.skillbox.notification_sender;

import ru.skillbox.notification.Notification;
import ru.skillbox.notification.PushNotification;

import java.util.List;

public class PushNotificationSender implements NotificationSender<Notification> {

    @Override
    public void send(Notification notification) {
        if (notification instanceof PushNotification) {
            PushNotification pushNotification = (PushNotification) notification;
            System.out.println("Message: " + pushNotification.formattedMessage());
            System.out.println("Subject: "+ pushNotification.getSubject());
            System.out.println("Receivers: " + pushNotification.getUserAccount());
        }
    }

    @Override
    public void send(List<Notification> notifications) {
        for (Notification item : notifications) {
            send(item);
        }
    }
}
