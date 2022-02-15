package holefixer;

import imageparts.Pixel;
import org.opencv.core.Mat;
import imageparts.*;

/**
 * class which gets an algorithm, and change has the utility of filling an hole in image
 */
public class HoleFixer {

    // the algorithm for fill the image
    private HoleFillingAlgorithm _algorithm;


    /**
     * default constructor, which set the WeightColor Algorithm
     */
    public HoleFixer(){
        this._algorithm=new WeightColorAlgorithm();
    }

    /**
     * another constructor, which gets algorithm and set it to be the current algorithm
     * @param algorithm the algo use in the class
     */
    public HoleFixer(HoleFillingAlgorithm algorithm){
        this._algorithm=algorithm;
    }

    /**
     * this method gets an hole, which some image to fil the hole
     * @param image the image to change
     */
    public Mat fix(Mat image){
        Hole hole =this._algorithm.fill(image);
        // change the image
        for (Pixel pixel:hole.getHolePixels()){
            double[] val={pixel.getColor()};
            image.put(pixel.getX(),pixel.getY(),val);
        }
        return image;
    }
}
