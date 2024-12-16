package ru.skillbox.notification_sender;

import ru.skillbox.notification.Notification;
import ru.skillbox.notification.SmsNotification;

import java.util.List;

public class SMSNotificationSender implements NotificationSender<Notification> {

    @Override
    public void send(Notification notification) {
        if (notification instanceof SmsNotification) {
            SmsNotification smsNotification = (SmsNotification) notification;
            StringBuilder receivers = new StringBuilder();
            receivers.append("Receivers: ");
            for (String item : smsNotification.getPhoneList()) {
                receivers.append(item).append(", ");
            }
            receivers.setLength(receivers.length()-2);
            System.out.println(receivers);
        }
    }

    @Override
    public void send(List<Notification> notifications) {
        if (!notifications.isEmpty()) {
            for (Notification item : notifications) {
                send(item);
            }
        }
    }
}
