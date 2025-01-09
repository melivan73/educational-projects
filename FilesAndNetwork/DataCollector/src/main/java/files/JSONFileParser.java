package files;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class JSONFileParser {
    private List<JsonFragment> data;

    public JSONFileParser(String path) {
        data = new ArrayList<>();
        parseJSON(path);
    }

    private void parseJSON(String path) {
        try {
            String json = Files.readString(Paths.get(path));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(json);
            for (int i = 0; i < node.size(); i++) {
                JsonFragment fragment = objectMapper.convertValue(node.get(i), JsonFragment.class);
                data.add(fragment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Setter
    @Getter
    public static class JsonFragment {
        private String station_name;
        private String depth;

        @Override
        public String toString() {
            return "JsonFragment{ " +
                    "station_name='" + station_name + '\'' +
                    ", depth='" + depth + '\'' +
                    '}';
        }
    }

    public static class JSONFragmentComparator implements Comparator<JsonFragment> {
        @Override
        public int compare(JsonFragment o1, JsonFragment o2) {
            int d1;
            int d2;
            try {
                d1 = Integer.parseInt(o1.getDepth());
            } catch (NumberFormatException ignored) {
                d1 = 0;
                o1.setDepth("0");
            }
            try {
                d2 = Integer.parseInt(o2.getDepth());
            } catch (NumberFormatException ignored) {
                d2 = 0;
                o2.setDepth("0");
            }
            if (o1.getStation_name().equals(o2.getStation_name())) {
                return -Integer.compare(Math.abs(d1), Math.abs(d2));
            } else {
                return o1.getStation_name().compareTo(o2.getStation_name());
            }
        }
    }
}

