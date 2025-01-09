package files;

import lombok.Getter;

import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Getter
public class FilesSearch {
    private final HashMap<FileFormat, List<Path>> files;

    public FilesSearch(String rootFolder) {
        files = new HashMap<>();
        files.put(FileFormat.JSON, searchFiles(rootFolder, FileFormat.JSON));
        files.put(FileFormat.CSV, searchFiles(rootFolder, FileFormat.CSV));
    }

    private List<Path> searchFiles(String rootFolder, FileFormat format) {
        try {
            return Files.walk(Paths.get(
                                        rootFolder),
                                10, FileVisitOption.FOLLOW_LINKS)
                        .filter(path ->
                                !Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS) &&
                                    path.toString()
                                        .substring(path.toString().lastIndexOf(".") + 1)
                                        .equalsIgnoreCase(format.toString()))
                        .toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public enum FileFormat {
        JSON, CSV
    }
}
