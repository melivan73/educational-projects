package subway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class SubwayFromHTMLSaver {
    private HashMap<String, List<String>> stations;
    private List<Line> lines;

    public SubwayFromHTMLSaver(List<Line> lines, List<Station> stationsNative) {
        this.stations = new LinkedHashMap<>();
        this.lines = lines;
        prepareObjects(stationsNative);
    }

    public void saveJsonFile(String path) {
        String newJson = "";
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);;
        try {
            newJson = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write(newJson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareObjects(List<Station> stationsNative) {
        lines = lines.stream()
                    .sorted(Comparator.comparingInt(Line::getNum))
                    .toList();
        for (Line line : lines) {
            List<String> lineStations = stationsNative.stream()
                    .filter(station -> station.getLineNum().equals(line.getNumber()))
                    .flatMap(station -> Stream.of(station.getName()))
                    .toList();
            stations.put(line.getNumber(), lineStations);
        }
    }
}
