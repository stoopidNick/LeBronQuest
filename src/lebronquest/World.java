/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author transflorida
 */
public class World {
    private static String MAP_FILENAME = "src/resources/map.txt";
    private static int MAP_WIDTH;
    private static int MAP_HEIGHT;
    private int[][] map;
    private ImageView[][] imageViewMap;
    private static final int NUM_TILE_TYPES = 64;
    private Tile[] tiles = new Tile[NUM_TILE_TYPES];
    private Group root;
    

    public World(Group root) {
        this.root = root;
        createTiles();
        readMap();
        //printMap();
        addTiles();
    }

    private void createTiles() {
        tiles[0] = new Tile("img/tiles/tile_00.png", true);
        tiles[1] = new Tile("img/tiles/tile_01.png", true);
        tiles[2] = new Tile("img/tiles/tile_02.png", true);
        tiles[3] = new Tile("img/tiles/tile_03.png", true);
        tiles[4] = new Tile("img/tiles/tile_04.png", true);
        tiles[5] = new Tile("img/tiles/tile_05.png", true);
        tiles[6] = new Tile("img/tiles/tile_06.png", true);
        tiles[7] = new Tile("img/tiles/tile_07.png", true);
        tiles[8] = new Tile("img/tiles/tile_08.png", false);
        tiles[9] = new Tile("img/tiles/tile_09.png", true);
        tiles[10] = new Tile("img/tiles/tile_10.png", true);
        tiles[11] = new Tile("img/tiles/tile_11.png", true);
        tiles[12] = new Tile("img/tiles/tile_12.png", false);
        tiles[13] = new Tile("img/tiles/tile_13.png", true);
        tiles[14] = new Tile("img/tiles/tile_14.png", true);
        tiles[15] = new Tile("img/tiles/tile_15.png", true);
        tiles[16] = new Tile("img/tiles/tile_16.png", true);
        tiles[17] = new Tile("img/tiles/tile_17.png", true);
        tiles[18] = new Tile("img/tiles/tile_18.png", true);
        tiles[19] = new Tile("img/tiles/tile_19.png", true);
        tiles[20] = new Tile("img/tiles/tile_20.png", true);
        tiles[21] = new Tile("img/tiles/tile_21.png", true);
        tiles[22] = new Tile("img/tiles/tile_22.png", true);
        tiles[23] = new Tile("img/tiles/tile_23.png", true);
        tiles[24] = new Tile("img/tiles/tile_24.png", true);
        tiles[25] = new Tile("img/tiles/tile_25.png", true);
        tiles[26] = new Tile("img/tiles/tile_26.png", true);
        tiles[27] = new Tile("img/tiles/tile_27.png", true);
        tiles[28] = new Tile("img/tiles/tile_28.png", false);
        tiles[29] = new Tile("img/tiles/tile_29.png", true);
        tiles[30] = new Tile("img/tiles/tile_30.png", true);
        tiles[31] = new Tile("img/tiles/tile_31.png", true);
        tiles[32] = new Tile("img/tiles/tile_32.png", true);
        tiles[33] = new Tile("img/tiles/tile_33.png", true);
        tiles[34] = new Tile("img/tiles/tile_34.png", true);
        tiles[35] = new Tile("img/tiles/tile_35.png", true);
        tiles[36] = new Tile("img/tiles/tile_36.png", true);
        tiles[37] = new Tile("img/tiles/tile_37.png", false);
        tiles[38] = new Tile("img/tiles/tile_38.png", false);
        tiles[39] = new Tile("img/tiles/tile_39.png", false);
        tiles[40] = new Tile("img/tiles/tile_40.png", false);
        tiles[41] = new Tile("img/tiles/tile_41.png", false);
        tiles[42] = new Tile("img/tiles/tile_42.png", false);
        tiles[43] = new Tile("img/tiles/tile_43.png", false);
        tiles[44] = new Tile("img/tiles/tile_44.png", false);
        tiles[45] = new Tile("img/tiles/tile_45.png", false);
        tiles[46] = new Tile("img/tiles/tile_46.png", false);
        tiles[47] = new Tile("img/tiles/tile_47.png", true);
        tiles[48] = new Tile("img/tiles/tile_48.png", true);
        tiles[49] = new Tile("img/tiles/tile_49.png", true);
        tiles[50] = new Tile("img/tiles/tile_50.png", false);
        tiles[51] = new Tile("img/tiles/tile_51.png", false);
        tiles[52] = new Tile("img/tiles/tile_52.png", true);
        tiles[53] = new Tile("img/tiles/tile_53.png", true);
        tiles[54] = new Tile("img/tiles/tile_54.png", false);
        tiles[55] = new Tile("img/tiles/tile_55.png", false);
        tiles[56] = new Tile("img/tiles/tile_56.png", true);
        tiles[57] = new Tile("img/tiles/tile_57.png", true);
        tiles[58] = new Tile("img/tiles/tile_58.png", true);
        tiles[59] = new Tile("img/tiles/tile_59.png", true);
        tiles[60] = new Tile("img/tiles/tile_60.png", true);
        tiles[61] = new Tile("img/tiles/tile_61.png", true);
        tiles[62] = new Tile("img/tiles/tile_62.png", true);
        tiles[63] = new Tile("img/tiles/tile_63.png", true);
    }

