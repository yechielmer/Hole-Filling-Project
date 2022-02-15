package imagepreaperer;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

/**
 * an service class for basic operations, like read imaging,
 */
public class ImagePreparer {

    // the value of color, which masked as hole
    private static final double HOLE_MASKED = -1.0;

    // the threshold which decide which image is hole
    private static final int THRESHOLD = 0;

    /**
     * this function convert picture to grey scale image
     */
    public static void greyScaling(Mat image){
        Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
    }

    /**
     * this function gets another picture and masking the original picture by the another mask. its gray the picture
     * and then copy it to the original picture
     * @param mask the mask of the image
     */
    public static Mat maskImage(Mat image,Mat mask) throws IllegalMaskException {

        Imgproc.cvtColor(mask, mask, Imgproc.COLOR_RGB2GRAY);

        // mask the image
        Mat newMat =new Mat(mask.rows(),mask.cols(),mask.type());
        if (image.rows()!=mask.rows() || image.cols()!=mask.cols()){
            throw new IllegalMaskException();
        }
        image.copyTo(newMat,mask);
        return newMat;
    }


    /**
     * this function change the color by specific threshold
     */
    public static Mat changeColorThreshold(Mat image) {
        Mat newImage = new Mat();
        image.copyTo(newImage);
        for (int i = 0; i < image.rows(); ++i) {
            for (int j = 0; j < image.cols(); ++j) {
                float color = (float) image.get(i, j)[0];
                if (color <= THRESHOLD) {
                    double[] newColor={HOLE_MASKED};
                    newImage.put(i,j,newColor);
                }
            }
        }
        return newImage;
    }

    /**
     * static method, which gets image and mask and change the image
     * @param image the image to change
     * @param mask the mask to add to the image
     * @return masked greysclaing Image
     */

    public static Mat prepareImage(Mat image, Mat mask) throws IllegalMaskException {
        // creating the image and greyscale
        ImagePreparer.greyScaling(image);
        Mat maskedImage = ImagePreparer.maskImage(image,mask);
        return ImagePreparer.changeColorThreshold(maskedImage);
    }



}

