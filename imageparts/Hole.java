package imageparts;

import java.util.HashSet;

/**
 * this class represents an hole in the image, which include set of pixels in hole and set of pixel close to hole
 */
public class Hole {

    // the Type of connectivity in the pixel in the hole
    private final int connectType;

    // four connected constant
    private static final int FOUR_CONNECTED = 4;


    // the set of pixels in the boundaries
    private final HashSet<Pixel> boundariesPixels;

    // the set of pixels in the hole itself
    private final HashSet<Pixel> holePixels;

    /**
     * the constructor of the hole, which get Type of connectivty, pixels in the boundary and pixels in the hole
     * @param connectType the Type of the connectivity
     * @param boundariesPixels the pixel in the boundaries
     * @param holePixels the pixels in the hole
     */
    public Hole(int connectType, HashSet<Pixel> boundariesPixels, HashSet<Pixel> holePixels){
        this.connectType = connectType;
        this.boundariesPixels = boundariesPixels;
        this.holePixels = holePixels;
    }
    /**
     * the constructor of the hole, which get pixels in the hole

     * @param holePixels the pixels in the hole
     */
    public Hole(HashSet<Pixel> holePixels){
        this.connectType = FOUR_CONNECTED;
        this.boundariesPixels = null;
        this.holePixels = holePixels;
    }

    /**
     *
     * @return the Type of the hole connectivity
     */
    public int getConnectType() {
        return connectType;
    }

    /**
     *
     * @return set of pixels in the hole
     */
    public HashSet<Pixel> getHolePixels() {
        return holePixels;
    }

    /**
     *
     * @return set of pixels in the boundaries
     */
    public HashSet<Pixel> getBoundariesPixels() {
        return boundariesPixels;
    }

}
