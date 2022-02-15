package holefixer;

import imageparts.Pixel;

/**
 * this interface represents the weight function, which gets gets 2 pixels and apply and weight for them
 */
public interface WeightFunction {
    /**
     * this method return the weight between two variables
     * @param a the first pixel
     * @param b another pixel
     * @return the weight between the pixels
     */
    double getWeight(Pixel a, Pixel b);
}
