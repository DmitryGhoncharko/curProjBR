package by.webproj.carshowroom.command;

import by.webproj.carshowroom.entity.ImageData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ColorDetector {
    private static String getColorFromRBG(String red, String green, String blue) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("E:\\ChromeDownloads\\121\\data.txt"));
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] lineSpl = line.trim().split("#");
            if ((Integer.parseInt(lineSpl[1].trim().split(" ")[0]) - Integer.parseInt(red) <= 40 && Integer.parseInt(lineSpl[1].trim().split(" ")[0]) - Integer.parseInt(red) >= 0) && (Integer.parseInt(lineSpl[1].trim().split(" ")[1]) - Integer.parseInt(green) <= 40 && Integer.parseInt(lineSpl[1].trim().split(" ")[1]) - Integer.parseInt(green) >= 0) && ((Integer.parseInt(lineSpl[1].trim().split(" ")[2]) - Integer.parseInt(blue) <= 40) && Integer.parseInt(lineSpl[1].trim().split(" ")[2]) - Integer.parseInt(blue) >= 0)) {
                return lineSpl[0];
            }
            line = bufferedReader.readLine();
        }
        return "Такого цвета не обнаружено";
    }

    public List<ImageData> analyzeImage(String uploadPath, String imageId) throws IOException {
        int tileSize = 400;
        File input = new File(uploadPath + File.separator + imageId + ".png");
        BufferedImage image = ImageIO.read(input);
        int rows = image.getHeight() / tileSize;
        int cols = image.getWidth() / tileSize;
        List<ImageData> imageData = new ArrayList<>();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                BufferedImage tile = image.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize);
                ImageIO.write(tile, "png", new File(uploadPath + File.separator + x + "_" + y + imageId + ".png"));
                BufferedImage image1 = ImageIO.read(new File(uploadPath + File.separator + x + "_" + y + imageId + ".png"));
                Map<Integer, Integer> colorMap = new HashMap<>();
                for (int y1 = 0; y1 < image1.getHeight(); y1++) {
                    for (int x1 = 0; x1 < image1.getWidth(); x1++) {
                        int color = image1.getRGB(x1, y1);
                        int count = colorMap.getOrDefault(color, 0);
                        colorMap.put(color, count + 1);
                    }
                }
                int maxCount = 0;
                int dominantColor = 0;
                for (int color : colorMap.keySet()) {
                    int count = colorMap.get(color);
                    if (count > maxCount) {
                        maxCount = count;
                        dominantColor = color;
                    }
                }
                Color c = new Color(dominantColor);
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                List<String> realColors = new ArrayList<>();
                realColors.add(getColorFromRBG(String.valueOf(red), String.valueOf(green), String.valueOf(blue)));
                imageData.add(ImageData.builder().imageUrl(x + "_" + y + imageId + ".png").colors(realColors).build());
            }
        }
        return imageData;
    }
}
