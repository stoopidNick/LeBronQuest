/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import javafx.scene.image.ImageView;

/**
 *
 * @author transflorida
 */
public class Tile {
    private ImageView imageView;
    private TileType type;

    public Tile(ImageView imageView, TileType type) {
        this.imageView = imageView;
        this.type = type;
    }
    
    

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public String toString() {
        return "Tile{" + "imageView=" + imageView + ", type=" + type + '}';
    }

    
    
}
