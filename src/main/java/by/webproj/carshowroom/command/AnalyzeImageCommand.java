package by.webproj.carshowroom.command;

import by.webproj.carshowroom.entity.Image;
import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.service.ImagesService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Core;
import org.opencv.core.DominantColorDetector;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class AnalyzeImageCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(AnalyzeImageCommand.class);
    private static final String DIRECTORY = "serverforapp";
    private static final String FILE_EXTENSION = ".png";
    private final RequestFactory requestFactory;
    private final ImagesService imagesService;

    @SneakyThrows
    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError {
        request.removeFromSession("imageId");
        request.removeFromSession("colors");
        User user = (User) request.retrieveFromSession("user").get();
        String saveImage = request.getParameter("save");
        long imageId = 0;
        if (saveImage == null) {
            imageId = imagesService.addImage(user.getId());
            saveImage(request, imageId);
        } else {
            imageId = Long.parseLong(request.getParameter("imageId"));
        }
        request.addToSession("imageId", imageId);
        String uploadPath = "E:\\serverData";
        try{
            Mat image = Imgcodecs.imread("example.jpg");

            int width = 500;
            int height = 500;
            Size size = new Size(width, height);
            Imgproc.resize(image, image, size);


            Mat hsvImage = new Mat();
            Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);


            Scalar lower = new Scalar(0, 0, 0);
            Scalar upper = new Scalar(179, 255, 255);


            Mat mask = new Mat();
            Core.inRange(hsvImage, lower, upper, mask);


            int totalPixels = width * height;
            int[] colorCounts = new int[8];
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    double[] pixel = mask.get(row, col);
                    if (pixel[0] == 0) {
                        colorCounts[0]++;
                    } else if (pixel[0] == 1) {
                        colorCounts[1]++;
                    } else if (pixel[0] == 2) {
                        colorCounts[2]++;
                    } else if (pixel[0] == 3) {
                        colorCounts[3]++;
                    } else if (pixel[0] == 4) {
                        colorCounts[4]++;
                    } else if (pixel[0] == 5) {
                        colorCounts[5]++;
                    } else if (pixel[0] == 6) {
                        colorCounts[6]++;
                    } else if (pixel[0] == 7) {
                        colorCounts[7]++;
                    }
                }
            }
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


            Mat image1 = Imgcodecs.imread("test_image.jpg");


            Mat hsv = new Mat();
            Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);


            Scalar lowerRange = new Scalar(0, 0, 0);
            Scalar upperRange = new Scalar(10, 10, 10);


            Mat mask1 = new Mat();
            Core.inRange(hsv, lowerRange, upperRange, mask);

            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 3));
            Imgproc.erode(mask, mask, kernel);
            Imgproc.dilate(mask, mask, kernel);
            Mat image11 = Imgcodecs.imread(String.valueOf(imageId));


            Mat gray = new Mat();
            Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);


            Mat binary = new Mat();
            Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY);


            List<MatOfPoint> contours1 = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(binary, contours1, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
            Mat result = image.clone();
            Imgproc.drawContours(result, contours1, -1, new Scalar(0, 0, 255), 2);


            Imgcodecs.imwrite("output.jpg", result);

            Mat contours = new Mat();
        }catch (Throwable e){

        }

        ColorDetector dominantColorDetector = new ColorDetector();
        dominantColorDetector.analyzeImage(uploadPath, String.valueOf(imageId));
        request.addToSession("imageData", dominantColorDetector.analyzeImage(uploadPath, String.valueOf(imageId)));
        return requestFactory.createRedirectResponse("/controller?command=result");
    }

    private void saveImage(CommandRequest request, long imageId) {
        String uploadPath = "E:\\serverData";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            for (Part part : request.getParts()) {
                String fileName = part.getSubmittedFileName();
                if (fileName != null) {
                    part.write(uploadPath + File.separator + imageId + FILE_EXTENSION);
                }
            }
        } catch (IOException | ServletException e) {
            LOG.debug(e.getMessage(), e);
        }
    }
}
