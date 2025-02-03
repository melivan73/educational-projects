import java.io.File;

public class Main {
    public static final int IMAGE_SIZE_NEW = 300;
    private static final String SRC_FOLDER = "C:/users/4762006/pictures/java_from";
    private static final String DST_FOLDER = "C:/users/4762006/pictures/java_to";

    public static void main(String[] args) {
        File srcDir = new File(SRC_FOLDER);
        //long start = System.currentTimeMillis();
        int threadsCount = Runtime.getRuntime().availableProcessors();
        if (threadsCount < 1) return;
        File[] files = srcDir.listFiles();
        if (files == null || files.length == 0) return;
        int commonSrcArraySize = files.length / threadsCount;
        int lastSrcArraySize = files.length % threadsCount;
        System.out.println("CPU cores count: " + threadsCount + ". Total files count: " + files.length + ".");
        System.out.println("Files count for processing with each thread (except last one) " + commonSrcArraySize + ".");
        System.out.println("Files count for processing with last thread " + lastSrcArraySize + ".");

        File[][] srcArrays = new File[threadsCount][commonSrcArraySize];
        int _size = 0;
        int srcArrayOffset = 0;

        for (int i = 0; i < threadsCount; i++) {
            srcArrayOffset += _size;
            _size = (i < threadsCount - 1) ? commonSrcArraySize : lastSrcArraySize;
            srcArrays[i] = new File[_size];
            System.arraycopy(files, srcArrayOffset, srcArrays[i], 0, _size);
            ImageResizer imageResizer = new ImageResizer(i, IMAGE_SIZE_NEW, DST_FOLDER, srcArrays[i]);
            new Thread(imageResizer).start();
        }
    }
}
