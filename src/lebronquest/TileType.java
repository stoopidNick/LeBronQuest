/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import javafx.scene.image.Image;

/**
 *
 * @author transflorida
 */
public class TileType {
    //Images for the tiles should be 32x32
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32; 
    private boolean isSolid;
    private Image image;
    private int ID;

    public TileType(String filename, boolean isSolid, int ID) {
        image = new Image(filename);
        this.isSolid = isSolid;
        this.ID = ID;
    }

    public boolean isIsSolid() {
        return isSolid;
    }

    public Image getImage() {
        return image;
    }

    public int getID() {
        return ID;
    }

    public void setIsSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "TileType{" + "isSolid=" + isSolid + ", image=" + image + ", ID=" + ID + '}';
    }
    
    
    
    
}
