package subway;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Station {
    private String name;
    private int num;
    private String lineNum;
    private String openDate;
    private String depth;

    public Station(String name, int num, String lineNum, String openDate, String depth) {
        this.name = name;
        this.num = num;
        this.lineNum = lineNum;
        this.openDate = openDate;
        this.depth = depth;
    }

    /**
    * constructor for WebPageParser
     */
    public Station(String name, int num, String lineNum) {
        this.name = name;
        this.num = num;
        this.lineNum = lineNum;
        this.openDate = null;
        this.depth = null;
    }
}