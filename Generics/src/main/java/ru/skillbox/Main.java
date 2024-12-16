package ru.skillbox;

import ru.skillbox.notification.EmailNotification;
import ru.skillbox.notification.Notification;
import ru.skillbox.notification.PushNotification;
import ru.skillbox.notification.SmsNotification;
import ru.skillbox.notification_sender.EmailNotificationSender;
import ru.skillbox.notification_sender.PushNotificationSender;
import ru.skillbox.notification_sender.SMSNotificationSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] emailArray = {"oleg@java.skillbox.ru", "masha@java.skillbox.ru", "yan@java.skillbox.ru"};
        List<String> emailList = Arrays.asList(emailArray);
        System.out.println("EMAIL");
        EmailNotification emailNotification = new EmailNotification("Успешная регистрация",
                "Тема письма", emailList);
        EmailNotificationSender emailNotificationSender = new EmailNotificationSender();
        emailNotificationSender.send(emailNotification);

        String[] phoneArray = {"+70001234567", "+79161234567"};
        List<String> phoneList = Arrays.asList(phoneArray);
        System.out.println("SMS");
        SmsNotification smsNotification = new SmsNotification("Спасибо за регистрацию на сервисе!",
                phoneList);
        SMSNotificationSender smsNotificationSender = new SMSNotificationSender();
        smsNotificationSender.send(smsNotification);

        System.out.println("PUSH");
        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(new PushNotification("Спасибо за регистрацию на сервисе!",
                "Успешная регистрация!", "a.savanov"));
        notifications.add(new PushNotification("Спасибо за регистрацию на сервисе!",
                "Успешная регистрация!", "o.yanovich"));

        PushNotificationSender pushNotificationSender = new PushNotificationSender();
        pushNotificationSender.send(notifications);
    }
}