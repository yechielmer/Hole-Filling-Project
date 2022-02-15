package holefixer;

import imageparts.Hole;
import org.opencv.core.Mat;

/**
 * this interface resembles an algorithm for hole filling. It gets an hole, and fill the hole by the algorithm
 */
public interface HoleFillingAlgorithm {
    /**
     * the main method of the algorithm, which gets an hole and fill it, depends on the algorithm process
     * @param image the image to fill the hole
     */
    Hole fill(Mat image);
}
