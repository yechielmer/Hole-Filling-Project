package imageparts;

import java.awt.*;
import java.util.Objects;

/**
 * this class represents some pixel, which include point 2d and greyscale color
 */
public class Pixel {

    // point 2d which include x and y parametres
    private final Point _point;

    // the color of the pixel
    private float _color;
    /**
     * the constructor of the pixel, which gets x,y and color
     * @param x the init x of the pixel
     * @param y the init y of the pixel
     * @param color the color of the pixel
     */
    public Pixel(int x, int y, float color){
        this._point=new Point(x,y);
        this._color=color;
    }

    /**
     *
     * @return x location of the pixel
     */
    public int getX(){
        return (int)this._point.getX();
    }

    /**
     *
     * @return y location of the pixel
     */
    public int getY(){
        return (int)this._point.getY();
    }

    /**
     *
     * @return the point of the pixel as point object
     */
    public Point getPoint(){
        return _point;
    }

    /**
     *
     * @return the color of the pixel
     */
    public float getColor() {
        return _color;
    }

    /**
     * sets the color of the pixel to new color
     * @param color new color to set
     */
    public void setColor(float color) {
        _color=color;
    }

    /**
     * this function return the distance between two pixels, based on the point distance
     * @param another another pixel to check the distance
     * @return the euclidean distance between the pixels
     */
    public double distance(Pixel another){
        return _point.distance(another.getPoint());
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Pixel pixel = (Pixel) o;
//        return Objects.equals(_point, pixel._point);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(_point);
//    }
}
