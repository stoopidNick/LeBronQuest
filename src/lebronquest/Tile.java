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
public class Tile {
    //Images for the tiles should be 32x32
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32; 
    private boolean isSolid;
    private Image image;

    public Tile(String filename, boolean isSolid) {
        image = new Image(filename);
        this.isSolid = isSolid;
    }

    public boolean isIsSolid() {
        return isSolid;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Tile{" + "isSolid=" + isSolid + ", image=" + image + '}';
    }
    
    
    
}
