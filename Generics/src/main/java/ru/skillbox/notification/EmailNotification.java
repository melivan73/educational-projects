package ru.skillbox.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EmailNotification implements Notification {
    static final String TAG_OPEN = "<p>";
    static final String TAG_CLOSE = "</p>";

    private final String text;
    private final String subject;
    private final List<String> emailList;

    @Override
    public String formattedMessage() {
        return TAG_OPEN + text + TAG_CLOSE;
    }
}
