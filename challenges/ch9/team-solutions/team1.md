```java
package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

/*
*   After many discussions about who is hotter between the two pictures we concluded that scrambled2.jpeg is the hottest.
*   there are two main reasons:
*       1. his cheekbones.
*       2. the way he stares at you.
*/


public class Main
{
    public static BufferedImage theUnTimeyWimeyStuffinator(BufferedImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                if((i + j) % 2 == 0)
                {
                    if((i < width / 2 && j + 1 > height / 2) || (i + 1 > width / 2 && j < height / 2))
                    {
                        newImage.setRGB(i, j, image.getRGB((width - 1) - i,(height - 1) - j));
                    }
                    else
                    {
                        newImage.setRGB(i, j, image.getRGB(i, j));
                    }
                }
                else
                {

                    if((i < width / 2 && j + 1 > height / 2) || (i + 1 > width / 2 && j < height / 2))
                    {
                        newImage.setRGB(i, j, image.getRGB(i, j));
                    }
                    else
                    {
                        newImage.setRGB((width - 1) - i,(height - 1) - j, image.getRGB(i, j));
                    }
                }
            }
        }

        return newImage;
    }

    public static void main(String[] args) throws IOException
    {
        BufferedImage scrambled1 = ImageIO.read(new File("scrambled1.jpeg"));
        BufferedImage scrambled2 = ImageIO.read(new File("scrambled2.jpeg"));

        File unScrambled1 = new File("unScrambled1.jpeg");
        File unScrambled2 = new File("unScrambled2.jpeg");


        ImageIO.write(theUnTimeyWimeyStuffinator(scrambled1), "jpeg", unScrambled1);
        ImageIO.write(theUnTimeyWimeyStuffinator(scrambled2), "jpeg", unScrambled2);


    }
}

```
