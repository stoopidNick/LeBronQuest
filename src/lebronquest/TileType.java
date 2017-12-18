/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author transflorida
 */
public class TileType {

    private final static Logger LOGGER = Logger.getLogger(TileType.class.getName());
    //Images for the tiles should be 32x32
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;
    private boolean isSolidTop;
    private boolean isSolidBottom;
    private boolean isSolidLeft;
    private boolean isSolidRight;
    private Image image;
    private int ID;

    public TileType(String filename, boolean isSolidTop, boolean isSolidBottom, boolean isSolidLeft, boolean isSolidRight, int ID) {
        LOGGER.setLevel(Level.INFO);
        //USe the classloader to tget the image from the jar file
        InputStream imageStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
        image = new Image(imageStream);
        this.isSolidTop = isSolidTop;
        this.isSolidBottom = isSolidBottom;
        this.isSolidLeft = isSolidLeft;
        this.isSolidRight = isSolidRight;
        this.ID = ID;
    }

    public Image getImage() {
        return image;
    }

    public int getID() {
        return ID;
    }

    public boolean isIsSolidTop() {
        return isSolidTop;
    }

    public void setIsSolidTop(boolean isSolidTop) {
        this.isSolidTop = isSolidTop;
    }

    public boolean isIsSolidBottom() {
        return isSolidBottom;
    }

    public void setIsSolidBottom(boolean isSolidBottom) {
        this.isSolidBottom = isSolidBottom;
    }

    public boolean isIsSolidLeft() {
        return isSolidLeft;
    }

    public void setIsSolidLeft(boolean isSolidLeft) {
        this.isSolidLeft = isSolidLeft;
    }

    public boolean isIsSolidRight() {
        return isSolidRight;
    }

    public void setIsSolidRight(boolean isSolidRight) {
        this.isSolidRight = isSolidRight;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "TileType{" + "isSolidTop=" + isSolidTop + ", isSolidBottom=" + isSolidBottom + ", isSolidLeft=" + isSolidLeft + ", isSolidRight=" + isSolidRight + ", image=" + image + ", ID=" + ID + '}';
    }

}
