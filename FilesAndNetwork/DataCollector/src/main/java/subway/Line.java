package subway;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Line {
    private final String name;
    private final String number;
    private int num;

    public Line(String name, String number) {
        this.name = name;
        this.number = number;
        this.num = 0;

        try {
            num = Integer.parseInt(this.number);
        } catch (NumberFormatException ignored) {
            this.number.chars().forEach(value -> num += value);
        }
    }
}
