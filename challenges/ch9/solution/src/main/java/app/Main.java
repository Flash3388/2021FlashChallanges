package app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedImage image = ImageIO.read(file);
        Scrambler scrambler = new Scrambler();
        scrambler.scramble(image);
        ImageIO.write(image, "jpg", new File(file.getParent(), "scrambled.jpeg"));
        scrambler.unscramble(image);
        ImageIO.write(image, "jpg", new File(file.getParent(), "original.jpeg"));
    }
}
