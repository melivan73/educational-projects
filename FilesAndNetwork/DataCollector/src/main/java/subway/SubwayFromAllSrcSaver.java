package subway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import files.CSVFileParser;
import files.JSONFileParser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("FieldMayBeFinal")
@Getter
public class SubwayFromAllSrcSaver {
    public static final String DEFAULT_DATE_VAL = "";
    public static final int DEFAULT_DEPTH_VAL = 0;

    private List<Line> lines;
    private List<Station> stations;
    private List<JSONFileParser.JsonFragment> stationsDepth;
    private List<CSVFileParser.CSVFragment> stationsOpenDate;
    private Map<String, List<LinkedHashMap<String, java.io.Serializable>>> objectToSave;

    public SubwayFromAllSrcSaver(List<Line> lines, List<Station> stations,
                                 List<JSONFileParser.JsonFragment> stationsDepth,
                                 List<CSVFileParser.CSVFragment> stationsOpenDate) {
        this.stations = stations;
        this.lines = lines;
        this.stationsDepth = stationsDepth;
        this.stationsOpenDate = stationsOpenDate;
        this.objectToSave = new HashMap<>();
        prepareObjects();
    }

    public void saveJsonFile(String path) {
        String newJson = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            newJson = objectMapper.writeValueAsString(objectToSave);
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

    private void prepareObjects() {
        processDuplicates();

        lines = lines.stream()
                     .sorted(Comparator.comparingInt(Line::getNum))
                     .toList();

        List<LinkedHashMap<String, java.io.Serializable>> listOfStations = new ArrayList<>();
        LinkedHashMap<String, java.io.Serializable> stationData;

        for (Line line : lines) {
            List<Station> lineStations = stations.stream()
                 .filter(station -> station.getLineNum().equals(line.getNumber()))
                 .toList();

            for (Station station : lineStations) {
                stationData = new LinkedHashMap<>();
                stationData.put("name", station.getName());
                stationData.put("line", line.getName());

                stationData.put("date", DEFAULT_DATE_VAL);
                for (CSVFileParser.CSVFragment csvFragment : stationsOpenDate) {
                    if (station.getName().equals(csvFragment.getName())) {
                        stationData.put("date", csvFragment.getDate());
                        break;
                    }
                }
                stationData.put("depth", DEFAULT_DEPTH_VAL);
                for (JSONFileParser.JsonFragment jsonFragment : stationsDepth) {
                    if (station.getName().equals(jsonFragment.getStation_name())) {
                        String depth = jsonFragment.getDepth();
                        if (!depth.isEmpty() && !depth.isBlank()) {
                            int d = Integer.parseInt(depth.replace(",", ".").strip());
                            stationData.put("depth", d);
                            break;
                        }
                    }
                }
                listOfStations.add(stationData);
            }
        }
        objectToSave.put("stations", listOfStations);
    }

    private void processDuplicates() {
        LinkedList<JSONFileParser.JsonFragment> jsonFragmentsSorted =
                stationsDepth.stream()
                             .sorted(new JSONFileParser.JSONFragmentComparator())
                             .collect(Collectors.toCollection(LinkedList::new));

        ListIterator<JSONFileParser.JsonFragment> jsonFragmentIterator =
                (ListIterator<JSONFileParser.JsonFragment>) jsonFragmentsSorted.iterator();
        String name = "";
        while (jsonFragmentIterator.hasNext()) {
            if (jsonFragmentIterator.next().getStation_name().equals(name)) {
                jsonFragmentIterator.previous();
                jsonFragmentIterator.remove();
            } else {
                jsonFragmentIterator.previous();
                name = jsonFragmentIterator.next().getStation_name();
            }
        }
        stationsDepth = jsonFragmentsSorted;

        LinkedList<CSVFileParser.CSVFragment> csvFragmentsSorted =
                stationsOpenDate.stream()
                             .sorted(new CSVFileParser.CSVFragmentComparator())
                             .collect(Collectors.toCollection(LinkedList::new));

        ListIterator<CSVFileParser.CSVFragment> csvFragmentIterator =
                (ListIterator<CSVFileParser.CSVFragment>) csvFragmentsSorted.iterator();
        name = "";
        while (csvFragmentIterator.hasNext()) {
            if (csvFragmentIterator.next().getName().equals(name)) {
                csvFragmentIterator.previous();
                csvFragmentIterator.remove();
            } else {
                csvFragmentIterator.previous();
                name = csvFragmentIterator.next().getName();
            }
        }
        stationsOpenDate = csvFragmentsSorted;
    }
}

