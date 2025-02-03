import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;

public class ImageResizer implements Runnable {
    private static final boolean USE_SCALE_LIB = false;
    private final int id;
    private final int newWidth;
    private final String dstFolder;
    private final File[] files;
    private int newHeight;

    public ImageResizer(int id, int newWidth, String dstFolder, File[] files) {
        this.id = id;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.files = files;
    }

    @Override
    public void run() {
        String sysName = Thread.currentThread().getName();
        long start = System.currentTimeMillis();
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }
                if (image.getWidth() <= newWidth) return;
                newHeight = (int) Math.round(image.getHeight() / (image.getWidth() / (double) newWidth));
                BufferedImage newImage;
                String fileNamePrefix;
                if (!USE_SCALE_LIB) {
                    newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                    lowAccuracyScale(image, newImage);
                    fileNamePrefix = "";
                } else {
                    newImage = highAccuracyScale(image);
                    fileNamePrefix = "__";
                }
                File newFile = new File(dstFolder + "/" + fileNamePrefix + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Thread id = " + id + ", System name = " + sysName + ", Duration = " + (System.currentTimeMillis() - start) + " ms.");
    }

    private void lowAccuracyScale(BufferedImage image, BufferedImage newImage) {
        int widthStep = image.getWidth() / newWidth;
        int heightStep = image.getHeight() / newHeight;
        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                int rgb = image.getRGB(x * widthStep, y * heightStep);
                newImage.setRGB(x, y, rgb);
            }
        }
    }

    private BufferedImage highAccuracyScale(BufferedImage image) {
        return Scalr.resize(image, newWidth, newHeight, (BufferedImageOp) null);
    }
}
