package ru.skillbox.notification_sender;

import ru.skillbox.notification.EmailNotification;
import ru.skillbox.notification.Notification;

import java.util.List;

public class EmailNotificationSender implements NotificationSender<Notification> {

    @Override
    public void send(Notification notification) {
        if (notification instanceof EmailNotification) {
            EmailNotification emailNotification = (EmailNotification) notification;
            System.out.println("Message: " + emailNotification.formattedMessage());
            System.out.println("Subject: "+ emailNotification.getSubject());
            StringBuilder emails = new StringBuilder();
            emails.append("Receivers: ");
            for (String item : emailNotification.getEmailList()) {
                emails.append(item).append(", ");
            }
            emails.setLength(emails.length()-2);
            System.out.println(emails);
        }
    }

    @Override
    public void send(List<Notification> notifications) {
        for (Notification item : notifications) {
            send(item);
        }
    }
}
