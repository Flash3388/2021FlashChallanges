package app;

import java.awt.image.BufferedImage;

public class Scrambler {

    public void scramble(BufferedImage image) {
        int x = image.getWidth();
        int y = image.getHeight();

        for(int i = 0; i < y; i++){
            for(int j = 0; j < (x / 2); j++){
                if(j % 2 == 0){
                    int leftPixel = image.getRGB(j, i);
                    int rightPixel = image.getRGB((x - j) - 1, (y - i) - 1);

                    image.setRGB(j, i, rightPixel);
                    image.setRGB((x - j) - 1, (y - i) - 1, leftPixel);
                }
            }
        }

        for(int i = 0; i < x; i++){
            for(int j = 0; j < (y / 2); j++){
                if(j % 2 == 0){
                    int topPixel = image.getRGB(i, j);
                    int bottomPixel = image.getRGB((x - i) - 1, (y - j) - 1);

                    image.setRGB(i, j, bottomPixel);
                    image.setRGB((x - i) - 1, ((y - j) - 1), topPixel);
                }
            }
        }
    }

    public void unscramble(BufferedImage image) {
        int x = image.getWidth();
        int y = image.getHeight();

        for(int i = 0; i < x; i++){
            for(int j = 0; j < (y / 2); j++){
                if(j % 2 == 0){
                    int topPixel = image.getRGB(i, j);
                    int bottomPixel = image.getRGB((x - i) - 1, (y - j) - 1);

                    image.setRGB(i, j, bottomPixel);
                    image.setRGB((x - i) - 1, ((y - j) - 1), topPixel);
                }
            }
        }

        for(int i = 0; i < y; i++){
            for(int j = 0; j < (x / 2); j++){
                if(j % 2 == 0){
                    int leftPixel = image.getRGB(j, i);
                    int rightPixel = image.getRGB((x - j) - 1, (y - i) - 1);

                    image.setRGB(j, i, rightPixel);
                    image.setRGB((x - j) - 1, (y - i) - 1, leftPixel);
                }
            }
        }
    }
}
