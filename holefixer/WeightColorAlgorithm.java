package holefixer;

import imageparts.Hole;
import imageparts.Pixel;
import org.opencv.core.Mat;

import java.util.HashSet;

/**
 * this is an hole filling algorithm, which gets an weight function,
 * then iterate over all the pixels in the hole:
 * for every pixel, its iterate over all the pixels in the boundaries,
 *  multiply the weight of every weight(holePixel, boundaryPixel) times the color of boundaryPixel and then sum it.
 *  Finally, it sum all of the weight(holePixel,boundaryPixel) and divide the by this sum
 */
public class WeightColorAlgorithm implements HoleFillingAlgorithm {

    // message of illegal 4 or 8 connectivity
    private static final String KIND_CONNECTIVITY_ERROR_MESSAGE = "Error: you MUST insert 4 or 8 in kind connectivity";

    // four connected constant
    private static final int FOUR_CONNECTED = 4;

    // eight connected constant
    private static final int EIGHT_CONNECTED = 8;

    // the weight function to use
    private final WeightFunction _weightFunction;

    // the connetivity type
    private final int _connectivityType;

    // the value of color, which masked as hole
    private static final int HOLE_MASKED = -1;

    /**
     * default constructor, that sets a default weight - default euclidean weight
     */
    WeightColorAlgorithm() {
        _weightFunction = new EuclideanWeight();
        _connectivityType = 4;

    }

    /**
     * another constructor, which gets an weight function and assign it to the algorithm
     * @param weightFunction the weight function to assign
     */
    public WeightColorAlgorithm(WeightFunction weightFunction, int connectivityType) {
        this._weightFunction = weightFunction;

        if (connectivityType!= FOUR_CONNECTED && connectivityType!= EIGHT_CONNECTED){
            throw new IllegalArgumentException(KIND_CONNECTIVITY_ERROR_MESSAGE);
        }
        this._connectivityType=connectivityType;
    }

    // get sum of specific pixel with all another pixels in some set
    private float getSumOfWeights(Pixel pixel, HashSet<Pixel> pixels) {
        float sumOfWeights = 0;
        for (Pixel pixelConnect : pixels) {
            sumOfWeights += this._weightFunction.getWeight(pixel, pixelConnect);
        }
        return sumOfWeights;
    }

    // an inner method, which pixel to the boundary set
    private void addPixelBoundarySet(Mat image,HashSet<Pixel> pixels, int x, int y){

        // illegal pixel to add
        if (x>=image.rows() || y>=image.cols() || x<0||y<0||image.get(x,y)[0]<=0){
            return;
        }
        pixels.add(new Pixel(x,y,(float) image.get(x,y)[0]));
    }

    // inner method for adding all the pixels in the boundaries
    private HashSet<Pixel> getConnectedPixels(Mat image,Pixel pixel, int connectedKind){
        HashSet<Pixel> pixels=new HashSet<>();
        int x = pixel.getX();
        int y = pixel.getY();

        // add the pixel in 4 connected
        addPixelBoundarySet(image,pixels,x+1,y);
        addPixelBoundarySet(image,pixels,x,y+1);
        addPixelBoundarySet(image,pixels,x-1,y);
        addPixelBoundarySet(image,pixels,x,y-1);

        // if 8 connected, add the another pixels
        if (connectedKind== EIGHT_CONNECTED){
            addPixelBoundarySet(image,pixels,x+1,y+1);
            addPixelBoundarySet(image,pixels,x-1,y-1);
            addPixelBoundarySet(image,pixels,x-1,y+1);
            addPixelBoundarySet(image,pixels,x+1,y-1);
        }
        return pixels;
    }


    /**
     * this method get type of connectivity and find an hole in the image, by assigning all the pixels in the hole,
     * and create an hole object
     * @param image the image to identify hole
     * @return an hole object with the pixels of the hole and pixel in the boundaries
     */
    public Hole identifyHole(Mat image){
        HashSet<Pixel> holesPixels=new HashSet<>();
        HashSet <Pixel> boundariesPixels=new HashSet<>();

        // iterate over all the pixels and check if the color lower then the threshold, and then add the boundaries
        for (int i=0; i<image.rows();++i){
            for (int j=0;j<image.cols();++j){
                float color =(float)image.get(i,j)[0];

                // if the color lower then the threshold,
                if (color<=0){
                    Pixel holePixel=new Pixel(i,j,color);
                    holesPixels.add(holePixel);
                    boundariesPixels.addAll(getConnectedPixels(image, holePixel,_connectivityType));
                }
            }
        }
        return new Hole(_connectivityType,boundariesPixels,holesPixels);
    }


    @Override
    public Hole fill(Mat image) {
        Hole hole =identifyHole(image);
        for (Pixel holePixel : hole.getHolePixels()) {
            float sumOfWights = 0;

            // get all the weights of pixels with the specific pixel
            float allWeights = getSumOfWeights(holePixel, hole.getBoundariesPixels());

            // sum of all the weight times the color of the pixel
            for (Pixel pixelConnect : hole.getBoundariesPixels()) {
                sumOfWights += this._weightFunction.getWeight(holePixel, pixelConnect) * pixelConnect.getColor();}
            // set the color of the pixel to new color
            holePixel.setColor(sumOfWights / allWeights);
        }
        return hole;
    }
}
