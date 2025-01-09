import files.*;
import subway.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {
    public static final String HTML_PATH = "src/main/resources/metro.html";
    public static final String ROOT_FILES_FOLDER = "src/main/resources/FilesSearch/data";
    public static final String OUTPUT_PATH_HTML = "src/main/resources/out/json_pattern_1.json";
    public static final String OUTPUT_PATH_ALL_SRC = "src/main/resources/out/json_pattern_2.json";

    public static void main(String[] args) {
        System.out.println("HTML page scan started at " + HTML_PATH);
        WebPageParser webPageParser = new WebPageParser(HTML_PATH);
        webPageParser.getAllData();
        System.out.println("Lines found: " + webPageParser.getLines().size());
        System.out.println("Stations found: " + webPageParser.getStations().size());

        System.out.println("Files search started at " + ROOT_FILES_FOLDER);
        FilesSearch filesSearch = new FilesSearch(ROOT_FILES_FOLDER);
        Map<FilesSearch.FileFormat, List<Path>> searchFilesMap = filesSearch.getFiles();
        System.out.println("Files found: " + "JSON " + searchFilesMap.get(FilesSearch.FileFormat.JSON).size());
        System.out.println("Files found: " + "CSV " + searchFilesMap.get(FilesSearch.FileFormat.CSV).size());
        // Два больших списка, куда складываются все данные из всех json и scv
        List<JSONFileParser.JsonFragment> jsonFragments = new ArrayList<>();
        List<CSVFileParser.CSVFragment> csvFragments = new ArrayList<>();
        for (Map.Entry<FilesSearch.FileFormat, List<Path>> entry : searchFilesMap.entrySet()) {
            for (Path path : entry.getValue()) {
                if (entry.getKey() == FilesSearch.FileFormat.JSON) {
                    JSONFileParser jsonFileParser = new JSONFileParser(path.toString());
                    jsonFragments.addAll(jsonFileParser.getData());
                } else {
                    CSVFileParser jsonFileParser = new CSVFileParser(path.toString());
                    csvFragments.addAll(jsonFileParser.getData());
                }
            }
        }

        SubwayFromHTMLSaver saveHTMLData = new SubwayFromHTMLSaver(webPageParser.getLines(),
                webPageParser.getStations());
        saveHTMLData.saveJsonFile(OUTPUT_PATH_HTML);
        System.out.println("JSON with pattern 1 saved to " + OUTPUT_PATH_HTML);

        SubwayFromAllSrcSaver subwayFromAllSrcSaver;
        try {
            subwayFromAllSrcSaver = new SubwayFromAllSrcSaver(
                    webPageParser.getLines(), webPageParser.getStations(),
                    jsonFragments, csvFragments);
            subwayFromAllSrcSaver.saveJsonFile(OUTPUT_PATH_ALL_SRC);
            System.out.println("JSON with pattern 2 saved to " + OUTPUT_PATH_ALL_SRC);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
