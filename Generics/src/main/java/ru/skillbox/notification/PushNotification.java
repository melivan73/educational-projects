package ru.skillbox.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PushNotification implements Notification {
    public final static String EMOJI_CODE = "\ud83d\udc4b";
    private final String text;
    private final String subject;
    private final String userAccount;

    @Override
    public String formattedMessage() {
        return EMOJI_CODE + " " + text;
    }
}
