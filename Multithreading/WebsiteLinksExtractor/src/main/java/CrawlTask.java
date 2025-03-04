import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class CrawlTask extends RecursiveTask<Void> {
    public static final String DOMAIN = "skillbox.ru";
    public static final String PROTO_PREFIX = "https://";
    static final Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());
    static final Set<CrawlTask> allTasksSet = Collections.synchronizedSet(new HashSet<>());
    private static final int TIMEOUT = 10000; // Таймаут соединения
    private final String url;
    private final CrawlTask parent;
    private final List<CrawlTask> children;

    public CrawlTask(String url, CrawlTask parent) {
        this.url = url;
        this.parent = parent;
        children = new ArrayList<>();
    }

    @Override
    protected Void compute() {

        if (!visitedUrls.add(url)) {
            return null;
        }

        allTasksSet.add(this);

        System.out.println("Scanning: " + url);

        Connection conn;
        Document doc;
        try {
            TimeUnit.MILLISECONDS.sleep(110);
            conn = Jsoup.connect(url).timeout(TIMEOUT);
            doc = conn.get();
            if (conn.response().contentType() == null ||
                !conn.response().contentType().contains("text/html")) {
                throw new IOException();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to retrieve: " + url);
            return null;
        }

        Elements links = doc.select("a[href]");
        Set<CrawlTask> tasks = new HashSet<>();
        for (Element link : links) {
            String nextUrl = link.absUrl("href");
            if (isValidUrl(nextUrl)) {
                CrawlTask task = new CrawlTask(nextUrl, this);
                tasks.add(task);
                this.children.add(task);
                task.fork();
            }
        }

        for (CrawlTask task : tasks) {
            task.join();
        }

        return null;
    }

    /**
     * Please note that incorrect links will also be added to the output file,
     * but cant have children
     */
    private boolean isValidUrl(String url) {
        // end slashed duplicates like http://ex.com/ ~ http://ex.com are not valid
        if ((url.endsWith("/") && visitedUrls.contains(url.substring(0, url.length() - 1)))
            || (!url.endsWith("/") && visitedUrls.contains(url + "/"))) {
            return false;
        }
        try {
            URI uri = new URI(URLDecoder.decode(url, StandardCharsets.UTF_8));
            if (uri.getScheme() == null || uri.isOpaque() || uri.getQuery() != null || uri.getFragment() != null ||
                !uri.isAbsolute() || uri.getPort() > 0 || !PROTO_PREFIX.startsWith(uri.getScheme())) {
                return false;
            }
            return uri.getHost() != null && uri.getHost().equals(DOMAIN) && !visitedUrls.contains(url);
        } catch (URISyntaxException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUrl() {
        return url;
    }

    public List<CrawlTask> getChildren() {
        return children;
    }

    public CrawlTask getParent() {
        return parent;
    }
}