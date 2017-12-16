/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lebronquest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

/**
 *
 * @author transflorida
 */
public class World {

    private final static Logger LOGGER = Logger.getLogger(World.class.getName());
    private static final String MAP_FILENAME = "src/resources/map.txt";
    private static int MAP_WIDTH;
    public static int GAME_WIDTH;
    private static int MAP_HEIGHT;
    public static int GAME_HEIGHT;
    private Tile[][] tileMap;
    private static final int NUM_TILE_TYPES = 64;
    private static final TileType[] TILE_TYPES = new TileType[NUM_TILE_TYPES];
    private final Group root;

    public World(Group root) {
        LOGGER.setLevel(Level.OFF);
        this.root = root;
        createTiles();
        readMap();
        //printMap();
    }

    private void createTiles() {
        for (int i = 0; i < NUM_TILE_TYPES; i++) {
            TILE_TYPES[i] = new TileType("img/tiles/tile_" + i + ".png", false, false, false, false, i);
        }

        TILE_TYPES[0].setIsSolidTop(true);
        TILE_TYPES[0].setIsSolidBottom(true);
        TILE_TYPES[0].setIsSolidRight(true);
        TILE_TYPES[0].setIsSolidLeft(true);
        TILE_TYPES[1].setIsSolidTop(true);
        TILE_TYPES[1].setIsSolidBottom(true);
        TILE_TYPES[1].setIsSolidRight(true);
        TILE_TYPES[1].setIsSolidLeft(true);
        TILE_TYPES[2].setIsSolidTop(true);
        TILE_TYPES[2].setIsSolidBottom(true);
        TILE_TYPES[2].setIsSolidRight(true);
        TILE_TYPES[2].setIsSolidLeft(true);
        TILE_TYPES[3].setIsSolidTop(true);
        TILE_TYPES[3].setIsSolidBottom(true);
        TILE_TYPES[3].setIsSolidRight(true);
        TILE_TYPES[3].setIsSolidLeft(true);
        TILE_TYPES[4].setIsSolidTop(true);
        TILE_TYPES[4].setIsSolidBottom(true);
        TILE_TYPES[4].setIsSolidRight(true);
        TILE_TYPES[4].setIsSolidLeft(true);
        TILE_TYPES[5].setIsSolidTop(true);
        TILE_TYPES[5].setIsSolidBottom(true);
        TILE_TYPES[5].setIsSolidRight(true);
        TILE_TYPES[5].setIsSolidLeft(true);
        TILE_TYPES[6].setIsSolidTop(true);
        TILE_TYPES[6].setIsSolidBottom(true);
        TILE_TYPES[6].setIsSolidRight(true);
        TILE_TYPES[6].setIsSolidLeft(true);
        TILE_TYPES[7].setIsSolidTop(true);
        TILE_TYPES[7].setIsSolidBottom(true);
        TILE_TYPES[7].setIsSolidRight(true);
        TILE_TYPES[7].setIsSolidLeft(true);

        TILE_TYPES[9].setIsSolidTop(true);
        TILE_TYPES[9].setIsSolidBottom(true);
        TILE_TYPES[9].setIsSolidRight(true);
        TILE_TYPES[9].setIsSolidLeft(true);
        TILE_TYPES[10].setIsSolidTop(true);
        TILE_TYPES[10].setIsSolidBottom(true);
        TILE_TYPES[10].setIsSolidRight(true);
        TILE_TYPES[10].setIsSolidLeft(true);
        TILE_TYPES[11].setIsSolidTop(true);
        TILE_TYPES[11].setIsSolidBottom(true);
        TILE_TYPES[11].setIsSolidRight(true);
        TILE_TYPES[11].setIsSolidLeft(true);

        TILE_TYPES[13].setIsSolidTop(true);
        TILE_TYPES[13].setIsSolidBottom(true);
        TILE_TYPES[13].setIsSolidRight(true);
        TILE_TYPES[13].setIsSolidLeft(true);
        TILE_TYPES[14].setIsSolidTop(true);
        TILE_TYPES[14].setIsSolidBottom(true);
        TILE_TYPES[14].setIsSolidRight(true);
        TILE_TYPES[14].setIsSolidLeft(true);
        TILE_TYPES[15].setIsSolidTop(true);
        TILE_TYPES[15].setIsSolidBottom(true);
        TILE_TYPES[15].setIsSolidRight(true);
        TILE_TYPES[15].setIsSolidLeft(true);
        TILE_TYPES[16].setIsSolidTop(true);
        TILE_TYPES[16].setIsSolidBottom(true);
        TILE_TYPES[16].setIsSolidRight(true);
        TILE_TYPES[16].setIsSolidLeft(true);
        TILE_TYPES[17].setIsSolidTop(true);
        TILE_TYPES[17].setIsSolidBottom(true);
        TILE_TYPES[17].setIsSolidRight(true);
        TILE_TYPES[17].setIsSolidLeft(true);
        TILE_TYPES[18].setIsSolidTop(true);
        TILE_TYPES[18].setIsSolidBottom(true);
        TILE_TYPES[18].setIsSolidRight(true);
        TILE_TYPES[18].setIsSolidLeft(true);
        TILE_TYPES[19].setIsSolidTop(true);
        TILE_TYPES[19].setIsSolidBottom(true);
        TILE_TYPES[19].setIsSolidRight(true);
        TILE_TYPES[19].setIsSolidLeft(true);
        TILE_TYPES[20].setIsSolidTop(true);
        TILE_TYPES[20].setIsSolidBottom(true);
        TILE_TYPES[20].setIsSolidRight(true);
        TILE_TYPES[20].setIsSolidLeft(true);
        TILE_TYPES[21].setIsSolidTop(true);
        TILE_TYPES[21].setIsSolidBottom(true);
        TILE_TYPES[21].setIsSolidRight(true);
        TILE_TYPES[21].setIsSolidLeft(true);
        TILE_TYPES[22].setIsSolidTop(true);
        TILE_TYPES[22].setIsSolidBottom(true);
        TILE_TYPES[22].setIsSolidRight(true);
        TILE_TYPES[22].setIsSolidLeft(true);
        TILE_TYPES[23].setIsSolidTop(true);
        TILE_TYPES[23].setIsSolidBottom(true);
        TILE_TYPES[23].setIsSolidRight(true);
        TILE_TYPES[23].setIsSolidLeft(true);
        TILE_TYPES[24].setIsSolidTop(true);
        TILE_TYPES[24].setIsSolidBottom(true);
        TILE_TYPES[24].setIsSolidRight(true);
        TILE_TYPES[24].setIsSolidLeft(true);
        TILE_TYPES[25].setIsSolidTop(true);
        TILE_TYPES[25].setIsSolidBottom(true);
        TILE_TYPES[25].setIsSolidRight(true);
        TILE_TYPES[25].setIsSolidLeft(true);
        TILE_TYPES[26].setIsSolidTop(true);
        TILE_TYPES[26].setIsSolidBottom(true);
        TILE_TYPES[26].setIsSolidRight(true);
        TILE_TYPES[26].setIsSolidLeft(true);
        TILE_TYPES[27].setIsSolidTop(true);
        TILE_TYPES[27].setIsSolidBottom(true);
        TILE_TYPES[27].setIsSolidRight(true);
        TILE_TYPES[27].setIsSolidLeft(true);

        TILE_TYPES[29].setIsSolidTop(true);
        TILE_TYPES[29].setIsSolidBottom(true);
        TILE_TYPES[29].setIsSolidRight(true);
        TILE_TYPES[29].setIsSolidLeft(true);
        TILE_TYPES[30].setIsSolidTop(true);
        TILE_TYPES[30].setIsSolidBottom(true);
        TILE_TYPES[30].setIsSolidRight(true);
        TILE_TYPES[30].setIsSolidLeft(true);
        TILE_TYPES[31].setIsSolidTop(true);
        TILE_TYPES[31].setIsSolidBottom(true);
        TILE_TYPES[31].setIsSolidRight(true);
        TILE_TYPES[31].setIsSolidLeft(true);
        TILE_TYPES[32].setIsSolidTop(true);
        TILE_TYPES[32].setIsSolidBottom(true);
        TILE_TYPES[32].setIsSolidRight(true);
        TILE_TYPES[32].setIsSolidLeft(true);
        TILE_TYPES[33].setIsSolidTop(true);
        TILE_TYPES[33].setIsSolidBottom(true);
        TILE_TYPES[33].setIsSolidRight(true);
        TILE_TYPES[33].setIsSolidLeft(true);
        TILE_TYPES[34].setIsSolidTop(true);
        TILE_TYPES[34].setIsSolidBottom(true);
        TILE_TYPES[34].setIsSolidRight(true);
        TILE_TYPES[34].setIsSolidLeft(true);
        TILE_TYPES[35].setIsSolidTop(true);
        TILE_TYPES[35].setIsSolidBottom(true);
        TILE_TYPES[35].setIsSolidRight(true);
        TILE_TYPES[35].setIsSolidLeft(true);
        TILE_TYPES[36].setIsSolidTop(true);
        TILE_TYPES[36].setIsSolidBottom(true);
        TILE_TYPES[36].setIsSolidRight(true);
        TILE_TYPES[36].setIsSolidLeft(true);

        TILE_TYPES[47].setIsSolidTop(true);
        TILE_TYPES[47].setIsSolidBottom(true);
        TILE_TYPES[47].setIsSolidRight(true);
        TILE_TYPES[47].setIsSolidLeft(true);
        TILE_TYPES[48].setIsSolidTop(true);
        TILE_TYPES[48].setIsSolidBottom(true);
        TILE_TYPES[48].setIsSolidRight(true);
        TILE_TYPES[48].setIsSolidLeft(true);
        TILE_TYPES[49].setIsSolidTop(true);
        TILE_TYPES[49].setIsSolidBottom(true);
        TILE_TYPES[49].setIsSolidRight(true);
        TILE_TYPES[49].setIsSolidLeft(true);

        TILE_TYPES[52].setIsSolidTop(true);
        TILE_TYPES[52].setIsSolidBottom(true);
        TILE_TYPES[52].setIsSolidRight(true);
        TILE_TYPES[52].setIsSolidLeft(true);
        TILE_TYPES[53].setIsSolidTop(true);
        TILE_TYPES[53].setIsSolidBottom(true);
        TILE_TYPES[53].setIsSolidRight(true);
        TILE_TYPES[53].setIsSolidLeft(true);

        TILE_TYPES[56].setIsSolidTop(true);
        TILE_TYPES[56].setIsSolidBottom(true);
        TILE_TYPES[56].setIsSolidRight(true);
        TILE_TYPES[56].setIsSolidLeft(true);
        TILE_TYPES[57].setIsSolidTop(true);
        TILE_TYPES[57].setIsSolidBottom(true);
        TILE_TYPES[57].setIsSolidRight(true);
        TILE_TYPES[57].setIsSolidLeft(true);
        TILE_TYPES[58].setIsSolidTop(true);
        TILE_TYPES[58].setIsSolidBottom(true);
        TILE_TYPES[58].setIsSolidRight(true);
        TILE_TYPES[58].setIsSolidLeft(true);
        TILE_TYPES[59].setIsSolidTop(true);
        TILE_TYPES[59].setIsSolidBottom(true);
        TILE_TYPES[59].setIsSolidRight(true);
        TILE_TYPES[59].setIsSolidLeft(true);
        TILE_TYPES[60].setIsSolidTop(true);
        TILE_TYPES[60].setIsSolidBottom(true);
        TILE_TYPES[60].setIsSolidRight(true);
        TILE_TYPES[60].setIsSolidLeft(true);
        TILE_TYPES[61].setIsSolidTop(true);
        TILE_TYPES[61].setIsSolidBottom(true);
        TILE_TYPES[61].setIsSolidRight(true);
        TILE_TYPES[61].setIsSolidLeft(true);
        TILE_TYPES[62].setIsSolidTop(true);
        TILE_TYPES[62].setIsSolidBottom(true);
        TILE_TYPES[62].setIsSolidRight(true);
        TILE_TYPES[62].setIsSolidLeft(true);
        TILE_TYPES[63].setIsSolidTop(true);
        TILE_TYPES[63].setIsSolidBottom(true);
        TILE_TYPES[63].setIsSolidRight(true);
        TILE_TYPES[63].setIsSolidLeft(true);

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

            for (int row = 0; row < MAP_HEIGHT; row++) {
                for (int col = 0; col < MAP_WIDTH; col++) {
                    int intType = scanner.nextInt();
                    ImageView imageView = new ImageView(TILE_TYPES[intType].getImage());
                    imageView.setTranslateX(col * TileType.TILE_WIDTH);
                    imageView.setTranslateY(row * TileType.TILE_HEIGHT);
                    root.getChildren().add(imageView);
                    tileMap[row][col] = new Tile(imageView, TILE_TYPES[intType], row, col);
                }
            }

            scanner.close();
        } catch (FileNotFoundException ex) {
            LOGGER.info("ERROR: Missing map file...");
        }
    }

    public void translateX(double XScroll) {
        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                tileMap[row][col].getImageView().setTranslateX(tileMap[row][col].getImageView().getTranslateX() + XScroll);//+ XScroll
            }
        }
    }

    public void translateY(double YScroll) {
        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                tileMap[row][col].getImageView().setTranslateY(tileMap[row][col].getImageView().getTranslateY() + YScroll); // + YScroll
            }
        }
    }
    
    void reset() {
        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                tileMap[row][col].getImageView().setTranslateX(col * TileType.TILE_WIDTH);               
                tileMap[row][col].getImageView().setTranslateY(row * TileType.TILE_HEIGHT); 
            }
        }
    }

    private void printMap() {
        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                LOGGER.info(tileMap[row][col].getType() + " ");
            }
            LOGGER.info("\n");
        }
    }

    private int getXCoordinate(double positionX, double width) {
        return (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
    }

    private int getYCoordinate(double positionY, double height) {
        return (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
    }

    public Tile getTileAbove(double positionX, double positionY, double width, double height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && col >= 0 && col < MAP_WIDTH) {
            return tileMap[row - 1][col];
        } else {
            return null;
        }
    }

    public Tile getTileBelow(double positionX, double positionY, double width, double height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && col >= 0 && col < MAP_WIDTH) {
            return tileMap[row + 1][col];
        } else {
            return null;
        }
    }

    public Tile getTileToTheRight(double positionX, double positionY, double width, double height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if (row >= 0 && row < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH) {
            return tileMap[row][col + 1];
        } else {
            return null;
        }
    }

    public Tile getTileToTheLeft(double positionX, double positionY, double width, double height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if (row >= 0 && row < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH) {
            return tileMap[row][col - 1];
        } else {
            return null;
        }
    }

    public Tile getTileAboveToTheRight(double positionX, double positionY, double width, double height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH) {
            return tileMap[row - 1][col + 1];
        } else {
            return null;
        }
    }

    public Tile getTileAboveToTheLeft(double positionX, double positionY, double width, double height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row - 1) >= 0 && (row - 1) < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH) {
            return tileMap[row - 1][col - 1];
        } else {
            return null;
        }
    }

    public Tile getTileBelowToTheRight(double positionX, double positionY, double width, double height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && (col + 1) >= 0 && (col + 1) < MAP_WIDTH) {
            return tileMap[row + 1][col + 1];
        } else {
            return null;
        }
    }

    public Tile getTileBelowToTheLeft(double positionX, double positionY, double width, double height) {
        int row = (int) ((positionY + height / 2) / TileType.TILE_HEIGHT);
        int col = (int) ((positionX + width / 2) / TileType.TILE_WIDTH);
        if ((row + 1) >= 0 && (row + 1) < MAP_HEIGHT && (col - 1) >= 0 && (col - 1) < MAP_WIDTH) {
            return tileMap[row + 1][col - 1];
        } else {
            return null;
        }
    }

    

}
