package ru.skillbox.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SmsNotification implements Notification {
    private final String text;
    private final List<String> phoneList;

    @Override
    public String formattedMessage() {
        return text;
    }
}
