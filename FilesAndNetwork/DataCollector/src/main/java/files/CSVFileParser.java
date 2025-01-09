package files;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
@Getter
public class CSVFileParser {
    public static final int HEADERS_LINES_COUNT = 1;
    public static final String DATA_SEPARATOR = ",";
    private List<CSVFragment> data;

    public CSVFileParser(String path) {
        data = new ArrayList<>();
        parseCSV(path);
    }

    public void parseCSV(String path) {
        Path filePath = Paths.get(path);
        try{
            Files.lines(filePath)
                 .limit(HEADERS_LINES_COUNT)
                 .forEach(s -> {
                     String[] headers = s.split(DATA_SEPARATOR);
                     CSVFragment.headerName = headers[0].strip();
                     CSVFragment.headerDate = headers[1].strip();
                 });
            Files.lines(filePath)
                 .skip(HEADERS_LINES_COUNT)
                 .forEach(s -> {
                     String[] line = s.split(DATA_SEPARATOR);
                     CSVFragment csvFragment = new CSVFragment();
                     csvFragment.setName(line[0].strip());
                     csvFragment.setDate(line[1].strip());
                     data.add(csvFragment);
                 });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public class CSVFragment {
        private static String headerName;
        private static String headerDate;
        private String name;
        private String date;

        @Override
        public String toString() {
            return "CSVFragment { " +
                    "name='" + name + '\'' +
                    ", date='" + date + '\'' +
                    " }";
        }
    }

    public static class CSVFragmentComparator implements Comparator<CSVFragment> {
        @Override
        public int compare(CSVFragment o1, CSVFragment o2) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate d1;
            LocalDate d2;
            if (o1.getName().equals(o2.getName())) {
                try {
                    d1 = LocalDate.parse(o1.getDate(), formatter);
                } catch (DateTimeParseException ignored) {
                    d1 = LocalDate.of(2099, 1, 1);
                }
                try {
                    d2 = LocalDate.parse(o2.getDate(), formatter);
                } catch (DateTimeParseException ignored) {
                    d2 = LocalDate.of(2099, 1, 1);
                }
                return d1.compareTo(d2);
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        }
    }
}
