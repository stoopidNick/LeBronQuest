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
import javafx.scene.image.ImageView;

/**
 *
 * @author transflorida
 */
public class World {
    private static final String MAP_FILENAME = "src/resources/map.txt";
    private static int MAP_WIDTH;
    public static int GAME_WIDTH;
    private static int MAP_HEIGHT;    
    public static int GAME_HEIGHT;
    private Tile[][] tileMap;
    private static final int NUM_TILE_TYPES = 64;
    private static final TileType[] tilesTypes = new TileType[NUM_TILE_TYPES];
    private final Group root;
    

    public World(Group root) {
        this.root = root;
        createTiles();
        readMap();
        //printMap();
    }

    private void createTiles() {
        for(int i = 0; i < NUM_TILE_TYPES; i++){
           tilesTypes[i] = new TileType("img/tiles/tile_"+i+".png", false, false, false, false, i); 
        }
        
        tilesTypes[0].setIsSolidTop(true);
        tilesTypes[0].setIsSolidBottom(true);
        tilesTypes[0].setIsSolidRight(true);
        tilesTypes[0].setIsSolidLeft(true);
        tilesTypes[1].setIsSolidTop(true);
        tilesTypes[1].setIsSolidBottom(true);
        tilesTypes[1].setIsSolidRight(true);
        tilesTypes[1].setIsSolidLeft(true);
        tilesTypes[2].setIsSolidTop(true);
        tilesTypes[2].setIsSolidBottom(true);
        tilesTypes[2].setIsSolidRight(true);
        tilesTypes[2].setIsSolidLeft(true);
        tilesTypes[3].setIsSolidTop(true);
        tilesTypes[3].setIsSolidBottom(true);
        tilesTypes[3].setIsSolidRight(true);
        tilesTypes[3].setIsSolidLeft(true);
        tilesTypes[4].setIsSolidTop(true);
        tilesTypes[4].setIsSolidBottom(true);
        tilesTypes[4].setIsSolidRight(true);
        tilesTypes[4].setIsSolidLeft(true);
        tilesTypes[5].setIsSolidTop(true);
        tilesTypes[5].setIsSolidBottom(true);
        tilesTypes[5].setIsSolidRight(true);
        tilesTypes[5].setIsSolidLeft(true);
        tilesTypes[6].setIsSolidTop(true);
        tilesTypes[6].setIsSolidBottom(true);
        tilesTypes[6].setIsSolidRight(true);
        tilesTypes[6].setIsSolidLeft(true);
        tilesTypes[7].setIsSolidTop(true);
        tilesTypes[7].setIsSolidBottom(true);
        tilesTypes[7].setIsSolidRight(true);
        tilesTypes[7].setIsSolidLeft(true);
        
        
        tilesTypes[9].setIsSolidTop(true);
        tilesTypes[9].setIsSolidBottom(true);
        tilesTypes[9].setIsSolidRight(true);
        tilesTypes[9].setIsSolidLeft(true);
        tilesTypes[10].setIsSolidTop(true);
        tilesTypes[10].setIsSolidBottom(true);
        tilesTypes[10].setIsSolidRight(true);
        tilesTypes[10].setIsSolidLeft(true);
        tilesTypes[11].setIsSolidTop(true);
        tilesTypes[11].setIsSolidBottom(true);
        tilesTypes[11].setIsSolidRight(true);
        tilesTypes[11].setIsSolidLeft(true);
        
        
        tilesTypes[13].setIsSolidTop(true);
        tilesTypes[13].setIsSolidBottom(true);
        tilesTypes[13].setIsSolidRight(true);
        tilesTypes[13].setIsSolidLeft(true);        
        tilesTypes[14].setIsSolidTop(true);
        tilesTypes[14].setIsSolidBottom(true);
        tilesTypes[14].setIsSolidRight(true);
        tilesTypes[14].setIsSolidLeft(true);
        tilesTypes[15].setIsSolidTop(true);
        tilesTypes[15].setIsSolidBottom(true);
        tilesTypes[15].setIsSolidRight(true);
        tilesTypes[15].setIsSolidLeft(true);
        tilesTypes[16].setIsSolidTop(true);
        tilesTypes[16].setIsSolidBottom(true);
        tilesTypes[16].setIsSolidRight(true);
        tilesTypes[16].setIsSolidLeft(true);
        tilesTypes[17].setIsSolidTop(true);
        tilesTypes[17].setIsSolidBottom(true);
        tilesTypes[17].setIsSolidRight(true);
        tilesTypes[17].setIsSolidLeft(true);
        tilesTypes[18].setIsSolidTop(true);
        tilesTypes[18].setIsSolidBottom(true);
        tilesTypes[18].setIsSolidRight(true);
        tilesTypes[18].setIsSolidLeft(true);
        tilesTypes[19].setIsSolidTop(true);
        tilesTypes[19].setIsSolidBottom(true);
        tilesTypes[19].setIsSolidRight(true);
        tilesTypes[19].setIsSolidLeft(true);
        tilesTypes[20].setIsSolidTop(true);
        tilesTypes[20].setIsSolidBottom(true);
        tilesTypes[20].setIsSolidRight(true);
        tilesTypes[20].setIsSolidLeft(true);
        tilesTypes[21].setIsSolidTop(true);
        tilesTypes[21].setIsSolidBottom(true);
        tilesTypes[21].setIsSolidRight(true);
        tilesTypes[21].setIsSolidLeft(true);
        tilesTypes[22].setIsSolidTop(true);
        tilesTypes[22].setIsSolidBottom(true);
        tilesTypes[22].setIsSolidRight(true);
        tilesTypes[22].setIsSolidLeft(true);
        tilesTypes[23].setIsSolidTop(true);
        tilesTypes[23].setIsSolidBottom(true);
        tilesTypes[23].setIsSolidRight(true);
        tilesTypes[23].setIsSolidLeft(true);
        tilesTypes[24].setIsSolidTop(true);
        tilesTypes[24].setIsSolidBottom(true);
        tilesTypes[24].setIsSolidRight(true);
        tilesTypes[24].setIsSolidLeft(true);
        tilesTypes[25].setIsSolidTop(true);
        tilesTypes[25].setIsSolidBottom(true);
        tilesTypes[25].setIsSolidRight(true);
        tilesTypes[25].setIsSolidLeft(true);
        tilesTypes[26].setIsSolidTop(true);
        tilesTypes[26].setIsSolidBottom(true);
        tilesTypes[26].setIsSolidRight(true);
        tilesTypes[26].setIsSolidLeft(true);
        tilesTypes[27].setIsSolidTop(true);
        tilesTypes[27].setIsSolidBottom(true);
        tilesTypes[27].setIsSolidRight(true);
        tilesTypes[27].setIsSolidLeft(true);
        
        
        tilesTypes[29].setIsSolidTop(true);
        tilesTypes[29].setIsSolidBottom(true);
        tilesTypes[29].setIsSolidRight(true);
        tilesTypes[29].setIsSolidLeft(true);
        tilesTypes[30].setIsSolidTop(true);
        tilesTypes[30].setIsSolidBottom(true);
        tilesTypes[30].setIsSolidRight(true);
        tilesTypes[30].setIsSolidLeft(true);
        tilesTypes[31].setIsSolidTop(true);
        tilesTypes[31].setIsSolidBottom(true);
        tilesTypes[31].setIsSolidRight(true);
        tilesTypes[31].setIsSolidLeft(true);
        tilesTypes[32].setIsSolidTop(true);
        tilesTypes[32].setIsSolidBottom(true);
        tilesTypes[32].setIsSolidRight(true);
        tilesTypes[32].setIsSolidLeft(true);
        tilesTypes[33].setIsSolidTop(true);
        tilesTypes[33].setIsSolidBottom(true);
        tilesTypes[33].setIsSolidRight(true);
        tilesTypes[33].setIsSolidLeft(true);
        tilesTypes[34].setIsSolidTop(true);
        tilesTypes[34].setIsSolidBottom(true);
        tilesTypes[34].setIsSolidRight(true);
        tilesTypes[34].setIsSolidLeft(true);
        tilesTypes[35].setIsSolidTop(true);
        tilesTypes[35].setIsSolidBottom(true);
        tilesTypes[35].setIsSolidRight(true);
        tilesTypes[35].setIsSolidLeft(true);
        tilesTypes[36].setIsSolidTop(true);
        tilesTypes[36].setIsSolidBottom(true);
        tilesTypes[36].setIsSolidRight(true);
        tilesTypes[36].setIsSolidLeft(true);
        
        
        tilesTypes[47].setIsSolidTop(true);
        tilesTypes[47].setIsSolidBottom(true);
        tilesTypes[47].setIsSolidRight(true);
        tilesTypes[47].setIsSolidLeft(true);
        tilesTypes[48].setIsSolidTop(true);
        tilesTypes[48].setIsSolidBottom(true);
        tilesTypes[48].setIsSolidRight(true);
        tilesTypes[48].setIsSolidLeft(true);
        tilesTypes[49].setIsSolidTop(true);
        tilesTypes[49].setIsSolidBottom(true);
        tilesTypes[49].setIsSolidRight(true);
        tilesTypes[49].setIsSolidLeft(true);
        
        
        tilesTypes[52].setIsSolidTop(true);
        tilesTypes[52].setIsSolidBottom(true);
        tilesTypes[52].setIsSolidRight(true);
        tilesTypes[52].setIsSolidLeft(true);
        tilesTypes[53].setIsSolidTop(true);
        tilesTypes[53].setIsSolidBottom(true);
        tilesTypes[53].setIsSolidRight(true);
        tilesTypes[53].setIsSolidLeft(true);
        
        
        tilesTypes[56].setIsSolidTop(true);
        tilesTypes[56].setIsSolidBottom(true);
        tilesTypes[56].setIsSolidRight(true);
        tilesTypes[56].setIsSolidLeft(true);
        tilesTypes[57].setIsSolidTop(true);
        tilesTypes[57].setIsSolidBottom(true);
        tilesTypes[57].setIsSolidRight(true);
        tilesTypes[57].setIsSolidLeft(true);
        tilesTypes[58].setIsSolidTop(true);
        tilesTypes[58].setIsSolidBottom(true);
        tilesTypes[58].setIsSolidRight(true);
        tilesTypes[58].setIsSolidLeft(true);
        tilesTypes[59].setIsSolidTop(true);
        tilesTypes[59].setIsSolidBottom(true);
        tilesTypes[59].setIsSolidRight(true);
        tilesTypes[59].setIsSolidLeft(true);
        tilesTypes[60].setIsSolidTop(true);
        tilesTypes[60].setIsSolidBottom(true);
        tilesTypes[60].setIsSolidRight(true);
        tilesTypes[60].setIsSolidLeft(true);
        tilesTypes[61].setIsSolidTop(true);
        tilesTypes[61].setIsSolidBottom(true);
        tilesTypes[61].setIsSolidRight(true);
        tilesTypes[61].setIsSolidLeft(true);
        tilesTypes[62].setIsSolidTop(true);
        tilesTypes[62].setIsSolidBottom(true);
        tilesTypes[62].setIsSolidRight(true);
        tilesTypes[62].setIsSolidLeft(true);
        tilesTypes[63].setIsSolidTop(true);
        tilesTypes[63].setIsSolidBottom(true);
        tilesTypes[63].setIsSolidRight(true);
        tilesTypes[63].setIsSolidLeft(true);
        
        
        
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


            for(int row = 0; row < MAP_HEIGHT; row ++){
                for(int col = 0; col < MAP_WIDTH; col ++){
                   int intType = scanner.nextInt();
                   ImageView imageView = new ImageView(tilesTypes[intType].getImage());                   
                   imageView.setTranslateX(col * TileType.TILE_WIDTH);
                   imageView.setTranslateY(row * TileType.TILE_HEIGHT);
                   root.getChildren().add(imageView);
                   tileMap[row][col] = new Tile(imageView, tilesTypes[intType], row, col);
                }
            }
            
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: Missing map file...");
        }
    }
    
    public void translateX(float XScroll){       
        for(int row = 0; row < MAP_HEIGHT; row ++){
            for(int col = 0; col < MAP_WIDTH; col ++){                         
               tileMap[row][col].getImageView().setTranslateX(tileMap[row][col].getImageView().getTranslateX() + XScroll);//+ XScroll
            }
        }
    }
    
    public void translateY(float YScroll){         
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

    int getXCoordinate(float positionX, float width){
        return (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
    }
    
    int getYCoordinate(float positionY, float height){
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