    private void readMap() {
        try {
            File file = new File(MAP_FILENAME);
            Scanner scanner = new Scanner(file);
            
            MAP_WIDTH = scanner.nextInt();
            MAP_HEIGHT = scanner.nextInt();
            map = new int[MAP_HEIGHT][MAP_WIDTH];
            imageViewMap = new ImageView[MAP_HEIGHT][MAP_WIDTH];
            
            System.out.println("MAP_WIDTH="+MAP_WIDTH);
            System.out.println("MAP_HEIGHT="+MAP_HEIGHT);

            for(int row = 0; row < MAP_HEIGHT; row ++){
                for(int col = 0; col < MAP_WIDTH; col ++){
                    map[row][col] = scanner.nextInt();
                }
            }
            
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: Missing map file...");
        }
    }
    
    private void printMap(){
        for(int row = 0; row < MAP_HEIGHT; row ++){
            for(int col = 0; col < MAP_WIDTH; col ++){
                    System.out.print(map[row][col] + " ");
            }
            System.out.println();
        }
    }

    private void addTiles() {
        for(int row = 0; row < MAP_HEIGHT; row ++){
            for(int col = 0; col < MAP_WIDTH; col ++){
                    
                imageViewMap[row][col] = new ImageView(tiles[map[row][col]].getImage());
                imageViewMap[row][col].setTranslateX(col * Tile.TILE_WIDTH);
                imageViewMap[row][col].setTranslateY(row * Tile.TILE_HEIGHT);
                root.getChildren().add(imageViewMap[row][col]);
            }
        }
    }

    int getXCoordinate(float positionX,float width){
        return (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
    }
    
    int getYCoordinate(float positionY,float height){
        return (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
    }
    
    Tile getTileAbove(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && col >= 0 && col < MAP_WIDTH)
            return tiles[map[row - 1][col]];
        else return null;
    }
    
    ImageView getTileImageViewAbove(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && col >= 0 && col < MAP_WIDTH)
            return imageViewMap[row - 1][col];
        else return null;
    }

    Tile getTileBelow(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && col >= 0 && col < MAP_WIDTH)
            return tiles[map[row + 1][col]];
        else return null;
    }
    
    ImageView getTileImageViewBelow(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && col >= 0 && col < MAP_WIDTH)
            return imageViewMap[row + 1][col];
        else return null;
    }

    Tile getTileToTheRight(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
//System.out.println("    positionX=" + positionX+", positionY="+ positionY+ "width=" + width + "height=" + height );
//System.out.println("    col=" + (col+1)+", row="+ row+ "solid: " + tiles[map[row][col + 1]].isIsSolid());
        
        if (row >= 0 && row < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH)
            return tiles[map[row][col + 1]];
        else return null;
    }
    
    ImageView getTileImageViewToTheRight(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if (row >= 0 && row < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH)
            return imageViewMap[row][col + 1];
        else return null;
    }

    Tile getTileToTheLeft(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if (row >= 0 && row < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH)
            return tiles[map[row][col - 1]];
        else return null;
    }
    
    ImageView getTileImageViewToTheLeft(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if (row >= 0 && row < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH)
            return imageViewMap[row][col - 1];
        else return null;
    }

    Tile getTileAboveToTheRight(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH)
            return tiles[map[row - 1][col + 1]];
        else return null;
    }
    
    ImageView getTileImageViewAboveToTheRight(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH)
            return imageViewMap[row - 1][col + 1];
        else return null;
    }

    Tile getTileAboveToTheLeft(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH)
            return tiles[map[row - 1][col - 1]];
        else return null;
    }
    
    ImageView getTileImageViewAboveToTheLeft(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH)
            return imageViewMap[row - 1][col - 1];
        else return null;
    }

    Tile getTileBelowToTheRight(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH)
            return tiles[map[row + 1][col + 1]];
        else return null;
    }
    
    ImageView getTileImageViewBelowToTheRight(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH)
            return imageViewMap[row + 1][col + 1];
        else return null;
    }

    Tile getTileBelowToTheLeft(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH)
            return tiles[map[row + 1][col - 1]];
        else return null;
    }
    
    ImageView getTileImageViewBelowToTheLeft(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / Tile.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / Tile.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH)
            return imageViewMap[row + 1][col - 1];
        else return null;
    }
    
}
