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
    public static int GAME_WIDTH;
    private static int MAP_HEIGHT;    
    public static int GAME_HEIGHT;
    private Tile[][] tileMap;
    private static final int NUM_TILE_TYPES = 64;
    private TileType[] tilesTypes = new TileType[NUM_TILE_TYPES];
    private Group root;
    

    public World(Group root) {
        this.root = root;
        createTiles();
        readMap();
        //printMap();
    }

    private void createTiles() {
        for(int i = 0; i < NUM_TILE_TYPES; i++){
           tilesTypes[i] = new TileType("img/tiles/tile_"+i+".png", true, i); 
        }


        tilesTypes[8].setIsSolid(false);
        tilesTypes[12].setIsSolid(false);
        tilesTypes[28].setIsSolid(false);
        tilesTypes[37].setIsSolid(false);
        tilesTypes[38].setIsSolid(false);
        tilesTypes[39].setIsSolid(false);
        tilesTypes[40].setIsSolid(false);
        tilesTypes[41].setIsSolid(false);
        tilesTypes[42].setIsSolid(false);
        tilesTypes[43].setIsSolid(false);
        tilesTypes[44].setIsSolid(false);
        tilesTypes[45].setIsSolid(false);
        tilesTypes[46].setIsSolid(false);
        tilesTypes[50].setIsSolid(false);
        tilesTypes[51].setIsSolid(false);
        tilesTypes[54].setIsSolid(false);
        tilesTypes[55].setIsSolid(false);
    }

    private void readMap() {
        try {
            File file = new File(MAP_FILENAME);
            Scanner scanner = new Scanner(file);
            
            MAP_WIDTH = scanner.nextInt();
            GAME_WIDTH = MAP_WIDTH * TileType.TILE_WIDTH;
            MAP_HEIGHT = scanner.nextInt();
            GAME_HEIGHT = MAP_HEIGHT * TileType.TILE_HEIGHT;
            tileMap = new Tile[MAP_HEIGHT][MAP_WIDTH];
            
            System.out.println("MAP_WIDTH="+MAP_WIDTH);
            System.out.println("MAP_HEIGHT="+MAP_HEIGHT);

            for(int row = 0; row < MAP_HEIGHT; row ++){
                for(int col = 0; col < MAP_WIDTH; col ++){
                   int intType = scanner.nextInt();
                   ImageView imageView = new ImageView(tilesTypes[intType].getImage());                   
                   imageView.setTranslateX(col * TileType.TILE_WIDTH);
                   imageView.setTranslateY(row * TileType.TILE_HEIGHT);
                   root.getChildren().add(imageView);
                   tileMap[row][col] = new Tile(imageView, tilesTypes[intType]);
                }
            }
            
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: Missing map file...");
        }
    }
    
    public void setTranslateX(float XScroll){        
System.out.println("+++++++++++++++XScrol="+XScroll);
        for(int row = 0; row < MAP_HEIGHT; row ++){
            for(int col = 0; col < MAP_WIDTH; col ++){                         
               tileMap[row][col].getImageView().setTranslateX(tileMap[row][col].getImageView().getTranslateX() + XScroll);//+ XScroll
            }
        }
    }
    
    public void setTranslateY(float YScroll){         
System.out.println("+++++++++++++++YScroll="+YScroll);
        for(int row = 0; row < MAP_HEIGHT; row ++){
            for(int col = 0; col < MAP_WIDTH; col ++){                         
               tileMap[row][col].getImageView().setTranslateY(tileMap[row][col].getImageView().getTranslateY() + YScroll); // + YScroll
            }
        }
    }
    
    private void printMap(){
        for(int row = 0; row < MAP_HEIGHT; row ++){
            for(int col = 0; col < MAP_WIDTH; col ++){
                    System.out.print(tileMap[row][col].getType() + " ");
            }
            System.out.println();
        }
    }


    int getXCoordinate(float positionX,float width){
        return (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
    }
    
    int getYCoordinate(float positionY,float height){
        return (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
    }
    
    Tile getTileAbove(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && col >= 0 && col < MAP_WIDTH)
            return tileMap[row - 1][col];
        else return null;
    }

    Tile getTileBelow(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && col >= 0 && col < MAP_WIDTH)
            return tileMap[row + 1][col];
        else return null;
    }

    Tile getTileToTheRight(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
//System.out.println("    positionX=" + positionX+", positionY="+ positionY+ "width=" + width + "height=" + height );
//System.out.println("    col=" + (col+1)+", row="+ row+ "solid: " + tiles[map[row][col + 1]].isIsSolid());
        if (row >= 0 && row < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH)
            return tileMap[row][col + 1];
        else return null;
    }
   

    Tile getTileToTheLeft(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if (row >= 0 && row < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH)
            return tileMap[row][col - 1];
        else return null;
    }

    Tile getTileAboveToTheRight(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH)
            return tileMap[row - 1][col + 1];
        else return null;
    }

    Tile getTileAboveToTheLeft(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH)
            return tileMap[row - 1][col - 1];
        else return null;
    }

    Tile getTileBelowToTheRight(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH)
            return tileMap[row + 1][col + 1];
        else return null;
    }

    Tile getTileBelowToTheLeft(float positionX, float positionY, float width, float height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH)
            return tileMap[row + 1][col - 1];
        else return null;
    }
    
}
