package holefixer;

import imageparts.Hole;
import imageparts.Pixel;
import org.opencv.core.Mat;

import java.util.Arrays;
import java.util.HashSet;

/**
 * this algorithm approximate the algorithm, by running all the the pixels in the image,
 * and get the mean of all the neighbors which are not -1.
 */
public class ApproximationAlgorithm implements HoleFillingAlgorithm {

    // the pixel which represents  the hole
    private final int HOLE_MASKED =-1;

    // set of all directions, like -1,1
    private final HashSet<Integer> directionsSet=new HashSet<>(Arrays.asList(1,-1));

    @Override
    public Hole fill(Mat image) {
        HashSet<Pixel> holePixels = new HashSet<>();
        Hole hole =new Hole(holePixels);

        // iterate over all the pixels and check if the color lower then the threshold, and then add the boundaries
        for (int i = 0; i < image.rows(); ++i) {
            for (int j = 0; j < image.cols(); ++j) {
                float color = (float) image.get(i, j)[0];

                // get all the weights of pixels with the specific pixel
                int counter = 0;
                float sumOfWights = 0;

                // if the color is -1
                if (color <= 0) {

                    for (Integer x :directionsSet){
                        for (Integer y:directionsSet){
                            float connectedColor = (float) image.get(i + x, j + y)[0];
                            if (connectedColor > 0) {
                                sumOfWights += connectedColor;
                                counter += 1;
                            }
                        }
                    }
                    Pixel holePixel = new Pixel(i, j, sumOfWights / counter);
                    double[] data={sumOfWights / counter};
                    image.put(i,j,data);
                    holePixels.add(holePixel);
                }
            }
        }
        return hole;
    }
}
