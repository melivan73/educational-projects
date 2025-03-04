import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static final String LEVEL_INDENT = "\t";
    private static final String BASE_URL = "https://skillbox.ru";
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    private static final String OUTPUT_FILE = "src/main/resources/out/site_map.txt";
    private static long startTime;

    public static void main(String[] args) {
        startTime = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool(MAX_THREADS);
        forkJoinPool.invoke(new CrawlTask(BASE_URL, null));
        forkJoinPool.shutdown();

        saveLinks();
        System.out.println("App ended in  : " + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static void saveLinks() {
        final Set<CrawlTask> tasks = CrawlTask.allTasksSet;
        try (FileWriter writer = new FileWriter(OUTPUT_FILE)) {
            writer.write("Site map of " + BASE_URL + " has been built." + "\n");
            writer.write("Total amount of links processed " + CrawlTask.visitedUrls.size() + "\n");
            writer.write("Total amount of tasks successfully executed " + tasks.size() + "\n");
            writer.write("Total time of execution ~ " + (System.currentTimeMillis() - startTime) + "\n");
            for (CrawlTask crawlTask : tasks) {
                if (crawlTask.getParent() == null) {
                    saveLinksRecursively(List.of(crawlTask), 0, writer);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving results: " + e.getMessage());
        }
    }

    private static void saveLinksRecursively(List<CrawlTask> tasks, int level, FileWriter writer)
            throws IOException {
        for (CrawlTask task : tasks) {
            String link = LEVEL_INDENT.repeat(level) + task.getUrl() + "\n";
            writer.write(link);
            if (!task.getChildren().isEmpty()) {
                List<CrawlTask> children = task.getChildren();
                saveLinksRecursively(children, ++level, writer);
            }
        }
    }
}
