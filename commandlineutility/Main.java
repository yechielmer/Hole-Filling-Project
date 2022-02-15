package commandlineutility;

import holefixer.*;
import imagepreaperer.IllegalMaskException;
import imagepreaperer.ImagePreparer;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * the main class, which gets 5 arguments, find hole and fill it by the euclidean weight algorithm
 */
public class Main {

    // the new image name
    private static final String NEW_IMAGE_NAME = "new_image.jpg";

    // message for not-enough-arguments case
    private static final String ARGUMENTS_EXCEPTION_MESSAGE = "Using: You MUST insert exact 5 arguments";

    // required number of arguments in the command line
    private static final int ARGUMENTS_NUMBER = 5;

    // create static member for opencv library
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}


    // this method gets address and read image
    private static Mat readImage(String address){

        // read file
        Mat image = Imgcodecs.imread(address);

        // the file does not exist
        if (image.rows()==0){
            throw new IllegalAccessError();
        }
        return image;
    }



    // this method prepare the algorithm for the hole filling algorithm
    private static HoleFillingAlgorithm constructAlgorithm(double z, double epsilon, int connectivityType){

        if (epsilon<=0){
            throw new IllegalArgumentException("Error: epsilon must be greater then zero");
        }

        // create weight function
        WeightFunction weight=new EuclideanWeight(z, epsilon);
        return new WeightColorAlgorithm(weight,connectivityType);
    }

    // method which write an image to file
    private static void writeImage(Mat image){
        Imgcodecs.imwrite(NEW_IMAGE_NAME,image);
    }

    /**
     * the main function of the file. The method gets from command line image, image mask,
     * z and epsilon for weight function and connectivity kind
     * @param args an string array that includes all the args for function
     */
    public static void main(String[] args) throws IllegalMaskException {
        if (args.length!= ARGUMENTS_NUMBER){
            throw new IllegalArgumentException(ARGUMENTS_EXCEPTION_MESSAGE);
        }

        // read file
        Mat image= readImage(args[0]);
        Mat mask= readImage(args[1]);

        // prepare the image
        Mat processedImage = ImagePreparer.prepareImage(image, mask);

        // hole filling algorithm
        HoleFillingAlgorithm algorithm= constructAlgorithm(Double.parseDouble(args[2]),Double.parseDouble(args[3]),
                Integer.parseInt(args[4]));


        // option for algorithm approximation
//        algorithm=new holefixer.ApproximationAlgorithm();

        // fill the hole
        HoleFixer holeFixer = new HoleFixer(algorithm);
        Mat fixedImage = holeFixer.fix(processedImage);

        // write the image
        writeImage(fixedImage);
    }
}
