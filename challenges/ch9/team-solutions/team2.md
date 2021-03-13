```java
public class Main {
    public static void AddPoints(int x, int y, ArrayList<Point> points)
    {
        points.add(new Point(x +1, y));
        points.add(new Point(x - 1, y));
        points.add(new Point(x, y + 1));
        points.add(new Point(x, y - 1));
        points.add(new Point(x + 1, y + 1));
        points.add(new Point(x + 1, y - 1));
        points.add(new Point(x - 1, y + 1));
        points.add(new Point(x - 1, y - 1));
    }

    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("scrambled2.jpeg"));

        for (int y = 0; y < image.getHeight(); y += 2)
        {
            for (int x = 0; x < image.getWidth(); x += 2)
            {
                int half_width = image.getWidth() / 2;
                int half_height = image.getHeight() / 2;

                if (half_width % 2 == 0) {
                    if (x == half_width) {
                        x++;
                    }
                }
                else {
                    if (x == half_width - 1) {
                        x++;
                    }
                }

                if (half_height % 2 == 0) {
                    if (y == half_height) {
                        y++;
                    }
                }
                else {
                    if (y == half_height - 1) {
                        y++;
                    }
                }

                ArrayList<Point> points = new ArrayList<Point>();

                AddPoints(x, y, points);

                for (Point point : points) {
                    if ((point.getX() >= 0 && point.getX() < image.getWidth()) && (point.getY() >= 0 && point.getY() < image.getHeight()))
                    {
                        image.setRGB((int)point.getX(), (int)point.getY(), image.getRGB(x, y));
                    }
                }
            }
        }

        ImageIO.write(image, "jpeg", new File("fixed.jpeg"));
    }
}
```
