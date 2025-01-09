package subway;

import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Getter
public class WebPageParser {
    public final static String LINE_NAME_CLASS = "span.js-metro-line";
    public final static String LINE_NUM_ATTR = "data-line";
    public final static String STATION_PARENT_CLASS = "div.js-depend";
    public final static String STATION_PARENT_LINE_ATTR = "data-depend-set";
    public final static String STATION_PARENT_LINE_ATTR_PREFIX = "lines-";
    public final static String STATION_CLASS ="p.single-station";
    public final static String STATION_NAME = "span.name";
    public final static String STATION_NUM = "span.num";

    private final String rawHTML;
    private final Document document;
    private final List<Line> lines;
    private final List<Station> stations;

    public WebPageParser(String path) {
        lines = new ArrayList<>();
        stations = new ArrayList<>();
        rawHTML = fileToString(path);
        document = Jsoup.parse(rawHTML);
    }

    public void getAllData() {
        Elements elements = document.select(LINE_NAME_CLASS);
        for (Element element : elements) {
            Line line = new Line(element.text(), element.attr(LINE_NUM_ATTR));
            lines.add(line);
            getAllStation(element.attr(LINE_NUM_ATTR));
        }
    }

    private void getAllStation(String lineNum) {
        Elements parentElements = document.select(STATION_PARENT_CLASS);
        for (Element parElem : parentElements) {
            if (parElem.attr(STATION_PARENT_LINE_ATTR).equals(STATION_PARENT_LINE_ATTR_PREFIX + lineNum)) {
                Elements stationElements = parElem.select(STATION_CLASS);
                for (Element elem : stationElements) {
                    Station station = new Station(
                            elem.select(STATION_NAME).text(),
                            Integer.parseInt(elem.select(STATION_NUM).text().replace(".", "")),
                            lineNum);
                    stations.add(station);
                }
            }
        }
    }

    private String fileToString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> builder.append(line).append("\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}