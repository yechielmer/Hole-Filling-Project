package holefixer;

import imageparts.Pixel;

/**
 * this class represents the euclidean weight class, some class which get z and epsilon and
 * apply it to distance between 2 pixels
 */
public class EuclideanWeight implements WeightFunction {

    // default value for z
    private static final int DEFAULT_Z = 3;

    // default value for epsilon
    private static final double DEFAULT_EPSILON = 0.01;

    // the z variable, which resembles the power of the euclidean weight
    private final double _z;

    // the epsilon variable, which resembles an little number to avoid divide by zero
    private final double _epsilon;

    /**
     * constructor of the class, which gets z and epsilon
     * @param z the z variable
     * @param epsilon the epsilon variable
     */
    public EuclideanWeight(double z, double epsilon){
        this._z=z;
        this._epsilon=epsilon;
    }

    /**
     * default constructor which doesn't get args
     */
    public EuclideanWeight(){
        this._z= DEFAULT_Z;
        this._epsilon= DEFAULT_EPSILON;
    }

    @Override
    public double getWeight(Pixel a, Pixel b) {

        // get the distance between two pixels
        double distance=a.distance(b);

        // power the distance by z
        double distanceZ = Math.pow(distance,this._z);

        // divide 1 by distance and epsilon
        return 1/(distanceZ+this._epsilon);
    }
}
