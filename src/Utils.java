import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

public class Utils {

    public static String konwertujObraz(File file) throws IOException {
        byte[] imageContent = FileUtils.readFileToByteArray(file);
        return Base64.getEncoder().encodeToString(imageContent);
    }
     public static Image konwertujObraz(String string) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(string);
        File tmpImage = File.createTempFile("image", "png");
        FileUtils.writeByteArrayToFile(tmpImage, decodedBytes);
        BufferedImage image = ImageIO.read(tmpImage);
        tmpImage.delete();
        return image;

    }
}
