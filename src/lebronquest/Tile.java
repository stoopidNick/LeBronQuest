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
    private int row;
    private int column;

    public Tile(ImageView imageView, TileType type, int row, int column) {
        this.imageView = imageView;
        this.type = type;
        this.row = row;
        this.column = column;
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

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Tile{" + "imageView=" + imageView + ", type=" + type + ", row=" + row + ", column=" + column + '}';
    }


    
    
}
