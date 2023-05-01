package by.webproj.carshowroom.model.connection;



import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class Test {
//    public static void main(String[] args) throws IOException {
//        String url = "https://colorscheme.ru/color-names.html";
//        Document document = Jsoup.connect(url).get();
//        Elements colors = document.select("table tr");
//        int index = 0;
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Element color : colors) {
//            Elements colorData = color.select("td");
//            for(Element element : colorData){
//                String data = element.text().replaceAll("#[A-F0-9]{6}","");
//
//                if(data.length()>0){
//                    if(index==4){
//                        stringBuilder.append("\n");
//                        index = 0;
//                    }
//
//                    stringBuilder.append(data +  " ");
//                    if(index==0){
//                      stringBuilder.append("# ");
//                    }
//                    index++;
//                }
//            }
//        }
//        FileWriter fileWriter = new FileWriter("E:\\ChromeDownloads\\121\\data.txt");
//        fileWriter.write(stringBuilder.toString());
//        fileWriter.close();
//    }
public static void main(String[] args) {
    try {
        File file = new File("E:\\serverData\\12.png");
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;
                System.out.println("(" + x + "," + y + "): R=" + r + ", G=" + g + ", B=" + b);
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading image file: " + e.getMessage());
    }
}
    private static String getColorFromHex(String color) throws IOException {
        color = color.toUpperCase();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("E:\\ChromeDownloads\\121\\content.txt"));
        String line = bufferedReader.readLine();

        if(line.contains(color)){
            String[] lineSpl = line.split(color);
            String[] data = lineSpl[0].split("#[A-Z]{0,}[0-9]{1,}");
            return data[lineSpl.length-1];
        }
        return "Такого цвета не обнаружено";
    }
}
